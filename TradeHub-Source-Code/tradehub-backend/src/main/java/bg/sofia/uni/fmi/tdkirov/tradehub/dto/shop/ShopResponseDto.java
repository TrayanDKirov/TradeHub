package bg.sofia.uni.fmi.tdkirov.tradehub.dto.shop;

import bg.sofia.uni.fmi.tdkirov.tradehub.dto.shopitem.ShopItemPreviewDto;
import bg.sofia.uni.fmi.tdkirov.tradehub.dto.user.seller.SellerInfoDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShopResponseDto {
    private Long id;
    private String name;
    private String pictureUrl;
    private List<ShopItemPreviewDto> items;
    private SellerInfoDto seller;
    private String createdAt;
}