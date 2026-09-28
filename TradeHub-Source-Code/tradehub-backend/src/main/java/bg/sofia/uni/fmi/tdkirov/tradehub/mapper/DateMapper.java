package bg.sofia.uni.fmi.tdkirov.tradehub.mapper;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class DateMapper {
    private final static String PATTERN = "yyyy-MM-dd HH:mm:ss";
    private final static DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(PATTERN);

    private final static String DATE_ONLY_PATTERN = "dd MMM yy";
    private final static DateTimeFormatter DATE_ONLY_FORMATTER =
        DateTimeFormatter.ofPattern(DATE_ONLY_PATTERN);

    public String toStringDateOnly(LocalDateTime dateTime) {
        return DATE_ONLY_FORMATTER.format(dateTime);
    }

    public String toStringDateTime(LocalDateTime dateTime) {
        return FORMATTER.format(dateTime);
    }
}
