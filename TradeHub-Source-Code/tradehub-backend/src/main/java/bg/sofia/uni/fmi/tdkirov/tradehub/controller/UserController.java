
package bg.sofia.uni.fmi.tdkirov.tradehub.controller;

import bg.sofia.uni.fmi.tdkirov.tradehub.dto.ApiResponse;
import bg.sofia.uni.fmi.tdkirov.tradehub.dto.MessageResponseDto;
import bg.sofia.uni.fmi.tdkirov.tradehub.dto.user.BuyerResponseDto;
import bg.sofia.uni.fmi.tdkirov.tradehub.dto.user.SellerResponseDto;
import bg.sofia.uni.fmi.tdkirov.tradehub.dto.user.UserPreviewDto;
import bg.sofia.uni.fmi.tdkirov.tradehub.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    private UserService service;

    @RequestMapping("/preview")
    private ResponseEntity<ApiResponse<UserPreviewDto>> getUserPreview(Principal principal) {
        UserPreviewDto result = service.getUserPreview(principal.getName());

        MessageResponseDto msgResponseDto = new MessageResponseDto(
            "",
            "Preview was send for user: " +
                String.format("(%s). ", principal.getName()));

        return ResponseEntity.ok(new ApiResponse<>(msgResponseDto, result));
    }

    @RequestMapping("/buyer")
    private ResponseEntity<ApiResponse<BuyerResponseDto>> getBuyer(Principal principal) {
        BuyerResponseDto result = service.getBuyer(principal.getName());

        MessageResponseDto msgResponseDto = new MessageResponseDto(
            "",
            "Data for buyer was send for user: " +
                String.format("(%s). ", principal.getName()));

        return ResponseEntity.ok(new ApiResponse<>(msgResponseDto, result));
    }

    @RequestMapping("/seller")
    private ResponseEntity<ApiResponse<SellerResponseDto>> getSeller(Principal principal) {
        SellerResponseDto result = service.getSeller(principal.getName());

        MessageResponseDto msgResponseDto = new MessageResponseDto(
            "",
            "Data for seller was send for user: " +
                String.format("(%s). ", principal.getName()));

        return ResponseEntity.ok(new ApiResponse<>(msgResponseDto, result));
    }
}
