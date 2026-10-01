package bg.sofia.uni.fmi.tdkirov.tradehub.service;

import bg.sofia.uni.fmi.tdkirov.tradehub.dto.user.BuyerRegisterDto;
import bg.sofia.uni.fmi.tdkirov.tradehub.dto.user.BuyerResponseDto;
import bg.sofia.uni.fmi.tdkirov.tradehub.dto.user.SellerRegisterDto;
import bg.sofia.uni.fmi.tdkirov.tradehub.dto.user.SellerResponseDto;
import bg.sofia.uni.fmi.tdkirov.tradehub.dto.user.UserLoginDto;
import bg.sofia.uni.fmi.tdkirov.tradehub.entity.user.BuyerProfile;
import bg.sofia.uni.fmi.tdkirov.tradehub.entity.user.SellerProfile;

public interface AuthenticationService {
    /**
     * Takes a register data transfer object for a buyer and with it
     * creates a user entity and saves it into the system.
     *
     * @param registerDto the register data transfer object for buyer, must not be null.
     *
     * @return the token for access to the system.
     */
    String registerBuyer(BuyerRegisterDto registerDto);

    /**
     * Takes a register data transfer object for a seller and with it
     * creates a user entity and saves it into the system.
     *
     * @param registerDto the register data transfer object for seller, must not be null.
     *
     * @return the token for access to the system.
     */
    String registerSeller(SellerRegisterDto registerDto);

    /**
     * Validates the username and password and if correct return a login token.
     *
     * @param loginDto the login data transfer object, must not be null.
     *
     * @return the token for access to the system.
     */
    String login(UserLoginDto loginDto);
}
