package bg.sofia.uni.fmi.tdkirov.tradehub.controller;

import bg.sofia.uni.fmi.tdkirov.tradehub.dto.ApiResponse;
import bg.sofia.uni.fmi.tdkirov.tradehub.dto.MessageResponseDto;
import bg.sofia.uni.fmi.tdkirov.tradehub.dto.shopitem.ShopItemCreateDto;
import bg.sofia.uni.fmi.tdkirov.tradehub.dto.shopitem.ShopItemPreviewDto;
import bg.sofia.uni.fmi.tdkirov.tradehub.dto.shopitem.ShopItemResponseDto;
import bg.sofia.uni.fmi.tdkirov.tradehub.service.ShopItemService;
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
@RequestMapping("/api/v1/shop-items")
public class ShopItemController {
    private ShopItemService shopItemService;

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ShopItemResponseDto>> getShopItem(@PathVariable Long id) {
        ShopItemResponseDto result = shopItemService.getShopItem(id);

        MessageResponseDto msgResponseDto = new MessageResponseDto(
            "",
            String.format("Shop item with id (%d) was send. ", id));

        return ResponseEntity.ok(new ApiResponse<>(msgResponseDto, result));
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<ShopItemPreviewDto>> createShopItem(
            @Valid @RequestPart("item") ShopItemCreateDto createDto,
            @RequestPart("picture") MultipartFile picture,
            Principal principal) {
        ShopItemPreviewDto result = shopItemService.createShopItem(createDto, picture, principal.getName());

        MessageResponseDto msgResponseDto = new MessageResponseDto(
            "Item " + result.getName() + " has been created. ",
            String.format("Shop item with id (%d) has been created in shop (%d) by user: (%s). ",
                result.getId(), result.getShopId(), principal.getName()));

        return ResponseEntity.status(HttpStatus.CREATED)
            .body(new ApiResponse<>(msgResponseDto, result));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteShopItem(@PathVariable Long id, Principal principal) {
        shopItemService.deleteShopItem(id, principal.getName());

        MessageResponseDto msgResponseDto = new MessageResponseDto(
            "Item has been deleted. ",
            String.format("Shop item with id (%d) was deleted by user: (%s). ", id, principal.getName()));

        return ResponseEntity.ok(new ApiResponse<>(msgResponseDto, null));
    }
}