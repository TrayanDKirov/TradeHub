package bg.sofia.uni.fmi.tdkirov.tradehub.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserRegisterDto {
    @NotBlank
    @Size(min = 3, max = 20, message = "Username should be from 3 to 20 characters. ")
    private String username;
    @NotBlank
    @Size(min = 6, max = 20, message = "Password should be from 6 to 20 characters. ")
    private String password;
}
