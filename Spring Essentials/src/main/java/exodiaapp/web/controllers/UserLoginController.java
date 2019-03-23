package exodiaapp.web.controllers;

import exodiaapp.domain.models.binding.UserLoginBindingModel;
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
public class UserLoginController extends BaseController {
    private final UserService userService;
    private final ModelMapper modelMapper;

    public UserLoginController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/login")
    public ModelAndView userLogin(HttpSession session) {
        if (session.getAttribute("username") != null) {
            return this.redirect("/home");
        }
        return this.view("login");
    }

    @PostMapping("/login")
    public ModelAndView userLoginPost(@ModelAttribute UserLoginBindingModel userLoginBindingModel, HttpSession session) {
        UserServiceModel userServiceModel = this.modelMapper.map(userLoginBindingModel, UserServiceModel.class);
        if (userServiceModel != null) {
            session.setAttribute("username", userServiceModel.getUsername());
            return this.redirect("/home");
        }
        return this.redirect("/login");
    }
}
