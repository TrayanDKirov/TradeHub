package bg.sofia.uni.fmi.tdkirov.tradehub.mapper;

import bg.sofia.uni.fmi.tdkirov.tradehub.dto.cart.CartItemResponseDto;
import bg.sofia.uni.fmi.tdkirov.tradehub.dto.cart.CartResponseDto;
import bg.sofia.uni.fmi.tdkirov.tradehub.entity.cart.CartItem;
import bg.sofia.uni.fmi.tdkirov.tradehub.entity.cart.ShoppingCart;
import bg.sofia.uni.fmi.tdkirov.tradehub.entity.shop.ShopItem;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Component
public class CartMapper {
    private PictureMapper pictureMapper;

    public CartItemResponseDto toItemResponseDto(CartItem cartItem) {
        ShopItem item = cartItem.getItem();

        return new CartItemResponseDto(cartItem.getId(), item.getId(), item.getName(),
            pictureMapper.toPictureUrl(item.getPicturePath()),
            item.getPrice(), cartItem.getQuantity(), item.getQuantity(),
            cartItem.getTotalPrice(),
            cartItem.getShop().getId(), cartItem.getShop().getName());
    }

    public CartResponseDto toResponseDto(ShoppingCart cart) {
        List<CartItemResponseDto> items = new ArrayList<>();
        BigDecimal totalPrice = BigDecimal.ZERO;
        int itemsCount = 0;

        for (CartItem cartItem : cart.getItems()) {
            items.add(toItemResponseDto(cartItem));
            totalPrice = totalPrice.add(cartItem.getTotalPrice());
            itemsCount += cartItem.getQuantity();
        }

        return new CartResponseDto(cart.getId(), items, itemsCount, totalPrice);
    }
}