package bg.sofia.uni.fmi.tdkirov.tradehub.service;

import bg.sofia.uni.fmi.tdkirov.tradehub.dto.user.UserLoginDto;
import bg.sofia.uni.fmi.tdkirov.tradehub.dto.user.UserRegisterDto;

public interface AuthenticationService {
    /**
     * Takes a register data transfer object and with it
     * creates a user entity and saves it into the system.
     *
     * @param registerDto the register data transfer object, must not be null.
     *
     * @return the id of the new user in the database.
     */
    Long register(UserRegisterDto registerDto);

    /**
     * Validates the username and password and if correct return a login token.
     *
     * @param loginDto the login data transfer object, must not be null.
     *
     * @return the token for access to the system.
     */
    String login(UserLoginDto loginDto);
}
