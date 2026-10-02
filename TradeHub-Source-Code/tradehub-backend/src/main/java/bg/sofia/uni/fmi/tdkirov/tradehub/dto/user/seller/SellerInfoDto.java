package bg.sofia.uni.fmi.tdkirov.tradehub.dto.user.seller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SellerInfoDto {
    private Long id;
    private String displayName;
    private String contactEmail;
    private String contactPhone;
    private String description;
    private String createdAt;
}
