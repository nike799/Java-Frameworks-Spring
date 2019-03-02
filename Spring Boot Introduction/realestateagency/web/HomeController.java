package realestateagency.web;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import realestateagency.domain.models.view.OfferViewModel;
import realestateagency.service.OfferService;

import java.util.List;

@Controller
public class HomeController extends BaseController {
    private final OfferService offerService;
    private final ModelMapper modelMapper;

    public HomeController(OfferService offerService, ModelMapper modelMapper) {
        this.offerService = offerService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/")
    public ModelAndView index(ModelAndView modelAndView) {
        List<OfferViewModel> offerViewModels =
                List.of(this.modelMapper.map(this.offerService.findAllOffers().toArray(), OfferViewModel[].class));
        modelAndView.addObject("offerViewModels", offerViewModels);
        return this.view("index",modelAndView);
    }
}
