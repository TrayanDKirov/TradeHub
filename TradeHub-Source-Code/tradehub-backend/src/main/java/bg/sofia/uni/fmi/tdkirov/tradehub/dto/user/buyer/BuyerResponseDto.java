package bg.sofia.uni.fmi.tdkirov.tradehub.dto.user.buyer;

import bg.sofia.uni.fmi.tdkirov.tradehub.dto.user.UserResponseDto;
import lombok.Data;

@Data
public class BuyerResponseDto extends UserResponseDto {
    private String fullName;

    private String shippingStreet;
    private String shippingCity;
    private String shippingPostalCode;
    private String shippingCountry;

    public BuyerResponseDto(Long id, String username, String email,
                            String phone, String createdAt, String fullName,
                            String shippingStreet, String shippingCity,
                            String shippingPostalCode, String shippingCountry) {
        super(id, username, email, phone, createdAt);

        this.fullName = fullName;
        this.shippingStreet = shippingStreet;
        this.shippingCity = shippingCity;
        this.shippingPostalCode = shippingPostalCode;
        this.shippingCountry = shippingCountry;
    }
}