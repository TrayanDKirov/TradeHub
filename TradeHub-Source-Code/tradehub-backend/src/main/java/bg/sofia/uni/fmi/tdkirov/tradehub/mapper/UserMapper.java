
package bg.sofia.uni.fmi.tdkirov.tradehub.mapper;
import bg.sofia.uni.fmi.tdkirov.tradehub.dto.user.BuyerRegisterDto;
import bg.sofia.uni.fmi.tdkirov.tradehub.dto.user.SellerRegisterDto;
import bg.sofia.uni.fmi.tdkirov.tradehub.dto.user.UserRegisterDto;
import bg.sofia.uni.fmi.tdkirov.tradehub.dto.user.UserResponseDto;
import bg.sofia.uni.fmi.tdkirov.tradehub.entity.user.BuyerProfile;
import bg.sofia.uni.fmi.tdkirov.tradehub.entity.user.Role;
import bg.sofia.uni.fmi.tdkirov.tradehub.entity.user.SellerProfile;
import bg.sofia.uni.fmi.tdkirov.tradehub.entity.user.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UserMapper {
    private DateMapper dateMapper;

    public User toUser(UserRegisterDto registerDto, Role role) {
        return new User(registerDto.getUsername(), registerDto.getEmail(),
            registerDto.getPhone(), registerDto.getPassword(), role);
    }

    public BuyerProfile toBuyer(User user, BuyerRegisterDto registerDto) {
        return new BuyerProfile(user, registerDto.getFullName(),
            registerDto.getShippingStreet(), registerDto.getShippingCity(),
            registerDto.getShippingPostalCode(), registerDto.getShippingCountry());
    }

    public SellerProfile toSeller(User user, SellerRegisterDto registerDto) {
        return new SellerProfile(user, registerDto.getDisplayName(),
            registerDto.getContactEmail(), registerDto.getContactPhone(),
            registerDto.getDescription());
    }

    public UserResponseDto toResponseDto(User user) {
        return new UserResponseDto(user.getId(), user.getUsername(),
            dateMapper.toStringDateTime(user.getCreatedAt()));
    }
}
