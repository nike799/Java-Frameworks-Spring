package residentevil.web;

import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import residentevil.domain.models.binding.UserRegisterBindingModel;
import residentevil.domain.models.service.UserServiceModel;
import residentevil.service.UserService;

@Controller
@RequestMapping("/users")
public class UserRegisterController extends BaseController {
    private final UserService userService;
    private final ModelMapper modelMapper;

    public UserRegisterController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/register")
    @PreAuthorize(value = "isAnonymous()")
    public ModelAndView register() {
        return this.view("user-register");
    }

    @PostMapping("/register")
    public ModelAndView registerConfirm(@ModelAttribute UserRegisterBindingModel userRegisterBindingModel) {

        return userService.register(modelMapper.map(userRegisterBindingModel, UserServiceModel.class)) ?
                this.redirect("/login") : this.view("/user-register");
    }
}
