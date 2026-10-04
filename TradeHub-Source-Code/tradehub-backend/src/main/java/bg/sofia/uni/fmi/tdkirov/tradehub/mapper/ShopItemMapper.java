package bg.sofia.uni.fmi.tdkirov.tradehub.mapper;

import bg.sofia.uni.fmi.tdkirov.tradehub.dto.shopitem.ShopItemCreateDto;
import bg.sofia.uni.fmi.tdkirov.tradehub.dto.shopitem.ShopItemPreviewDto;
import bg.sofia.uni.fmi.tdkirov.tradehub.dto.shopitem.ShopItemResponseDto;
import bg.sofia.uni.fmi.tdkirov.tradehub.entity.shop.Shop;
import bg.sofia.uni.fmi.tdkirov.tradehub.entity.shop.ShopItem;
import bg.sofia.uni.fmi.tdkirov.tradehub.entity.user.SellerProfile;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class ShopItemMapper {
    private DateMapper dateMapper;
    private PictureMapper pictureMapper;

    public ShopItem toEntity(ShopItemCreateDto createDto, String picturePath,
                             Shop shop, SellerProfile createdBy) {
        return new ShopItem(createDto.getName(), picturePath,
            createDto.getPrice(), createDto.getQuantity(),
            createDto.getDescription(), shop, createdBy);
    }

    public ShopItemPreviewDto toPreviewDto(ShopItem shopItem) {
        return new ShopItemPreviewDto(shopItem.getId(), shopItem.getName(),
            pictureMapper.toPictureUrl(shopItem.getPicturePath()),
            shopItem.getPrice(), shopItem.getQuantity(), shopItem.getShop().getId(),
            dateMapper.toStringDateOnly(shopItem.getCreatedAt()));
    }

    public ShopItemResponseDto toResponseDto(ShopItem shopItem) {
        return new ShopItemResponseDto(shopItem.getId(), shopItem.getName(),
            pictureMapper.toPictureUrl(shopItem.getPicturePath()),
            shopItem.getPrice(), shopItem.getQuantity(), shopItem.getDescription(),
            shopItem.getShop().getId(),
            dateMapper.toStringDateOnly(shopItem.getCreatedAt()));
    }
}