package residentevil.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import residentevil.domain.models.service.UserServiceModel;

import java.util.List;

public interface UserService extends UserDetailsService {
    boolean register (UserServiceModel userServiceModel);
    List<UserServiceModel> getAllUsers();
}
