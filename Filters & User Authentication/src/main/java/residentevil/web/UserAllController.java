package residentevil.web;

import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import residentevil.domain.entities.Role;
import residentevil.domain.models.binding.UserEditPermissionBindingModel;
import residentevil.domain.models.service.UserServiceModel;
import residentevil.domain.models.view.UserViewModel;
import residentevil.service.UserService;

import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
        modelAndView.addObject("currentUser",principal.getName());
        modelAndView.addObject("users", List.of(this.modelMapper.map(this.userService.getAllUsers().toArray(), UserServiceModel[].class)));
        return this.view("user-all", modelAndView);
    }

    @GetMapping("/edit-permission/{id}")
    public ModelAndView editPermission(@PathVariable Long id, ModelAndView modelAndView) {
        UserServiceModel userServiceModel = this.userService.findUserById(id);
        UserViewModel userViewModel = this.modelMapper.map(userServiceModel, UserViewModel.class);
        userViewModel.setAdmin(userServiceModel.getAuthorities().stream().anyMatch(role -> role.getAuthority().equals("ADMIN")));
        userViewModel.setModerator(userServiceModel.getAuthorities().stream().anyMatch(role -> role.getAuthority().equals("MODERATOR")));
        modelAndView.addObject("userViewModel", userViewModel);
        return this.view("user-profile", modelAndView);
    }

    @PostMapping("/edit-permission")
    public ModelAndView editPermissionConfirm(@ModelAttribute UserEditPermissionBindingModel userEditPermissionBindingModel) {
        UserServiceModel userServiceModel = this.userService.findUserById(userEditPermissionBindingModel.getId());
        this.modelMapper.map(userEditPermissionBindingModel,userServiceModel);
        Set<Role> roles = new HashSet<>();
        if (userEditPermissionBindingModel.getAdmin()) {
            Role admin = new Role() {{
                setAuthority("ADMIN");
            }};
            roles.add(admin);
        }
        if (userEditPermissionBindingModel.getModerator()) {
            Role moderator = new Role() {{
                setAuthority("MODERATOR");
            }};
            roles.add(moderator);
        }
        this.userService.editUserPermissions(userServiceModel,roles);
        return this.redirect("/users/all");
    }
}
