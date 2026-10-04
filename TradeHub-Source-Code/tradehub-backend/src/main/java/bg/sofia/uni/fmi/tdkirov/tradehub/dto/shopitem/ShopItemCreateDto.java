package bg.sofia.uni.fmi.tdkirov.tradehub.dto.shopitem;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class ShopItemCreateDto {
    @NotBlank
    @Size(min = 6, max = 25, message = "Item name should be from 6 to 25 characters. ")
    private String name;
    @NotNull
    @DecimalMin(value = "0.01", message = "Price should be positive. ")
    @Digits(integer = 8, fraction = 2, message = "Price should have at most 8 digits and 2 decimals. ")
    private BigDecimal price;
    @NotNull
    @PositiveOrZero(message = "Quantity should not be negative. ")
    private Integer quantity;
    @NotBlank
    private String description;
    @NotNull
    private Long shopId;
}