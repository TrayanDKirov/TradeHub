package bg.sofia.uni.fmi.tdkirov.tradehub.service.implementations;

import bg.sofia.uni.fmi.tdkirov.tradehub.dto.shopitem.ShopItemCreateDto;
import bg.sofia.uni.fmi.tdkirov.tradehub.dto.shopitem.ShopItemPreviewDto;
import bg.sofia.uni.fmi.tdkirov.tradehub.dto.shopitem.ShopItemResponseDto;
import bg.sofia.uni.fmi.tdkirov.tradehub.entity.shop.Shop;
import bg.sofia.uni.fmi.tdkirov.tradehub.entity.shop.ShopItem;
import bg.sofia.uni.fmi.tdkirov.tradehub.entity.user.SellerProfile;
import bg.sofia.uni.fmi.tdkirov.tradehub.exception.shop.ShopAccessDenied;
import bg.sofia.uni.fmi.tdkirov.tradehub.exception.shop.ShopNotFound;
import bg.sofia.uni.fmi.tdkirov.tradehub.exception.shopitem.ShopItemNotFound;
import bg.sofia.uni.fmi.tdkirov.tradehub.exception.user.seller.SellerNotFound;
import bg.sofia.uni.fmi.tdkirov.tradehub.mapper.ShopItemMapper;
import bg.sofia.uni.fmi.tdkirov.tradehub.repository.CartItemRepository;
import bg.sofia.uni.fmi.tdkirov.tradehub.repository.SellerProfileRepository;
import bg.sofia.uni.fmi.tdkirov.tradehub.repository.ShopItemRepository;
import bg.sofia.uni.fmi.tdkirov.tradehub.repository.ShopRepository;
import bg.sofia.uni.fmi.tdkirov.tradehub.service.FileStorageService;
import bg.sofia.uni.fmi.tdkirov.tradehub.service.ShopItemService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ShopItemServiceImpl implements ShopItemService {
    private static final String SHOP_ITEM_PICTURES_DIR = "shop_items";

    private ShopItemMapper mapper;
    private ShopItemRepository repository;
    private ShopRepository shopRepository;
    private SellerProfileRepository sellerRepository;
    private CartItemRepository cartItemRepository;
    private FileStorageService fileStorageService;

    @Override
    public ShopItemPreviewDto createShopItem(ShopItemCreateDto createDto, MultipartFile picture, String currentUsername) {
        SellerProfile seller = sellerRepository.findByUserUsername(currentUsername)
            .orElseThrow(() -> new SellerNotFound(String.format(
                "There is no seller with this username - %s",
                currentUsername)));

        Shop shop = shopRepository.findById(createDto.getShopId())
            .orElseThrow(() -> new ShopNotFound("The shop is missing. "));

        if (!shop.getCreatedBy().getUserId().equals(seller.getUserId())) {
            throw new ShopAccessDenied(String.format(
                "The shop with id %d is not owned by %s",
                shop.getId(), currentUsername));
        }

        String picturePath = fileStorageService.saveImage(picture, SHOP_ITEM_PICTURES_DIR);

        ShopItem shopItem = mapper.toEntity(createDto, picturePath, shop, seller);

        try {
            shopItem = repository.save(shopItem);
        } catch (RuntimeException e) {
            fileStorageService.delete(picturePath);
            throw e;
        }

        return mapper.toPreviewDto(shopItem);
    }

    @Override
    public List<ShopItemPreviewDto> getShopItems(Long shopId) {
        if (!shopRepository.existsById(shopId)) {
            throw new ShopNotFound("The shop is missing. ");
        }

        List<ShopItem> shopItems = repository.findAllByShopId(shopId);

        List<ShopItemPreviewDto> shopItemPreviews = new ArrayList<>();
        for (ShopItem shopItem : shopItems) {
            shopItemPreviews.add(mapper.toPreviewDto(shopItem));
        }

        return shopItemPreviews;
    }

    @Override
    public ShopItemResponseDto getShopItem(Long id) {
        ShopItem shopItem = repository.findById(id)
            .orElseThrow(() -> new ShopItemNotFound("The shop item is missing. "));

        return mapper.toResponseDto(shopItem);
    }

    @Transactional
    @Override
    public void deleteShopItem(Long id, String currUsername) {
        ShopItem shopItem = repository.findById(id)
            .orElseThrow(() -> new ShopItemNotFound("The shop item is missing. "));

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        boolean isAdmin = auth.getAuthorities().stream()
            .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        if (!isAdmin && !shopItem.getCreatedBy().getUser().getUsername().equals(currUsername)) {
            throw new ShopAccessDenied(String.format(
                "The shop item with id %d is not owned by %s", id, currUsername));
        }

        String picturePath = shopItem.getPicturePath();

        cartItemRepository.deleteAllByItemId(id);
        repository.delete(shopItem);

        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void afterCommit() {
                fileStorageService.delete(picturePath);
            }
        });
    }
}