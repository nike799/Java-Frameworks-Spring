package exodiaapp.web.controllers;

import exodiaapp.domain.models.binding.UserRegisterBindingModel;
import exodiaapp.domain.models.service.UserServiceModel;
import exodiaapp.service.user.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
public class UserRegisterController extends BaseController {
    private final UserService userService;
    private final ModelMapper modelMapper;

    public UserRegisterController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/register")
    public ModelAndView userRegister(HttpSession session) {
        if (session.getAttribute("username") != null) {
            return this.redirect("/home");
        }
        return this.view("register");
    }

    @PostMapping("/register")
    public ModelAndView userRegisterPost(@ModelAttribute UserRegisterBindingModel userRegisterBindingModel) {
        if (!userRegisterBindingModel.getPassword().equals(userRegisterBindingModel.getConfirmPassword())) {
            return this.redirect("/register");
        }
        return  this.userService.userRegister(this.modelMapper.map(userRegisterBindingModel, UserServiceModel.class)) !=null ?
                this.redirect("/login") : this.redirect("/register");
    }
}
