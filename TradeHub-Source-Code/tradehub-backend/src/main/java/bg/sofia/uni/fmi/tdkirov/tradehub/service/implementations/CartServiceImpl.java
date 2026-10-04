package bg.sofia.uni.fmi.tdkirov.tradehub.service.implementations;

import bg.sofia.uni.fmi.tdkirov.tradehub.dto.cart.CartItemAddDto;
import bg.sofia.uni.fmi.tdkirov.tradehub.dto.cart.CartItemUpdateDto;
import bg.sofia.uni.fmi.tdkirov.tradehub.dto.cart.CartResponseDto;
import bg.sofia.uni.fmi.tdkirov.tradehub.entity.cart.CartItem;
import bg.sofia.uni.fmi.tdkirov.tradehub.entity.cart.ShoppingCart;
import bg.sofia.uni.fmi.tdkirov.tradehub.entity.shop.ShopItem;
import bg.sofia.uni.fmi.tdkirov.tradehub.entity.user.BuyerProfile;
import bg.sofia.uni.fmi.tdkirov.tradehub.exception.cart.CartItemNotFound;
import bg.sofia.uni.fmi.tdkirov.tradehub.exception.cart.InsufficientStock;
import bg.sofia.uni.fmi.tdkirov.tradehub.exception.shopitem.ShopItemNotFound;
import bg.sofia.uni.fmi.tdkirov.tradehub.exception.user.buyer.BuyerNotFoundException;
import bg.sofia.uni.fmi.tdkirov.tradehub.mapper.CartMapper;
import bg.sofia.uni.fmi.tdkirov.tradehub.repository.BuyerProfileRepository;
import bg.sofia.uni.fmi.tdkirov.tradehub.repository.CartItemRepository;
import bg.sofia.uni.fmi.tdkirov.tradehub.repository.ShopItemRepository;
import bg.sofia.uni.fmi.tdkirov.tradehub.repository.ShoppingCartRepository;
import bg.sofia.uni.fmi.tdkirov.tradehub.service.CartService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class CartServiceImpl implements CartService {
    private ShoppingCartRepository repository;
    private CartItemRepository cartItemRepository;
    private ShopItemRepository shopItemRepository;
    private BuyerProfileRepository buyerRepository;
    private CartMapper mapper;

    @Override
    public CartResponseDto getCart(String currUsername) {
        return mapper.toResponseDto(getOrCreateCart(currUsername));
    }

    @Override
    public CartResponseDto addItem(CartItemAddDto addDto, String currUsername) {
        ShoppingCart cart = getOrCreateCart(currUsername);

        ShopItem item = shopItemRepository.findById(addDto.getItemId())
            .orElseThrow(() -> new ShopItemNotFound("The shop item is missing. "));

        Optional<CartItem> existing = cart.getItems().stream()
            .filter(cartItem -> cartItem.getItem().getId().equals(item.getId()))
            .findFirst();

        if (existing.isPresent()) {
            int newQuantity = existing.get().getQuantity() + addDto.getQuantity();
            checkStock(item, newQuantity);
            existing.get().setQuantity(newQuantity);
        } else {
            checkStock(item, addDto.getQuantity());
            CartItem cartItem = cartItemRepository.save(new CartItem(item, addDto.getQuantity(), cart));
            cart.getItems().add(cartItem);
        }

        return mapper.toResponseDto(cart);
    }

    @Override
    public CartResponseDto updateItem(Long cartItemId, CartItemUpdateDto updateDto, String currUsername) {
        ShoppingCart cart = getOrCreateCart(currUsername);
        CartItem cartItem = findInCart(cart, cartItemId);

        checkStock(cartItem.getItem(), updateDto.getQuantity());
        cartItem.setQuantity(updateDto.getQuantity());

        return mapper.toResponseDto(cart);
    }

    @Override
    public CartResponseDto removeItem(Long cartItemId, String currUsername) {
        ShoppingCart cart = getOrCreateCart(currUsername);
        CartItem cartItem = findInCart(cart, cartItemId);

        cart.getItems().remove(cartItem);

        return mapper.toResponseDto(cart);
    }

    @Override
    public CartResponseDto clearCart(String currUsername) {
        ShoppingCart cart = getOrCreateCart(currUsername);

        cart.getItems().clear();

        return mapper.toResponseDto(cart);
    }

    private ShoppingCart getOrCreateCart(String currUsername) {
        return repository.findByBuyerUserUsername(currUsername)
            .orElseGet(() -> {
                BuyerProfile buyer = buyerRepository.findByUserUsername(currUsername)
                    .orElseThrow(() -> new BuyerNotFoundException(String.format(
                        "There is no buyer with this username - %s",
                        currUsername)));

                return repository.save(new ShoppingCart(buyer));
            });
    }

    private CartItem findInCart(ShoppingCart cart, Long cartItemId) {
        return cart.getItems().stream()
            .filter(cartItem -> cartItem.getId().equals(cartItemId))
            .findFirst()
            .orElseThrow(() -> new CartItemNotFound("The item is not in your cart. "));
    }

    private void checkStock(ShopItem item, int quantity) {
        if (quantity > item.getQuantity()) {
            throw new InsufficientStock(String.format(
                "Only %d of %s left in stock. ",
                item.getQuantity(), item.getName()));
        }
    }
}