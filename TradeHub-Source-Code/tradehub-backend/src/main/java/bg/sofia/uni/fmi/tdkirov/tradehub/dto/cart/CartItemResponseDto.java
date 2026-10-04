package bg.sofia.uni.fmi.tdkirov.tradehub.dto.cart;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItemResponseDto {
    private Long id;
    private Long itemId;
    private String itemName;
    private String pictureUrl;
    private BigDecimal price;
    private Integer quantity;
    private Integer inStock;
    private BigDecimal totalPrice;
    private Long shopId;
    private String shopName;
}