package bg.sofia.uni.fmi.tdkirov.tradehub.entity.cart;

import bg.sofia.uni.fmi.tdkirov.tradehub.entity.shop.Shop;
import bg.sofia.uni.fmi.tdkirov.tradehub.entity.shop.ShopItem;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "cart_items",
    uniqueConstraints = @UniqueConstraint(columnNames = {"cart_id", "item_id"}))
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "item_id", nullable = false, updatable = false)
    @Setter(AccessLevel.NONE)
    private ShopItem item;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "shop_id", nullable = false, updatable = false)
    @Setter(AccessLevel.NONE)
    private Shop shop;

    @Column(nullable = false)
    private Integer quantity;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cart_id", nullable = false, updatable = false)
    @Setter(AccessLevel.NONE)
    private ShoppingCart cart;

    public CartItem(ShopItem item, Integer quantity, ShoppingCart cart) {
        this.item = item;
        this.shop = item.getShop();
        this.quantity = quantity;
        this.cart = cart;
    }

    public BigDecimal getTotalPrice() {
        return item.getPrice().multiply(BigDecimal.valueOf(quantity));
    }
}