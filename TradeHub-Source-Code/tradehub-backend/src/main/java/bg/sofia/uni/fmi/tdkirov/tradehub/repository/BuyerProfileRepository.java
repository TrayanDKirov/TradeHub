package bg.sofia.uni.fmi.tdkirov.tradehub.repository;

import bg.sofia.uni.fmi.tdkirov.tradehub.entity.user.BuyerProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BuyerProfileRepository extends JpaRepository<BuyerProfile, Long> {
}