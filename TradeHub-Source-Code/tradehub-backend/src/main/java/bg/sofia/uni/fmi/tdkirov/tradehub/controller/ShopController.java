package bg.sofia.uni.fmi.tdkirov.tradehub.controller;

import bg.sofia.uni.fmi.tdkirov.tradehub.dto.ApiResponse;
import bg.sofia.uni.fmi.tdkirov.tradehub.dto.MessageResponseDto;
import bg.sofia.uni.fmi.tdkirov.tradehub.dto.shop.ShopCreateDto;
import bg.sofia.uni.fmi.tdkirov.tradehub.dto.shop.ShopPreviewDto;
import bg.sofia.uni.fmi.tdkirov.tradehub.dto.shop.ShopResponseDto;
import bg.sofia.uni.fmi.tdkirov.tradehub.service.ShopService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/shops")
public class ShopController {
    private ShopService shopService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<ShopPreviewDto>>> getShops(
            @RequestParam(required = false) Long userId) {
        List<ShopPreviewDto> result = userId == null
            ? shopService.getAllShops()
            : shopService.getAllShopsOfUser(userId);

        MessageResponseDto msgResponseDto = new MessageResponseDto(
            "",
            userId == null
                ? "All shops were send. "
                : String.format("Shops were send for user with id: (%d). ", userId));

        return ResponseEntity.ok(new ApiResponse<>(msgResponseDto, result));
    }

    @GetMapping("/my")
    public ResponseEntity<ApiResponse<List<ShopPreviewDto>>> getMyShops(Principal principal) {
        List<ShopPreviewDto> result = shopService.getAllShopsOfUser(principal.getName());

        MessageResponseDto msgResponseDto = new MessageResponseDto(
            "",
            String.format("Shops were send for user: (%s). ", principal.getName()));

        return ResponseEntity.ok(new ApiResponse<>(msgResponseDto, result));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ShopResponseDto>> getShop(@PathVariable Long id) {
        ShopResponseDto result = shopService.getShopById(id);

        MessageResponseDto msgResponseDto = new MessageResponseDto(
            "",
            String.format("Shop with id (%d) was send. ", id));

        return ResponseEntity.ok(new ApiResponse<>(msgResponseDto, result));
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<ShopPreviewDto>> createShop(
            @Valid @RequestPart("shop") ShopCreateDto createDto,
            @RequestPart("picture") MultipartFile picture,
            Principal principal) {
        ShopPreviewDto result = shopService.createShop(createDto, picture, principal.getName());

        MessageResponseDto msgResponseDto = new MessageResponseDto(
            "Shop " + result.getName() + " has been created. ",
            String.format("Shop with id (%d) has been created by user: (%s). ",
                result.getId(), principal.getName()));

        return ResponseEntity.status(HttpStatus.CREATED)
            .body(new ApiResponse<>(msgResponseDto, result));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteShop(@PathVariable Long id, Principal principal) {
        shopService.deleteShop(id, principal.getName());

        MessageResponseDto msgResponseDto = new MessageResponseDto(
            "The shop has been deleted. ",
            String.format("Shop with id (%d) has been deleted by user: (%s). ",
                id, principal.getName()));

        return ResponseEntity.ok(new ApiResponse<>(msgResponseDto, null));
    }
}