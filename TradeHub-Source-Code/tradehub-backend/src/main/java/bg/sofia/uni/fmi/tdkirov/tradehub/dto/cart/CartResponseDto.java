package bg.sofia.uni.fmi.tdkirov.tradehub.dto.cart;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartResponseDto {
    private Long id;
    private List<CartItemResponseDto> items;
    private Integer itemsCount;
    private BigDecimal totalPrice;
}