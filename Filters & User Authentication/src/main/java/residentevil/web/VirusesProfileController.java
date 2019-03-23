package residentevil.web;

import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import residentevil.domain.models.binding.VirusAddBindingModel;
import residentevil.domain.models.binding.VirusEditBindingModel;
import residentevil.domain.models.service.VirusServiceModel;
import residentevil.service.CapitalService;
import residentevil.service.VirusService;

import javax.validation.Valid;
import java.lang.reflect.Field;

@Controller
@RequestMapping("/viruses")
public class VirusesProfileController extends BaseController {
    private final VirusService virusService;
    private final CapitalService capitalService;
    private final ModelMapper modelMapper;

    public VirusesProfileController(VirusService virusService, CapitalService capitalService, ModelMapper modelMapper) {
        this.virusService = virusService;
        this.capitalService = capitalService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/edit/{id}")
    @PreAuthorize(value = "hasAnyAuthority('MODERATOR','ADMIN')")
    public ModelAndView editVirus(@PathVariable Long id, ModelAndView modelAndView) {
        VirusEditBindingModel virusEditBindingModel = this.modelMapper.map(this.virusService.findById(id), VirusEditBindingModel.class);
        modelAndView.addObject("virusEditBindingModel", virusEditBindingModel);
        modelAndView.addObject("capitals", this.capitalService.findAllCapitals());
        return this.view("viruses-profile", modelAndView);
    }

    @PostMapping("/save/{id}")
    @PreAuthorize(value = "hasAnyAuthority('MODERATOR','ADMIN')")
    public ModelAndView save( @PathVariable Long id,@Valid @ModelAttribute VirusAddBindingModel virusAddBindingModel, BindingResult bindingResult, ModelAndView modelAndView) throws IllegalAccessException, NoSuchFieldException {
        if (bindingResult.hasErrors()) {
            return this.redirect("/viruses/edit/"+ id);
        }
        VirusServiceModel origin = this.virusService.findById(id);
        this.updateVirus(virusAddBindingModel, origin);
        virusService.update(origin, virusAddBindingModel.getCapitalsId());
        return this.redirect("/viruses/show");
    }

    private void updateVirus(VirusAddBindingModel binding, VirusServiceModel origin) throws IllegalAccessException, NoSuchFieldException {
        System.out.println();
        for (Field f : binding.getClass().getDeclaredFields()) {
            f.setAccessible(true);
            if (f.get(binding) != null && !f.getName().equals("capitalsId")) {
                String fieldName = f.getName();
                Field f1 = origin.getClass().getDeclaredField(fieldName);
                f1.setAccessible(true);
                f1.set(origin, f.get(binding));
            }
        }
    }
}
