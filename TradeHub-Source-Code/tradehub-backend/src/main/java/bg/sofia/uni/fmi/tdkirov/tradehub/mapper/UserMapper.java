
package bg.sofia.uni.fmi.tdkirov.tradehub.mapper;
import bg.sofia.uni.fmi.tdkirov.tradehub.dto.user.UserRegisterDto;
import bg.sofia.uni.fmi.tdkirov.tradehub.dto.user.UserResponseDto;
import bg.sofia.uni.fmi.tdkirov.tradehub.entity.user.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UserMapper {
    private DateMapper dateMapper;

    public User toEntity(UserRegisterDto registerDto) {
        return new User(registerDto.getUsername(), registerDto.getPassword());
    }

    public UserResponseDto toResponseDto(User user) {
        return new UserResponseDto(user.getId(), user.getUsername(),
            dateMapper.toStringDateTime(user.getCreatedAt()));
    }
}
