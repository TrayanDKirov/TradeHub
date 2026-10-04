package bg.sofia.uni.fmi.tdkirov.tradehub.mapper;

import org.springframework.stereotype.Component;

@Component
public class PictureMapper {
    // Must match the URL path the upload directory is served under (see WebConfiguration)
    private static final String UPLOADS_URL_PREFIX = "/uploads/";

    public String toPictureUrl(String picturePath) {
        if (picturePath == null || picturePath.isBlank()) {
            return null;
        }

        return UPLOADS_URL_PREFIX + picturePath;
    }
}