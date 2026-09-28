package bg.sofia.uni.fmi.tdkirov.tradehub.dto.user;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BuyerRegisterDto extends UserRegisterDto {
    @NotBlank
    private String fullName;

    private String shippingStreet;
    private String shippingCity;
    private String shippingPostalCode;
    private String shippingCountry;
}