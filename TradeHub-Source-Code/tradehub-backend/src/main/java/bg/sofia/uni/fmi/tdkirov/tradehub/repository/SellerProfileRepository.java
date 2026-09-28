package bg.sofia.uni.fmi.tdkirov.tradehub.repository;

import bg.sofia.uni.fmi.tdkirov.tradehub.entity.user.SellerProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SellerProfileRepository extends JpaRepository<SellerProfile, Long> {
}