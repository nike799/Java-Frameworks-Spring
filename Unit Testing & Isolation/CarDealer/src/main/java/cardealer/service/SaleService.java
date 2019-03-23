package cardealer.service;

import cardealer.domain.models.service.CarSaleServiceModel;
import cardealer.domain.models.service.PartSaleServiceModel;

public interface SaleService {

    CarSaleServiceModel saleCar(CarSaleServiceModel carSaleServiceModel);

    PartSaleServiceModel salePart(PartSaleServiceModel partSaleServiceModel);
}
