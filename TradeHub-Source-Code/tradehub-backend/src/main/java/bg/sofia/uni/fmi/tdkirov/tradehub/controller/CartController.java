package bg.sofia.uni.fmi.tdkirov.tradehub.controller;

import bg.sofia.uni.fmi.tdkirov.tradehub.dto.ApiResponse;
import bg.sofia.uni.fmi.tdkirov.tradehub.dto.MessageResponseDto;
import bg.sofia.uni.fmi.tdkirov.tradehub.dto.cart.CartItemAddDto;
import bg.sofia.uni.fmi.tdkirov.tradehub.dto.cart.CartItemUpdateDto;
import bg.sofia.uni.fmi.tdkirov.tradehub.dto.cart.CartResponseDto;
import bg.sofia.uni.fmi.tdkirov.tradehub.service.CartService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/cart")
public class CartController {
    private CartService cartService;

    @GetMapping
    public ResponseEntity<ApiResponse<CartResponseDto>> getCart(Principal principal) {
        CartResponseDto result = cartService.getCart(principal.getName());

        MessageResponseDto msgResponseDto = new MessageResponseDto(
            "",
            String.format("Cart was send for user: (%s). ", principal.getName()));

        return ResponseEntity.ok(new ApiResponse<>(msgResponseDto, result));
    }

    @PostMapping("/items")
    public ResponseEntity<ApiResponse<CartResponseDto>> addItem(
            @Valid @RequestBody CartItemAddDto addDto,
            Principal principal) {
        CartResponseDto result = cartService.addItem(addDto, principal.getName());

        MessageResponseDto msgResponseDto = new MessageResponseDto(
            "The item has been added to your cart. ",
            String.format("Shop item (%d) x%d has been added to the cart of user: (%s). ",
                addDto.getItemId(), addDto.getQuantity(), principal.getName()));

        return ResponseEntity.ok(new ApiResponse<>(msgResponseDto, result));
    }

    @PatchMapping("/items/{cartItemId}")
    public ResponseEntity<ApiResponse<CartResponseDto>> updateItem(
            @PathVariable Long cartItemId,
            @Valid @RequestBody CartItemUpdateDto updateDto,
            Principal principal) {
        CartResponseDto result = cartService.updateItem(cartItemId, updateDto, principal.getName());

        MessageResponseDto msgResponseDto = new MessageResponseDto(
            "",
            String.format("Cart item (%d) quantity set to %d for user: (%s). ",
                cartItemId, updateDto.getQuantity(), principal.getName()));

        return ResponseEntity.ok(new ApiResponse<>(msgResponseDto, result));
    }

    @DeleteMapping("/items/{cartItemId}")
    public ResponseEntity<ApiResponse<CartResponseDto>> removeItem(
            @PathVariable Long cartItemId,
            Principal principal) {
        CartResponseDto result = cartService.removeItem(cartItemId, principal.getName());

        MessageResponseDto msgResponseDto = new MessageResponseDto(
            "The item has been removed from your cart. ",
            String.format("Cart item (%d) has been removed for user: (%s). ",
                cartItemId, principal.getName()));

        return ResponseEntity.ok(new ApiResponse<>(msgResponseDto, result));
    }

    @DeleteMapping
    public ResponseEntity<ApiResponse<CartResponseDto>> clearCart(Principal principal) {
        CartResponseDto result = cartService.clearCart(principal.getName());

        MessageResponseDto msgResponseDto = new MessageResponseDto(
            "Your cart has been emptied. ",
            String.format("Cart has been cleared for user: (%s). ", principal.getName()));

        return ResponseEntity.ok(new ApiResponse<>(msgResponseDto, result));
    }
}