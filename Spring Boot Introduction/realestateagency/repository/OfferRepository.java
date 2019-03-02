package realestateagency.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import realestateagency.domain.entities.Offer;

import java.util.Optional;


@Repository
public interface OfferRepository extends JpaRepository<Offer,String> {
    Optional<Offer> findByApartmentTypeIsLike(String type);
}
