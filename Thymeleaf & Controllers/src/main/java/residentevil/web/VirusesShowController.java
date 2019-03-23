package residentevil.web;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import residentevil.domain.models.binding.VirusEditBindingModel;
import residentevil.domain.models.view.VirusViewModel;
import residentevil.service.CapitalService;
import residentevil.service.VirusService;

import java.util.List;

@Controller
@RequestMapping("/viruses")
public class VirusesShowController extends BaseController {
    private final VirusService virusService;
    private final CapitalService capitalService;
    private final ModelMapper modelMapper;

    public VirusesShowController(VirusService virusService, CapitalService capitalService, ModelMapper modelMapper) {
        this.virusService = virusService;
        this.capitalService = capitalService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/show")
    public ModelAndView viruses(@ModelAttribute VirusEditBindingModel virusEditBindingModel, ModelAndView modelAndView) {
        modelAndView.addObject("virusEditBindingModel", virusEditBindingModel);
        modelAndView.addObject("capitals", this.capitalService.findAllCapitals());
        modelAndView.addObject("viewModels", List.of(this.modelMapper.map(this.virusService.findAllViruses().toArray(), VirusViewModel[].class)));
        return this.view("viruses-show",modelAndView);
    }

    @PostMapping("/delete/{id}")
    public ModelAndView deleteVirus(@PathVariable Long id){
        this.virusService.delete(id);
        return this.redirect("/viruses/show");
    }
}
