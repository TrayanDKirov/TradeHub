package bg.sofia.uni.fmi.tdkirov.tradehub.dto.shop;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ShopCreateDto {
    @NotBlank
    @Size(min = 3, max = 50, message = "Shop name should be from 3 to 50 characters. ")
    private String name;
}