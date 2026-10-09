package bg.sofia.uni.fmi.tdkirov.tradehub.service.implementations;

import bg.sofia.uni.fmi.tdkirov.tradehub.dto.shop.ShopCreateDto;
import bg.sofia.uni.fmi.tdkirov.tradehub.dto.shop.ShopPreviewDto;
import bg.sofia.uni.fmi.tdkirov.tradehub.dto.shop.ShopResponseDto;
import bg.sofia.uni.fmi.tdkirov.tradehub.entity.shop.Shop;
import bg.sofia.uni.fmi.tdkirov.tradehub.entity.shop.ShopItem;
import bg.sofia.uni.fmi.tdkirov.tradehub.entity.user.SellerProfile;
import bg.sofia.uni.fmi.tdkirov.tradehub.exception.shop.ShopAccessDenied;
import bg.sofia.uni.fmi.tdkirov.tradehub.exception.shop.ShopNotFound;
import bg.sofia.uni.fmi.tdkirov.tradehub.exception.user.seller.SellerNotFound;
import bg.sofia.uni.fmi.tdkirov.tradehub.mapper.ShopMapper;
import bg.sofia.uni.fmi.tdkirov.tradehub.repository.CartItemRepository;
import bg.sofia.uni.fmi.tdkirov.tradehub.repository.SellerProfileRepository;
import bg.sofia.uni.fmi.tdkirov.tradehub.repository.ShopItemRepository;
import bg.sofia.uni.fmi.tdkirov.tradehub.repository.ShopRepository;
import bg.sofia.uni.fmi.tdkirov.tradehub.service.FileStorageService;
import bg.sofia.uni.fmi.tdkirov.tradehub.service.ShopService;
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

@AllArgsConstructor
@Service
public class ShopServiceImpl implements ShopService {
    private static final String SHOP_PICTURES_DIR = "shops";

    private ShopRepository repository;
    private ShopMapper mapper;
    private SellerProfileRepository sellerRepository;
    private ShopItemRepository shopItemRepository;
    private CartItemRepository cartItemRepository;
    private FileStorageService fileStorageService;

    @Override
    public ShopPreviewDto createShop(ShopCreateDto createDto, MultipartFile picture,  String currUsername) {
        if (!sellerRepository.existsByUserUsername(currUsername)) {
            throw new SellerNotFound(String.format(
                "There is no seller with this username - %s",
                currUsername));
        }

        SellerProfile seller = sellerRepository.findByUserUsername(currUsername).get();

        String picturePath = fileStorageService.saveImage(picture, SHOP_PICTURES_DIR);

        Shop shop = mapper.toEntity(createDto, picturePath, seller);

        try {
            shop = repository.save(shop);
        } catch (RuntimeException e) {
            fileStorageService.delete(picturePath);
            throw e;
        }

        return mapper.toPreviewDto(shop);
    }

    @Override
    public List<ShopPreviewDto> getAllShops() {
        List<Shop> shops = repository.findAll();

        List<ShopPreviewDto> shopPreviews = new ArrayList<>();
        for (Shop shop: shops) {
            shopPreviews.add(mapper.toPreviewDto(shop));
        }

        return shopPreviews;
    }

    @Override
    public List<ShopPreviewDto> getAllShopsOfUser(Long userId) {
        List<Shop> shops = repository.findAllByCreatedByUserId(userId);

        List<ShopPreviewDto> shopPreviews = new ArrayList<>();
        for (Shop shop: shops) {
            shopPreviews.add(mapper.toPreviewDto(shop));
        }

        return shopPreviews;
    }

    @Override
    public List<ShopPreviewDto> getAllShopsOfUser(String currUsername) {
        List<Shop> shops = repository.findAllByCreatedByUserUsername(currUsername);

        List<ShopPreviewDto> shopPreviews = new ArrayList<>();
        for (Shop shop: shops) {
            shopPreviews.add(mapper.toPreviewDto(shop));
        }

        return shopPreviews;
    }

    @Transactional
    @Override
    public void deleteShop(Long id, String currUsername) {
        Shop shop = repository.findById(id)
            .orElseThrow(() -> new ShopNotFound("The shop is missing. "));

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        boolean isAdmin = auth.getAuthorities().stream()
            .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        if (!isAdmin && !shop.getCreatedBy().getUser().getUsername().equals(currUsername)) {
            throw new ShopAccessDenied(String.format(
                "The shop with id %d is not owned by %s",
                id, currUsername));
        }

        List<String> picturePaths = new ArrayList<>();
        picturePaths.add(shop.getPicturePath());
        for (ShopItem item : shop.getItems()) {
            picturePaths.add(item.getPicturePath());
        }

        cartItemRepository.deleteAllByShopId(id);
        shopItemRepository.deleteAll(shop.getItems());
        repository.delete(shop);

        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void afterCommit() {
                picturePaths.forEach(fileStorageService::delete);
            }
        });
    }

    @Transactional(readOnly = true)
    @Override
    public ShopResponseDto getShopById(Long id) {
        if (!repository.existsById(id)) {
            throw new ShopNotFound("The shop is missing. ");
        }

        Shop shop = repository.findById(id).get();

        return mapper.toResponseDto(shop);
    }
}
