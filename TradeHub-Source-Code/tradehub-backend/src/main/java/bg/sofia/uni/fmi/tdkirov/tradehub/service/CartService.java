package bg.sofia.uni.fmi.tdkirov.tradehub.service;

import bg.sofia.uni.fmi.tdkirov.tradehub.dto.cart.CartItemAddDto;
import bg.sofia.uni.fmi.tdkirov.tradehub.dto.cart.CartItemUpdateDto;
import bg.sofia.uni.fmi.tdkirov.tradehub.dto.cart.CartResponseDto;
import bg.sofia.uni.fmi.tdkirov.tradehub.exception.cart.CartItemNotFound;
import bg.sofia.uni.fmi.tdkirov.tradehub.exception.cart.InsufficientStock;
import bg.sofia.uni.fmi.tdkirov.tradehub.exception.shopitem.ShopItemNotFound;
import bg.sofia.uni.fmi.tdkirov.tradehub.exception.user.buyer.BuyerNotFoundException;

public interface CartService {
    /**
     * Retrieves the shopping cart of the current user from the database,
     * creating an empty one if the user does not have a cart yet.
     *
     * @param currUsername the username of the current user.
     *
     * @throws BuyerNotFoundException if the current user is not a buyer.
     *
     * @return the cart response data transfer object.
     */
    CartResponseDto getCart(String currUsername);

    /**
     * Adds a shop item to the cart of the current user. If the item is already
     * in the cart, its quantity is increased instead.
     *
     * @param addDto       the item and the quantity to add.
     * @param currUsername the username of the current user.
     *
     * @throws BuyerNotFoundException if the current user is not a buyer.
     * @throws ShopItemNotFound       if the shop item is not found.
     * @throws InsufficientStock      if the quantity in the cart would exceed the stock.
     *
     * @return the updated cart response data transfer object.
     */
    CartResponseDto addItem(CartItemAddDto addDto, String currUsername);

    /**
     * Sets the quantity of an item in the cart of the current user.
     *
     * @param cartItemId   the identifier of the cart item.
     * @param updateDto    the new quantity.
     * @param currUsername the username of the current user.
     *
     * @throws BuyerNotFoundException if the current user is not a buyer.
     * @throws CartItemNotFound       if the item is not in the cart of the current user.
     * @throws InsufficientStock      if the quantity exceeds the stock.
     *
     * @return the updated cart response data transfer object.
     */
    CartResponseDto updateItem(Long cartItemId, CartItemUpdateDto updateDto, String currUsername);

    /**
     * Removes an item from the cart of the current user.
     *
     * @param cartItemId   the identifier of the cart item.
     * @param currUsername the username of the current user.
     *
     * @throws BuyerNotFoundException if the current user is not a buyer.
     * @throws CartItemNotFound       if the item is not in the cart of the current user.
     *
     * @return the updated cart response data transfer object.
     */
    CartResponseDto removeItem(Long cartItemId, String currUsername);

    /**
     * Removes all the items from the cart of the current user.
     *
     * @param currUsername the username of the current user.
     *
     * @throws BuyerNotFoundException if the current user is not a buyer.
     *
     * @return the empty cart response data transfer object.
     */
    CartResponseDto clearCart(String currUsername);
}