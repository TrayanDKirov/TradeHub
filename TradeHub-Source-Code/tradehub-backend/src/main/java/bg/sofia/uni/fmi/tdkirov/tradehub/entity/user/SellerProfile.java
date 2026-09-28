package bg.sofia.uni.fmi.tdkirov.tradehub.entity.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "seller_profiles")
public class SellerProfile {
    @Id
    private Long userId;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private String displayName;

    @Column
    private String contactEmail;
    @Column
    private String contactPhone;

    @Column(columnDefinition = "TEXT")
    private String description;
    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean verified = false;

    public SellerProfile(User user, String displayName, String contactEmail,
                         String contactPhone, String description) {
        this.user = user;
        this.displayName = displayName;
        this.contactEmail = contactEmail;
        this.contactPhone = contactPhone;
        this.description = description;
    }
}
