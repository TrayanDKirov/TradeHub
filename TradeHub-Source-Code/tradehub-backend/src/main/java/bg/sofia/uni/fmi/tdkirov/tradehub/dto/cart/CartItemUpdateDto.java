package bg.sofia.uni.fmi.tdkirov.tradehub.dto.cart;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CartItemUpdateDto {
    @NotNull
    @Positive(message = "Quantity should be at least 1. ")
    private Integer quantity;
}