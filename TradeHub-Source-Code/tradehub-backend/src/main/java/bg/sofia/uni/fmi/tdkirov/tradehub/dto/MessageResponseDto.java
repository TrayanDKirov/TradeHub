package bg.sofia.uni.fmi.tdkirov.tradehub.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MessageResponseDto {
    String userMessage;
    String developerMessage;
}
