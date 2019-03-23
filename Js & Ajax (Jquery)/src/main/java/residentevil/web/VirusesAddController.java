package residentevil.web;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import residentevil.domain.models.binding.VirusAddBindingModel;
import residentevil.domain.models.service.VirusServiceModel;
import residentevil.service.CapitalService;
import residentevil.service.VirusService;

import javax.validation.Valid;

@Controller
@RequestMapping("/viruses")
public class VirusesAddController extends BaseController {
    private final CapitalService capitalService;
    private final VirusService virusService;
    private final ModelMapper modelMapper;

    public VirusesAddController(CapitalService capitalService, VirusService virusService, ModelMapper modelMapper) {
        this.capitalService = capitalService;
        this.virusService = virusService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/add")
    public ModelAndView addViruses(@ModelAttribute VirusAddBindingModel virusAddBindingModel, ModelAndView modelAndView) {
        modelAndView.addObject("virusAddBindingModel", virusAddBindingModel);
        modelAndView.addObject("capitals", this.capitalService.findAllCapitals());
        return this.view("viruses-add", modelAndView);
    }

    @PostMapping("/add")
    public ModelAndView addVirusesConfirm(@Valid @ModelAttribute VirusAddBindingModel virusAddBindingModel, BindingResult bindingResult, ModelAndView modelAndView) {
        if (bindingResult.hasErrors()) {
            return this.view("viruses-add", modelAndView);
        }
        VirusServiceModel virusServiceModel = this.modelMapper.map(virusAddBindingModel, VirusServiceModel.class);
        virusServiceModel.setId(null);
        return this.virusService.addVirus(virusServiceModel, virusAddBindingModel.getCapitalsId()) != null ?
                this.redirect("/viruses/show") :
                this.redirect("/viruses/add");
    }
}
