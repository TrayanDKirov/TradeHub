package bg.sofia.uni.fmi.tdkirov.tradehub.config;

import bg.sofia.uni.fmi.tdkirov.tradehub.entity.cart.ShoppingCart;
import bg.sofia.uni.fmi.tdkirov.tradehub.entity.shop.Shop;
import bg.sofia.uni.fmi.tdkirov.tradehub.entity.shop.ShopItem;
import bg.sofia.uni.fmi.tdkirov.tradehub.entity.user.BuyerProfile;
import bg.sofia.uni.fmi.tdkirov.tradehub.entity.user.Role;
import bg.sofia.uni.fmi.tdkirov.tradehub.entity.user.SellerProfile;
import bg.sofia.uni.fmi.tdkirov.tradehub.entity.user.User;
import bg.sofia.uni.fmi.tdkirov.tradehub.repository.BuyerProfileRepository;
import bg.sofia.uni.fmi.tdkirov.tradehub.repository.SellerProfileRepository;
import bg.sofia.uni.fmi.tdkirov.tradehub.repository.ShopItemRepository;
import bg.sofia.uni.fmi.tdkirov.tradehub.repository.ShopRepository;
import bg.sofia.uni.fmi.tdkirov.tradehub.repository.ShoppingCartRepository;
import bg.sofia.uni.fmi.tdkirov.tradehub.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
@Order(2)
@RequiredArgsConstructor
public class DataSeeder implements ApplicationRunner {

    private final UserRepository userRepository;
    private final BuyerProfileRepository buyerProfileRepository;
    private final SellerProfileRepository sellerProfileRepository;
    private final ShopRepository shopRepository;
    private final ShopItemRepository shopItemRepository;
    private final ShoppingCartRepository shoppingCartRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${app.upload-dir:uploads}")
    private String uploadDir;

    @Override
    public void run(ApplicationArguments args) {
        if (userRepository.existsByUsername("buyer1")) {
            return;
        }

        copySeedImages();
        seedBuyers();
        seedSellers();
    }

