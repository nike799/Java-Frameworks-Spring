package residentevil.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import residentevil.domain.entities.Role;
import residentevil.domain.models.service.UserServiceModel;

import java.util.List;
import java.util.Set;

public interface UserService extends UserDetailsService {
    boolean register (UserServiceModel userServiceModel);
    List<UserServiceModel> getAllUsers();
    UserServiceModel findUserById(Long id);
    void editUserPermissions(UserServiceModel userServiceModel, Set<Role> roles);
}
