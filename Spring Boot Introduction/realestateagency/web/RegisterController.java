package realestateagency.web;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import realestateagency.domain.models.binding.OfferRegisterBindingModel;
import realestateagency.domain.models.service.OfferServiceModel;
import realestateagency.service.OfferService;

@Controller
public class RegisterController extends BaseController{
    private final OfferService offerService;
    private final ModelMapper modelMapper;

    public RegisterController(OfferService offerService, ModelMapper modelMapper) {
        this.offerService = offerService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/register")
    public ModelAndView register() {
        return this.view("register");
    }

    @PostMapping("/register")
    public ModelAndView registerConfirm(@ModelAttribute OfferRegisterBindingModel offerRegisterBindingModel) {
        try {
            this.offerService.registerOffer(this.modelMapper.map(offerRegisterBindingModel, OfferServiceModel.class));
        } catch (Exception e) {
            e.printStackTrace();
            return this.redirect("register");
        }
        return this.redirect("");
    }
}
