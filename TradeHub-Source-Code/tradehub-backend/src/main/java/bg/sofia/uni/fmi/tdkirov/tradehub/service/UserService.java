package bg.sofia.uni.fmi.tdkirov.tradehub.service;

import bg.sofia.uni.fmi.tdkirov.tradehub.dto.user.BuyerResponseDto;
import bg.sofia.uni.fmi.tdkirov.tradehub.dto.user.SellerResponseDto;
import bg.sofia.uni.fmi.tdkirov.tradehub.dto.user.UserPreviewDto;
import bg.sofia.uni.fmi.tdkirov.tradehub.entity.user.User;

import java.util.List;

;

public interface UserService {
    List<User> getUsers();

    /**
     * Retrieves data for the current user from the database and packages
     * it into user preview data transfer object.
     *
     * @param currUsername the username of the current user.
     *
     * @return the user preview data transfer object.
     */
    UserPreviewDto getUserPreview(String currUsername);

    /**
     * Retrieves data for the current user-buyer from the database and packages
     * it into buyer response data transfer object.
     *
     * @param currUsername the username of the current user.
     *
     * @return the buyer response data transfer object.
     */
    BuyerResponseDto getBuyer(String currUsername);

    /**
     * Retrieves data for the current user-seller from the database and packages
     * it into seller response data transfer object.
     *
     * @param currUsername the username of the current user.
     *
     * @return the buyer response data transfer object.
     */
    SellerResponseDto getSeller(String currUsername);
}
