package bg.sofia.uni.fmi.tdkirov.tradehub.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Paths;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {
    private static final String UPLOADS_URL_PATTERN = "/uploads/**";

    private final String uploadDir;

    public WebConfiguration(@Value("${app.upload-dir:uploads}") String uploadDir) {
        this.uploadDir = uploadDir;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String location = Paths.get(uploadDir).toAbsolutePath().normalize().toUri().toString();

        if (!location.endsWith("/")) {
            location += "/";
        }

        registry.addResourceHandler(UPLOADS_URL_PATTERN)
            .addResourceLocations(location);
    }
}