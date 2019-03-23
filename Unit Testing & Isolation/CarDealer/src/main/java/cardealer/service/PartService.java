package cardealer.service;

import cardealer.domain.models.service.PartServiceModel;

public interface PartService {

    PartServiceModel savePart(PartServiceModel partServiceModel);

    PartServiceModel editPart(PartServiceModel partServiceModel);

    PartServiceModel deletePart(String id);

    PartServiceModel findPartById(String id);
}
