package residentevil.web;

import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import residentevil.domain.models.binding.VirusEditBindingModel;
import residentevil.domain.models.view.CapitalViewModel;
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
    @PreAuthorize(value = "hasAnyAuthority('USER','MODERATOR','ADMIN')")
    public ModelAndView viruses(@ModelAttribute VirusEditBindingModel virusEditBindingModel, ModelAndView modelAndView) {
        modelAndView.addObject("virusEditBindingModel", virusEditBindingModel);
        modelAndView.addObject("capitals", this.capitalService.findAllCapitals());
        modelAndView.addObject("viruses", List.of(this.modelMapper.map(this.virusService.findAllViruses().toArray(), VirusViewModel[].class)));
        return this.view("viruses-show", modelAndView);
    }

    @GetMapping(value = "/fetch-viruses", produces = "application/json")
    @ResponseBody
    public List<VirusViewModel> fetchViruses() {
        return List.of(this.modelMapper.map(this.virusService.findAllViruses().toArray(), VirusViewModel[].class));
    }

    @GetMapping(value = "/fetch-capitals", produces = "application/json")
    @ResponseBody
    public List<CapitalViewModel> fetchCapitals() {
        return List.of(this.modelMapper.map(this.capitalService.findAllCapitals().toArray(), CapitalViewModel[].class));
    }

    @GetMapping(value = "/delete/{id}")
    @PreAuthorize(value = "hasAnyAuthority('MODERATOR','ADMIN')")
    public ModelAndView deleteVirus(@PathVariable Long id) {
        this.virusService.delete(id);
        return this.redirect("/viruses/show");
    }
}
