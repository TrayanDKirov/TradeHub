package bg.sofia.uni.fmi.tdkirov.tradehub.service;

import bg.sofia.uni.fmi.tdkirov.tradehub.dto.shopitem.ShopItemCreateDto;
import bg.sofia.uni.fmi.tdkirov.tradehub.dto.shopitem.ShopItemPreviewDto;
import bg.sofia.uni.fmi.tdkirov.tradehub.dto.shopitem.ShopItemResponseDto;
import bg.sofia.uni.fmi.tdkirov.tradehub.exception.shop.ShopAccessDenied;
import bg.sofia.uni.fmi.tdkirov.tradehub.exception.shop.ShopNotFound;
import bg.sofia.uni.fmi.tdkirov.tradehub.exception.shopitem.ShopItemNotFound;
import bg.sofia.uni.fmi.tdkirov.tradehub.exception.user.seller.SellerNotFound;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ShopItemService {
    /**
     * Takes a shop item create data transfer object, creates a shop item
     * in the shop with the given identifier and saves it in the database.
     *
     * @param createDto       the create data transfer object for the shop item.
     * @param picture         file of the picture for the shop item, must not be null.
     * @param currentUsername the username of the current user.
     *
     * @throws SellerNotFound   if the current user is not a seller.
     * @throws ShopNotFound     if the shop is not found.
     * @throws ShopAccessDenied if the shop is not owned by the current user.
     *
     * @return the shop item preview data transfer object.
     */
    ShopItemPreviewDto createShopItem(ShopItemCreateDto createDto, MultipartFile picture, String currentUsername);

    /**
     * Retrieves all the items of the shop with this identifier from the database,
     * maps each of them to a shop item preview data transfer object and returns it.
     *
     * @param shopId the identifier of the shop, must not be null.
     *
     * @throws ShopNotFound if the shop is not found.
     *
     * @return a list of shop item preview data transfer objects.
     */
    List<ShopItemPreviewDto> getShopItems(Long shopId);

    /**
     * Retrieves the shop item with this identifier from the database,
     * maps it to a shop item response data transfer object and returns it.
     *
     * @param id the identifier of the shop item, must not be null.
     *
     * @throws ShopItemNotFound if the shop item is not found.
     *
     * @return the shop item response data transfer object.
     */
    ShopItemResponseDto getShopItem(Long id);

    /**
     * Deletes the shop item with the given identifier.
     * Only the shop owner or an admin may delete an item.
     *
     * @param id              the identifier of the shop item.
     * @param currUsername    the username of the requesting user.
     *
     * @throws ShopItemNotFound  if the shop item does not exist.
     * @throws ShopAccessDenied  if the user neither owns the shop nor is an admin.
     */
    void deleteShopItem(Long id, String currUsername);
}