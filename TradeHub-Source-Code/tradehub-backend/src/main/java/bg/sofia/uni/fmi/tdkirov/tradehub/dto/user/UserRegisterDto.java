package bg.sofia.uni.fmi.tdkirov.tradehub.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public abstract class UserRegisterDto {
    @NotBlank
    @Size(min = 3, max = 20, message = "Username should be from 3 to 20 characters. ")
    private String username;
    @NotBlank
    @Email(message = "Email should be valid. ")
    private String email;
    @NotBlank
    private String phone;
    @NotBlank
    @Size(min = 6, max = 20, message = "Password should be from 6 to 20 characters. ")
    private String password;
}