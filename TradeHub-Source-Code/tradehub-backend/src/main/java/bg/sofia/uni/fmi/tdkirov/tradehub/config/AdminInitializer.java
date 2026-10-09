package bg.sofia.uni.fmi.tdkirov.tradehub.config;

import bg.sofia.uni.fmi.tdkirov.tradehub.entity.user.Role;
import bg.sofia.uni.fmi.tdkirov.tradehub.entity.user.User;
import bg.sofia.uni.fmi.tdkirov.tradehub.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdminInitializer implements ApplicationRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${admin.username}")
    private String adminUsername;

    @Value("${admin.password}")
    private String adminPassword;

    @Override
    public void run(ApplicationArguments args) {
        if (userRepository.existsByUsername(adminUsername)) {
            return;
        }

        User admin = new User(
            adminUsername,
            adminUsername + "@tradehub.internal",
            "N/A",
            passwordEncoder.encode(adminPassword),
            Role.ADMIN
        );

        userRepository.save(admin);
    }
}
