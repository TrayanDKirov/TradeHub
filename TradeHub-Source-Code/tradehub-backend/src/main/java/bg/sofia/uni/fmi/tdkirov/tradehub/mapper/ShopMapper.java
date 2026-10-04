package bg.sofia.uni.fmi.tdkirov.tradehub.mapper;

import bg.sofia.uni.fmi.tdkirov.tradehub.dto.shop.ShopCreateDto;
import bg.sofia.uni.fmi.tdkirov.tradehub.dto.shop.ShopPreviewDto;
import bg.sofia.uni.fmi.tdkirov.tradehub.dto.shop.ShopResponseDto;
import bg.sofia.uni.fmi.tdkirov.tradehub.dto.shopitem.ShopItemPreviewDto;
import bg.sofia.uni.fmi.tdkirov.tradehub.entity.shop.Shop;
import bg.sofia.uni.fmi.tdkirov.tradehub.entity.shop.ShopItem;
import bg.sofia.uni.fmi.tdkirov.tradehub.entity.user.SellerProfile;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Component
public class ShopMapper {
    private DateMapper dateMapper;
    private UserMapper userMapper;
    private PictureMapper pictureMapper;
    private ShopItemMapper shopItemMapper;

    public Shop toEntity(ShopCreateDto createDto, String picturePath, SellerProfile createdBy) {
        return new Shop(createDto.getName(), picturePath, createdBy);
    }

    public ShopPreviewDto toPreviewDto(Shop shop) {
        return new ShopPreviewDto(shop.getId(), shop.getName(),
            pictureMapper.toPictureUrl(shop.getPicturePath()),
            shop.getCreatedBy().getDisplayName(),
            dateMapper.toStringDateOnly(shop.getCreatedAt()));
    }

    public ShopResponseDto toResponseDto(Shop shop) {
        List<ShopItemPreviewDto> items = new ArrayList<>();
        for (ShopItem item : shop.getItems()) {
            items.add(shopItemMapper.toPreviewDto(item));
        }

        return new ShopResponseDto(shop.getId(), shop.getName(),
            pictureMapper.toPictureUrl(shop.getPicturePath()),
            items,
            userMapper.toSellerInfo(shop.getCreatedBy()),
            dateMapper.toStringDateOnly(shop.getCreatedAt()));
    }
}