    private void copySeedImages() {
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        Path root = Paths.get(uploadDir).toAbsolutePath().normalize();

        try {
            for (Resource resource : resolver.getResources("classpath:seed-images/**/*.{jpg,jpeg,png,webp}")) {
                String filename = resource.getFilename();
                if (filename == null) continue;
                String uri = resource.getURI().toString();
                String subDir = uri.contains("/shops/") ? "shops" : "items";

                Path dest = root.resolve(subDir).resolve(filename);
                Files.createDirectories(dest.getParent());
                if (!Files.exists(dest)) {
                    try (InputStream in = resource.getInputStream()) {
                        Files.copy(in, dest);
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to copy seed images", e);
        }
    }

    private void seedBuyers() {
        User u1 = userRepository.save(new User(
            "buyer1", "buyer1@example.com", "+359 88 111 1111",
            passwordEncoder.encode("password123"), Role.BUYER));
        BuyerProfile bp1 = buyerProfileRepository.save(new BuyerProfile(
            u1, "Alice Johnson", "15 Vitosha Blvd", "Sofia", "1000", "Bulgaria"));
        shoppingCartRepository.save(new ShoppingCart(bp1));

        User u2 = userRepository.save(new User(
            "buyer2", "buyer2@example.com", "+359 88 222 2222",
            passwordEncoder.encode("password123"), Role.BUYER));
        BuyerProfile bp2 = buyerProfileRepository.save(new BuyerProfile(
            u2, "Bob Smith", "8 Maritsa St", "Plovdiv", "4000", "Bulgaria"));
        shoppingCartRepository.save(new ShoppingCart(bp2));
    }

    private void seedSellers() {
        User u1 = userRepository.save(new User(
            "seller1", "seller1@example.com", "+359 88 333 3333",
            passwordEncoder.encode("password123"), Role.SELLER));
        SellerProfile sp1 = sellerProfileRepository.save(new SellerProfile(
            u1, "TechGadgets", "seller1@example.com",
            "+359 88 333 3333", "Your one-stop shop for tech accessories."));

        Shop electronics = shopRepository.save(new Shop("TechGadgets Electronics", "shops/tech-electronics.jpg", sp1));
        shopItemRepository.save(new ShopItem("Wireless Mouse", "items/wireless-mouse.jpg",
            new BigDecimal("25.99"), 50,
            "Ergonomic wireless mouse with 2.4 GHz connection.", electronics, sp1));
        shopItemRepository.save(new ShopItem("USB-C Hub", "items/usb-hub.jpg",
            new BigDecimal("39.99"), 30,
            "7-in-1 USB-C hub with HDMI, USB 3.0, and SD card slots.", electronics, sp1));
        shopItemRepository.save(new ShopItem("Mechanical Keyboard", "items/keyboard.jpg",
            new BigDecimal("79.99"), 20,
            "Compact TKL mechanical keyboard with blue switches.", electronics, sp1));

        Shop accessories = shopRepository.save(new Shop("TechGadgets Accessories", "shops/tech-accessories.jpg", sp1));
        shopItemRepository.save(new ShopItem("Phone Stand", "items/phone-stand.jpg",
            new BigDecimal("12.99"), 100,
            "Adjustable aluminium phone and tablet stand.", accessories, sp1));
        shopItemRepository.save(new ShopItem("Cable Organizer", "items/cable-organizer.jpg",
            new BigDecimal("8.49"), 200,
            "Set of 10 reusable silicone cable ties.", accessories, sp1));
        shopItemRepository.save(new ShopItem("Webcam HD", "items/webcam.jpg",
            new BigDecimal("54.99"), 15,
            "1080p webcam with built-in microphone and auto-focus.", accessories, sp1));

        User u2 = userRepository.save(new User(
            "seller2", "seller2@example.com", "+359 88 444 4444",
            passwordEncoder.encode("password123"), Role.SELLER));
        SellerProfile sp2 = sellerProfileRepository.save(new SellerProfile(
            u2, "HomeEssentials", "seller2@example.com",
            "+359 88 444 4444", "Quality home goods for everyday living."));

        Shop kitchen = shopRepository.save(new Shop("HomeEssentials Kitchen", "shops/home-kitchen.jpg", sp2));
        shopItemRepository.save(new ShopItem("Cutting Board", "items/cutting-board.jpg",
            new BigDecimal("18.99"), 60,
            "Large bamboo cutting board with juice groove.", kitchen, sp2));
        shopItemRepository.save(new ShopItem("Kitchen Scale", "items/kitchen-scale.jpg",
            new BigDecimal("22.50"), 40,
            "Digital kitchen scale accurate to 1 g, up to 5 kg.", kitchen, sp2));
        shopItemRepository.save(new ShopItem("Coffee Maker", "items/coffee-maker.jpg",
            new BigDecimal("89.99"), 10,
            "12-cup programmable drip coffee maker with thermal carafe.", kitchen, sp2));

        Shop living = shopRepository.save(new Shop("HomeEssentials Living", "shops/home-living.jpg", sp2));
        shopItemRepository.save(new ShopItem("Scented Candle", "items/candle.jpg",
            new BigDecimal("14.99"), 80,
            "100% soy wax candle, lavender & vanilla scent, 40-hour burn.", living, sp2));
        shopItemRepository.save(new ShopItem("Picture Frame", "items/picture-frame.jpg",
            new BigDecimal("9.99"), 120,
            "Classic wooden picture frame, 10x15 cm.", living, sp2));
        shopItemRepository.save(new ShopItem("Throw Pillow", "items/throw-pillow.jpg",
            new BigDecimal("19.99"), 45,
            "Soft velvet throw pillow, 45x45 cm, multiple colours.", living, sp2));
    }
}