package bg.sofia.uni.fmi.tdkirov.tradehub.dto.shop;

import bg.sofia.uni.fmi.tdkirov.tradehub.dto.user.seller.SellerInfoDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShopResponseDto {
    private Long id;
    private String name;
    private String pictureUrl;

    private SellerInfoDto seller;
    private String createdAt;
}