package bg.sofia.uni.fmi.tdkirov.tradehub.mapper;

import bg.sofia.uni.fmi.tdkirov.tradehub.dto.shop.ShopCreateDto;
import bg.sofia.uni.fmi.tdkirov.tradehub.dto.shop.ShopPreviewDto;
import bg.sofia.uni.fmi.tdkirov.tradehub.dto.shop.ShopResponseDto;
import bg.sofia.uni.fmi.tdkirov.tradehub.entity.shop.Shop;
import bg.sofia.uni.fmi.tdkirov.tradehub.entity.user.SellerProfile;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class ShopMapper {
    // Must match the URL path the upload directory is served under
    private static final String UPLOADS_URL_PREFIX = "/uploads/";

    private DateMapper dateMapper;
    private UserMapper userMapper;

    public Shop toEntity(ShopCreateDto createDto, String picturePath, SellerProfile createdBy) {
        return new Shop(createDto.getName(), picturePath, createdBy);
    }

    public ShopPreviewDto toPreviewDto(Shop shop) {
        return new ShopPreviewDto(shop.getId(), shop.getName(), toPictureUrl(shop.getPicturePath()),
            shop.getCreatedBy().getDisplayName(),
            dateMapper.toStringDateOnly(shop.getCreatedAt()));
    }

    public ShopResponseDto toResponseDto(Shop shop) {
        return new ShopResponseDto(shop.getId(), shop.getName(), toPictureUrl(shop.getPicturePath()),
            userMapper.toSellerInfo(shop.getCreatedBy()),
            dateMapper.toStringDateOnly(shop.getCreatedAt()));
    }

    private String toPictureUrl(String picturePath) {
        if (picturePath == null || picturePath.isBlank()) {
            return null;
        }

        return UPLOADS_URL_PREFIX + picturePath;
    }
}
