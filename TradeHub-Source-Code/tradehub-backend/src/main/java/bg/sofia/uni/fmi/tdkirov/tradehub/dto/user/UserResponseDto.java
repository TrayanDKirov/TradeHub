package bg.sofia.uni.fmi.tdkirov.tradehub.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserResponseDto {
    private Long id;
    private String username;

    private String createdAt;
}
