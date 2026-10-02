
package bg.sofia.uni.fmi.tdkirov.tradehub.mapper;
import bg.sofia.uni.fmi.tdkirov.tradehub.dto.user.buyer.BuyerRegisterDto;
import bg.sofia.uni.fmi.tdkirov.tradehub.dto.user.buyer.BuyerResponseDto;
import bg.sofia.uni.fmi.tdkirov.tradehub.dto.user.seller.SellerInfoDto;
import bg.sofia.uni.fmi.tdkirov.tradehub.dto.user.seller.SellerRegisterDto;
import bg.sofia.uni.fmi.tdkirov.tradehub.dto.user.seller.SellerResponseDto;
import bg.sofia.uni.fmi.tdkirov.tradehub.dto.user.UserPreviewDto;
import bg.sofia.uni.fmi.tdkirov.tradehub.dto.user.UserRegisterDto;
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

    public SellerInfoDto toSellerInfo(SellerProfile seller) {
        return new SellerInfoDto(seller.getUserId(), seller.getDisplayName(),
            seller.getContactEmail(), seller.getContactPhone(),
            seller.getDescription(),
            dateMapper.toStringDateOnly(seller.getUser().getCreatedAt()));
    }

    public BuyerResponseDto toBuyerResponseDto(BuyerProfile buyer) {
        User user = buyer.getUser();

        return new BuyerResponseDto(user.getId(), user.getUsername(), user.getEmail(), user.getPhone(),
            dateMapper.toStringDateTime(user.getCreatedAt()), buyer.getFullName(), buyer.getShippingStreet(),
            buyer.getShippingCity(), buyer.getShippingPostalCode(), buyer.getShippingCountry());
    }

    public SellerResponseDto toSellerResponseDto(SellerProfile seller) {
        User user = seller.getUser();

        return new SellerResponseDto(user.getId(), user.getUsername(), user.getEmail(), user.getPhone(),
            dateMapper.toStringDateTime(user.getCreatedAt()), seller.getDisplayName(), seller.getContactEmail(),
            seller.getContactPhone(), seller.getDescription());

    }

    public UserPreviewDto toPreviewDto(User user) {
        return new UserPreviewDto(user.getUsername(), user.getRole().getRoleName());

    }
}

