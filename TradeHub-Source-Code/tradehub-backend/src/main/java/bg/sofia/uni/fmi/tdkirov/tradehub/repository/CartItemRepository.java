package bg.sofia.uni.fmi.tdkirov.tradehub.repository;

import bg.sofia.uni.fmi.tdkirov.tradehub.entity.cart.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    void deleteAllByShopId(Long shopId);
}