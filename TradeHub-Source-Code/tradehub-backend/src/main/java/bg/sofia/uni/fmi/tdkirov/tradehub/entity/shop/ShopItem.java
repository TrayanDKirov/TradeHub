package bg.sofia.uni.fmi.tdkirov.tradehub.entity.shop;

import bg.sofia.uni.fmi.tdkirov.tradehub.entity.user.SellerProfile;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "shop_items")
public class ShopItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(name = "picture_path")
    private String picturePath;

    // numeric(10, 2): exact decimal, up to 99 999 999.99
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;
    @Column
    private Integer quantity;

    @Column(columnDefinition = "TEXT")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "shop_id", nullable = false, updatable = false)
    @Setter(AccessLevel.NONE)
    private Shop shop;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "created_by_id", nullable = false, updatable = false)
    @Setter(AccessLevel.NONE)
    private SellerProfile createdBy;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    @Setter(AccessLevel.NONE)
    private LocalDateTime createdAt;

    public ShopItem(String name, String picturePath, BigDecimal price,
                    Integer quantity, String description,
                    Shop shop, SellerProfile createdBy) {
        this.name = name;
        this.picturePath = picturePath;
        this.price = price;
        this.quantity = quantity;
        this.description = description;
        this.shop = shop;
        this.createdBy = createdBy;
    }
}
