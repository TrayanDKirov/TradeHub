package bg.sofia.uni.fmi.tdkirov.tradehub.service.implementations;

import bg.sofia.uni.fmi.tdkirov.tradehub.exception.file.FileStorageException;
import bg.sofia.uni.fmi.tdkirov.tradehub.service.FileStorageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.UUID;

@Service
public class FileStorageServiceImpl implements FileStorageService {
    private static final Map<String, String> ALLOWED_IMAGE_TYPES = Map.of(
        "image/jpeg", ".jpg",
        "image/png", ".png",
        "image/webp", ".webp");

    private final Path rootDir;

    public FileStorageServiceImpl(@Value("${app.upload-dir:uploads}") String uploadDir) {
        this.rootDir = Paths.get(uploadDir).toAbsolutePath().normalize();
    }

    @Override
    public String saveImage(MultipartFile image, String subDir) {
        if (image == null || image.isEmpty()) {
            throw new FileStorageException("The picture must not be empty. ");
        }

        String extension = ALLOWED_IMAGE_TYPES.get(image.getContentType());
        if (extension == null) {
            throw new FileStorageException("Only JPEG, PNG and WEBP pictures are allowed. ");
        }

        String fileName = UUID.randomUUID() + extension;
        Path dir = rootDir.resolve(subDir);

        try (InputStream in = image.getInputStream()) {
            Files.createDirectories(dir);
            Files.copy(in, dir.resolve(fileName));
        } catch (IOException e) {
            throw new FileStorageException("The picture could not be saved. ", e);
        }

        return subDir + "/" + fileName;
    }

    @Override
    public void delete(String relativePath) {
        if (relativePath == null || relativePath.isBlank()) {
            return;
        }

        Path file = rootDir.resolve(relativePath).normalize();
        if (!file.startsWith(rootDir)) {
            return;
        }

        try {
            Files.deleteIfExists(file);
        } catch (IOException ignored) {
            // A leftover file is not worth failing the request for
        }
    }
}