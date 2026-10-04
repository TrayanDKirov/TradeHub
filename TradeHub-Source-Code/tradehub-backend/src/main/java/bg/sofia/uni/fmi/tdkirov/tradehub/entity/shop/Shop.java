package bg.sofia.uni.fmi.tdkirov.tradehub.entity.shop;

import bg.sofia.uni.fmi.tdkirov.tradehub.entity.user.SellerProfile;
import bg.sofia.uni.fmi.tdkirov.tradehub.entity.user.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "shops")
public class Shop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(name = "picture_path")
    private String picturePath;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "created_by_id", nullable = false, updatable = false)
    @Setter(AccessLevel.NONE)
    private SellerProfile createdBy;

    // The relation is owned by ShopItem.shop, so this list adds no column to shops
    @OneToMany(mappedBy = "shop", fetch = FetchType.LAZY)
    @Setter(AccessLevel.NONE)
    private List<ShopItem> items = new ArrayList<>();

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    @Setter(AccessLevel.NONE)
    private LocalDateTime createdAt;

    public Shop(Long id) {
        this.id = id;
    }

    public Shop(String name, String picturePath, SellerProfile createdBy) {
        this.name = name;
        this.picturePath = picturePath;
        this.createdBy = createdBy;
    }
}