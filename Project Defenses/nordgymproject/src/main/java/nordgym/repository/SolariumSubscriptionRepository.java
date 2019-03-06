package nordgym.repository;

import nordgym.domain.entities.SolariumSubscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SolariumSubscriptionRepository extends JpaRepository<SolariumSubscription,Long> {
}
