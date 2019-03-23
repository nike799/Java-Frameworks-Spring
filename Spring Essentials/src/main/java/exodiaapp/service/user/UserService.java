package exodiaapp.service.user;

import exodiaapp.domain.models.service.UserServiceModel;

import java.util.List;

public interface UserService {
    UserServiceModel userRegister(UserServiceModel userServiceModel);
    UserServiceModel userLogin(UserServiceModel userServiceModel);
    List<UserServiceModel> getAllUsers();
    UserServiceModel findUserByUsername(String username);
    UserServiceModel findUserById(String id);
}
