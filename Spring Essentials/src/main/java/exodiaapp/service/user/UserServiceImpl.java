package exodiaapp.service.user;

import exodiaapp.domain.entities.User;
import exodiaapp.domain.models.service.UserServiceModel;
import exodiaapp.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final BCryptPasswordEncoder bcpe;


    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, BCryptPasswordEncoder bcpe) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.bcpe = bcpe;
    }


    @Override
    public UserServiceModel userRegister(UserServiceModel userServiceModel) {
        userServiceModel.setPassword(bcpe.encode(userServiceModel.getPassword()));
        return this.modelMapper
                .map(this.userRepository.save(this.modelMapper.map(userServiceModel,User.class)),UserServiceModel.class);
    }

    @Override
    public UserServiceModel userLogin(UserServiceModel userServiceModel) {

        User user = this.userRepository.findByUsername(userServiceModel.getUsername()).orElse(null);

            return user != null && this.bcpe.matches(userServiceModel.getPassword(),user.getPassword()) ?
                    this.modelMapper.map(user,UserServiceModel.class) : null;

    }

    @Override
    public List<UserServiceModel> getAllUsers() {
        return null;
    }

    @Override
    public UserServiceModel findUserByUsername(String username) {
        return null;
    }

    @Override
    public UserServiceModel findUserById(String id) {
        return null;
    }
}
