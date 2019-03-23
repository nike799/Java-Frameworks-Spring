package residentevil.web;

import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import residentevil.domain.models.view.UserViewModel;
import residentevil.service.UserService;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/users")
public class UserAllController extends BaseController {
    private final UserService userService;
    private final ModelMapper modelMapper;

    public UserAllController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/all")
    @PreAuthorize(value = "hasAuthority('ADMIN')")
    public ModelAndView usersAll(ModelAndView modelAndView, Principal principal) {

        modelAndView.addObject("users", List.of(this.modelMapper.map(this.userService.getAllUsers().toArray(), UserViewModel[].class)));
        return this.view("user-all", modelAndView);
    }
}
