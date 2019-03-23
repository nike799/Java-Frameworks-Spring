package residentevil.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import residentevil.domain.entities.Capital;
import residentevil.domain.models.service.CapitalServiceModel;
import residentevil.repository.CapitalRepository;

import java.util.Comparator;
import java.util.List;

@Service
public class CapitalServiceImpl implements CapitalService {
    private final CapitalRepository capitalRepository;
    private final ModelMapper modelMapper;

    public CapitalServiceImpl(CapitalRepository capitalRepository, ModelMapper modelMapper) {
        this.capitalRepository = capitalRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<CapitalServiceModel> findAllCapitals() {
       return List.of(this.modelMapper.map(this.capitalRepository.findAll()
               .stream()
               .sorted(Comparator.comparing(Capital::getName)).toArray(), CapitalServiceModel[].class));
    }
}
