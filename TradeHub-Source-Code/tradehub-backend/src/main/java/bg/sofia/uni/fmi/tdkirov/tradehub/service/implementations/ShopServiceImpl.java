package bg.sofia.uni.fmi.tdkirov.tradehub.service.implementations;

import bg.sofia.uni.fmi.tdkirov.tradehub.dto.shop.ShopCreateDto;
import bg.sofia.uni.fmi.tdkirov.tradehub.dto.shop.ShopPreviewDto;
import bg.sofia.uni.fmi.tdkirov.tradehub.dto.shop.ShopResponseDto;
import bg.sofia.uni.fmi.tdkirov.tradehub.entity.shop.Shop;
import bg.sofia.uni.fmi.tdkirov.tradehub.entity.user.SellerProfile;
import bg.sofia.uni.fmi.tdkirov.tradehub.exception.shop.ShopNotFound;
import bg.sofia.uni.fmi.tdkirov.tradehub.exception.user.seller.SellerNotFound;
import bg.sofia.uni.fmi.tdkirov.tradehub.mapper.ShopMapper;
import bg.sofia.uni.fmi.tdkirov.tradehub.repository.SellerProfileRepository;
import bg.sofia.uni.fmi.tdkirov.tradehub.repository.ShopRepository;
import bg.sofia.uni.fmi.tdkirov.tradehub.service.FileStorageService;
import bg.sofia.uni.fmi.tdkirov.tradehub.service.ShopService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
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

    @Override
    public ShopResponseDto getShopById(Long id) {
        if (!repository.existsById(id)) {
            throw new ShopNotFound("The shop is missing. ");
        }

        Shop shop = repository.findById(id).get();

        return mapper.toResponseDto(shop);
    }
}
