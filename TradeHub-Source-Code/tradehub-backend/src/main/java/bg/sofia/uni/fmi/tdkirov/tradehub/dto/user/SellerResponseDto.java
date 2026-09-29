package bg.sofia.uni.fmi.tdkirov.tradehub.dto.user;

import lombok.Data;

@Data
public class SellerResponseDto extends UserResponseDto {
    private String displayName;

    private String contactEmail;
    private String contactPhone;
    private String description;

    public SellerResponseDto(Long id, String username, String email,
                            String phone, String createdAt, String displayName,
                            String contactEmail, String contactPhone,
                            String description) {
        super(id, username, email, phone, createdAt);

        this.displayName = displayName;
        this.contactEmail = contactEmail;
        this.contactPhone = contactPhone;
        this.description = description;
    }
}
