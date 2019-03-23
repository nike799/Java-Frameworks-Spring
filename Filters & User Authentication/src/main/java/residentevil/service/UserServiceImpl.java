package residentevil.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import residentevil.domain.entities.Role;
import residentevil.domain.entities.User;
import residentevil.domain.models.service.UserServiceModel;
import residentevil.repository.RoleRepository;
import residentevil.repository.UserRepository;
import java.util.*;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository,
                           ModelMapper modelMapper, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.modelMapper = modelMapper;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                user.getAuthorities()
        );
    }

    @Override
    public boolean register(UserServiceModel userServiceModel) {
        seedRolesIntoDB();
        User user = this.modelMapper.map(userServiceModel, User.class);
        user.setPassword(bCryptPasswordEncoder.encode(userServiceModel.getPassword()));
        giveRolesToUser(user);
        try {
            userRepository.save(user);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<UserServiceModel> getAllUsers() {
        return List.of(this.modelMapper.map(this.userRepository.findAll().toArray(),UserServiceModel[].class));
    }

    private void seedRolesIntoDB() {
        if (roleRepository.count() == 0) {
            Role admin = new Role();
            admin.setAuthority("ADMIN");
            Role user = new Role();
            user.setAuthority("USER");
            Role moderator = new Role();
            moderator.setAuthority("MODERATOR");
            List<Role> roles = new ArrayList<>();
            roles.add(admin);
            roles.add(user);
            roles.add(moderator);
            this.roleRepository.saveAll(roles);
        }
    }

    @SuppressWarnings("unchecked")
    private void giveRolesToUser(User user) {
        user.setAuthorities(userRepository.count() == 0 ? new HashSet<>(roleRepository.findAll()) : Collections.singleton(roleRepository.findByAuthority("USER")));
    }
}
