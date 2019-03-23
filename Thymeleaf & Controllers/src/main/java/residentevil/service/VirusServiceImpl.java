package residentevil.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import residentevil.domain.entities.Capital;
import residentevil.domain.entities.Virus;
import residentevil.domain.models.service.VirusServiceModel;
import residentevil.repository.CapitalRepository;
import residentevil.repository.VirusRepository;

import java.util.List;

@Service
public class VirusServiceImpl implements VirusService {
    private final VirusRepository virusRepository;
    private final CapitalRepository capitalRepository;
    private final ModelMapper modelMapper;

    public VirusServiceImpl(VirusRepository virusRepository, CapitalRepository capitalRepository, ModelMapper modelMapper) {
        this.virusRepository = virusRepository;
        this.capitalRepository = capitalRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public VirusServiceModel addVirus(VirusServiceModel virusServiceModel, List<Long> capitalsIds) {
        List<Capital> capitals = this.capitalRepository.findAllById(capitalsIds);
        Virus virus = this.modelMapper.map(virusServiceModel, Virus.class);
        virus.setCapitals(capitals);
        virus = this.virusRepository.saveAndFlush(virus);
        return this.modelMapper.map(virus, VirusServiceModel.class);
    }

    @Override
    public List<VirusServiceModel> findAllViruses() {
        return List.of(this.modelMapper.map(this.virusRepository.findAll().toArray(), VirusServiceModel[].class));
    }

    @Override
    public void delete(Long id) {
        this.virusRepository.findById(id).ifPresent(this.virusRepository::delete);
    }

    @Override
    public void update(VirusServiceModel virusServiceModel, List<Long> capitalIds) {
        Virus virus = this.virusRepository.findById(Long.parseLong(virusServiceModel.getId())).orElse(null);
        if (virus != null) {
            this.modelMapper.map(virusServiceModel, virus);
            if (capitalIds != null) {
                virus.setCapitals(this.capitalRepository.findAllById(capitalIds));
            }
        }
        this.virusRepository.save(virus);
    }

    @Override
    public VirusServiceModel findById(Long id) {
        return this.modelMapper.map(this.virusRepository.findById(id).get(), VirusServiceModel.class);
    }
}
