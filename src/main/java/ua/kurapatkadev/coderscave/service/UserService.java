package ua.kurapatkadev.coderscave.service;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.kurapatkadev.coderscave.dto.users.RegistrationUserDTO;
import ua.kurapatkadev.coderscave.dto.users.UserDTO;
import ua.kurapatkadev.coderscave.entities.User;
import ua.kurapatkadev.coderscave.mappers.UserMapper;
import ua.kurapatkadev.coderscave.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service

public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final RoleService roleService;


    public UserService(UserRepository userRepository, RoleService roleService) {
        this.userRepository = userRepository;
        this.roleService = roleService;
    }

    public Optional<User> findByUserName(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByUserName(username).orElseThrow(() -> new UsernameNotFoundException(
                String.format("User '%s' not found", username)
        ));
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList())
        );
    }

    public UserDTO createNewUser(RegistrationUserDTO registrationUserDTO) {
        User user = UserMapper.INSTANCE.mapToEntity(registrationUserDTO);
        user.setRoles(List.of(roleService.getUserRole()));
        userRepository.save(user);
        return UserMapper.INSTANCE.mapToDto(user);
    }
}
