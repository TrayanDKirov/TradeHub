package bg.sofia.uni.fmi.tdkirov.tradehub.controller;

import bg.sofia.uni.fmi.tdkirov.tradehub.dto.MessageResponseDto;
import bg.sofia.uni.fmi.tdkirov.tradehub.dto.user.UserLoginDto;
import bg.sofia.uni.fmi.tdkirov.tradehub.dto.user.UserRegisterDto;
import bg.sofia.uni.fmi.tdkirov.tradehub.service.AuthenticationService;
import jakarta.validation.constraints.NotNull;
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

    @PostMapping("/register")
    public ResponseEntity<MessageResponseDto> register(@NotNull @RequestBody UserRegisterDto registerDto) {
        Long id = service.register(registerDto);
        MessageResponseDto responseDto = new MessageResponseDto(
            "User account for " + registerDto.getUsername() + " has been created. ",
            "User has been added to the system: " +
            String.format("(%d, %s). ", id, registerDto.getUsername()));

        return ResponseEntity.ok(responseDto);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@NotNull @RequestBody UserLoginDto loginDto) {
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
