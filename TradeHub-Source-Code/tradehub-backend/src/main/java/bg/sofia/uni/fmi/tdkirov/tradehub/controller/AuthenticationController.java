package bg.sofia.uni.fmi.tdkirov.tradehub.controller;

import bg.sofia.uni.fmi.tdkirov.tradehub.dto.MessageResponseDto;
import bg.sofia.uni.fmi.tdkirov.tradehub.dto.user.BuyerRegisterDto;
import bg.sofia.uni.fmi.tdkirov.tradehub.dto.user.SellerRegisterDto;
import bg.sofia.uni.fmi.tdkirov.tradehub.dto.user.UserLoginDto;
import bg.sofia.uni.fmi.tdkirov.tradehub.entity.user.BuyerProfile;
import bg.sofia.uni.fmi.tdkirov.tradehub.entity.user.SellerProfile;
import bg.sofia.uni.fmi.tdkirov.tradehub.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.LinkedHashMap;
import java.util.Map;

@Controller
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthenticationController {
    private AuthenticationService service;

    @PostMapping("/register/buyer")
    public ResponseEntity<MessageResponseDto> registerBuyer(@Valid @RequestBody BuyerRegisterDto registerDto) {
        BuyerProfile buyer = service.registerBuyer(registerDto);
        MessageResponseDto responseDto = new MessageResponseDto(
            "User account for " + buyer.getUser().getUsername() + " has been created. ",
            "User-buyer has been added to the system: " +
            String.format("(%d, %s). ", buyer.getUserId(), buyer.getUser().getUsername()));

        return ResponseEntity.ok(responseDto);
    }

    @PostMapping("/register/seller")
    public ResponseEntity<MessageResponseDto> registerSeller(@Valid @RequestBody SellerRegisterDto registerDto) {
        SellerProfile seller = service.registerSeller(registerDto);
        MessageResponseDto responseDto = new MessageResponseDto(
            "User account for " + seller.getUser().getUsername() + " has been created. ",
            "User-seller has been added to the system: " +
                String.format("(%d, %s). ", seller.getUserId(), seller.getUser().getUsername()));

        return ResponseEntity.ok(responseDto);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@Valid @RequestBody UserLoginDto loginDto) {
        String token = service.login(loginDto);
        MessageResponseDto messageDto = new MessageResponseDto(
            "You have logged in account for " + loginDto.getUsername() + ". ",
            "Token has been given for " +
                String.format("(%s). ", loginDto.getUsername()));

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("message", messageDto);
        result.put("token", token);

        return ResponseEntity.ok(result);
    }
}
