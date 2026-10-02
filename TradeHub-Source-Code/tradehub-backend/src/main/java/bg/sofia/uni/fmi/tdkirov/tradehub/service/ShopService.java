package bg.sofia.uni.fmi.tdkirov.tradehub.service;

import bg.sofia.uni.fmi.tdkirov.tradehub.dto.shop.ShopCreateDto;
import bg.sofia.uni.fmi.tdkirov.tradehub.dto.shop.ShopPreviewDto;
import bg.sofia.uni.fmi.tdkirov.tradehub.dto.shop.ShopResponseDto;
import bg.sofia.uni.fmi.tdkirov.tradehub.exception.shop.ShopNotFound;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ShopService {
    /**
     * Takes a shop create data transfer object, creates a shop and saves it in the database.
     *
     *
     * @param createDto the create dta transfer object for the shop.
     * @param picture file of the picture for the shop, must not be null.
     * @param currUsername the username of the current user.
     *
     * @return the buyer response data transfer object.
     */
    ShopResponseDto createShop(ShopCreateDto createDto, MultipartFile picture, String currUsername);

    /**
     * Retrieves all the shops of this user from the database,
     * maps each of them to a shop preview data transfer object and returns it.
     *
     * @param userId the identifier of the user, must not be null
     *
     *
     * @return a list of shop preview data transfer objects.
     */
    List<ShopPreviewDto> getAllShopsOfUser(Long userId);

    /**
     * Retrieves all the shops of the current user from the database,
     * maps each of them to a shop preview data transfer object and returns it.
     *
     * @param currUsername the username of the current user, must not be null
     *
     * @return a list of shop preview data transfer objects.
     */
    List<ShopPreviewDto> getAllShopsOfUser(String currUsername);

    /**
     * Retrieves the shop with this identifier from the database,
     * maps it to a shop response data transfer object and returns it.
     *
     *
     * @param id the identifier for the shop, must not be null.
     *
     * @throws ShopNotFound if a shop is not found.
     *
     * @return the buyer response data transfer object.
     */
    ShopResponseDto getShopById(Long id);
}
