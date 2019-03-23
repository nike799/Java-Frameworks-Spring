package residentevil.service;
import residentevil.domain.models.service.VirusServiceModel;

import java.util.List;

public interface VirusService {
    VirusServiceModel addVirus(VirusServiceModel virusServiceModel, List<Long> capitalIds);
    List<VirusServiceModel> findAllViruses();
    void delete(Long id);
    void update(VirusServiceModel virusServiceModel,List<Long> capitalIds);
    VirusServiceModel findById(Long id);
}
