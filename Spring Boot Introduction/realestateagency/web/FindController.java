package realestateagency.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import realestateagency.domain.models.binding.OfferFindBindingModel;
import realestateagency.service.OfferService;

@Controller
public class FindController extends BaseController {
    private final OfferService offerService;

    public FindController(OfferService offerService) {
        this.offerService = offerService;
    }

    @GetMapping("/find")
    public ModelAndView find() {
        return this.view("find");
    }

    @PostMapping("/find")
    public ModelAndView findPost(@ModelAttribute OfferFindBindingModel offerFindBindingModel) {
        try {
            this.offerService.findOffer(offerFindBindingModel);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return this.redirect("find");
        }
        return this.redirect("");
    }
}
