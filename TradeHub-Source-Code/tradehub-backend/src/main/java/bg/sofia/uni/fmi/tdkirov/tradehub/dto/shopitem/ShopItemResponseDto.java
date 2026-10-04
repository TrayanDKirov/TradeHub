package bg.sofia.uni.fmi.tdkirov.tradehub.dto.shopitem;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShopItemResponseDto {
    private Long id;
    private String name;
    private String pictureUrl;
    private BigDecimal price;
    private Integer quantity;
    private String description;
    private Long shopId;
    private String createdAt;
}
