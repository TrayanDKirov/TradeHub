package bg.sofia.uni.fmi.tdkirov.tradehub.service;

import bg.sofia.uni.fmi.tdkirov.tradehub.exception.file.FileStorageException;
import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {
    /**
     * Validates the image and saves it under a newly generated name
     * in the given subdirectory of the upload directory.
     *
     * @param image  the uploaded image, must not be null or empty.
     * @param subDir the subdirectory to save the image in, for example "shops".
     *
     * @throws FileStorageException if the image is empty, has an unsupported
     *                              type or could not be saved.
     *
     * @return the path of the saved image, relative to the upload directory.
     */
    String saveImage(MultipartFile image, String subDir);

    /**
     * Deletes the file with this path if it exists. Does nothing when the path is null or blank.
     *
     * @param relativePath the path of the file, relative to the upload directory.
     */
    void delete(String relativePath);
}