package realestateagency.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import realestateagency.domain.entities.Offer;
import realestateagency.domain.models.binding.OfferFindBindingModel;
import realestateagency.domain.models.service.OfferServiceModel;
import realestateagency.repository.OfferRepository;

import javax.validation.Validator;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;

@Service
public class OfferServiceImpl implements OfferService {
    private final OfferRepository offerRepository;
    private final ModelMapper modelMapper;
    private final Validator validator;

    public OfferServiceImpl(OfferRepository offerRepository, ModelMapper modelMapper, @Qualifier("validation") Validator validator) {
        this.offerRepository = offerRepository;
        this.modelMapper = modelMapper;
        this.validator = validator;
    }

    @Override
    public void registerOffer(OfferServiceModel offerServiceModel) {
        if (this.validator.validate(offerServiceModel).size() > 0) {
            throw new IllegalArgumentException("Something went wrong!");
        }
        this.offerRepository.save(this.modelMapper.map(offerServiceModel, Offer.class));
    }

    @Override
    public List<OfferServiceModel> findAllOffers() {
        return List.of(this.modelMapper.map(this.offerRepository.findAll().toArray(), OfferServiceModel[].class));
    }

    @Override
    public void findOffer(OfferFindBindingModel offerFindBindingModel) {
        Optional<Offer> offer = this.offerRepository.findByApartmentTypeIsLike(offerFindBindingModel.getFamilyApartmentType());
        if (offer.isEmpty() || this.validator.validate(offerFindBindingModel).size() > 0) {
                throw new IllegalArgumentException("Something went wrong.");
        }else {
            boolean hasEnoughBudget = offer.get().getApartmentRent().multiply(offer.get().getAgencyCommission().divide(BigDecimal.valueOf(100), RoundingMode.HALF_DOWN).add(BigDecimal.ONE))
                    .compareTo(offerFindBindingModel.getFamilyBudget()) > 0;
            if (hasEnoughBudget){
                throw new IllegalArgumentException("Something went wrong.");
            }
        }
        this.offerRepository.delete(offer.get());
    }
}
