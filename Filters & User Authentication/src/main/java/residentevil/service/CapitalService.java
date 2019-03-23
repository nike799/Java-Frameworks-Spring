package residentevil.service;
import residentevil.domain.models.service.CapitalServiceModel;
import java.util.List;

public interface CapitalService {
    List<CapitalServiceModel> findAllCapitals();
}
