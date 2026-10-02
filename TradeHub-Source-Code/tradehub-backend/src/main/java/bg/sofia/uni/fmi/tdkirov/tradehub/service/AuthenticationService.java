package bg.sofia.uni.fmi.tdkirov.tradehub.service;

import bg.sofia.uni.fmi.tdkirov.tradehub.dto.user.buyer.BuyerRegisterDto;
import bg.sofia.uni.fmi.tdkirov.tradehub.dto.user.seller.SellerRegisterDto;
import bg.sofia.uni.fmi.tdkirov.tradehub.dto.user.UserLoginDto;

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
