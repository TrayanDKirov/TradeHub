package bg.sofia.uni.fmi.tdkirov.tradehub.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SellerRegisterDto extends UserRegisterDto {
    @NotBlank
    private String displayName;

    @Email(message = "Contact email should be valid. ")
    private String contactEmail;
    private String contactPhone;

    private String description;
}