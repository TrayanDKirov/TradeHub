package bg.sofia.uni.fmi.tdkirov.tradehub.controller;

import bg.sofia.uni.fmi.tdkirov.tradehub.dto.ApiResponse;
import bg.sofia.uni.fmi.tdkirov.tradehub.dto.MessageResponseDto;
import bg.sofia.uni.fmi.tdkirov.tradehub.dto.user.buyer.BuyerRegisterDto;
import bg.sofia.uni.fmi.tdkirov.tradehub.dto.user.seller.SellerRegisterDto;
import bg.sofia.uni.fmi.tdkirov.tradehub.dto.user.UserLoginDto;
import bg.sofia.uni.fmi.tdkirov.tradehub.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/api/v1/auth")
@AllArgsConstructor
public class AuthenticationController {
    private AuthenticationService service;

    @PostMapping("/register/buyer")
    public ResponseEntity<ApiResponse<String>> registerBuyer(@Valid @RequestBody BuyerRegisterDto registerDto) {
        String token = service.registerBuyer(registerDto);
        MessageResponseDto msgResponseDto = new MessageResponseDto(
            "User account for " + registerDto.getUsername() + " has been created. ",
            "User-buyer has been added to the system: " +
            String.format("(%s). ", registerDto.getUsername()));

        return ResponseEntity.ok(new ApiResponse<>(msgResponseDto, token));
    }

    @PostMapping("/register/seller")
    public ResponseEntity<ApiResponse<String>> registerSeller(@Valid @RequestBody SellerRegisterDto registerDto) {
        String token = service.registerSeller(registerDto);
        MessageResponseDto msgResponseDto = new MessageResponseDto(
            "User account for " + registerDto.getUsername() + " has been created. ",
            "User-seller has been added to the system: " +
                String.format("(%s). ", registerDto.getUsername()));

        return ResponseEntity.ok(new ApiResponse<>(msgResponseDto, token));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<String>> login(@Valid @RequestBody UserLoginDto loginDto) {
        String token = service.login(loginDto);
        MessageResponseDto messageDto = new MessageResponseDto(
            "You have logged in account for " + loginDto.getUsername() + ". ",
            "Token has been given for " +
                String.format("(%s). ", loginDto.getUsername()));

        return ResponseEntity.ok(new ApiResponse<>(messageDto, token));
    }
}
