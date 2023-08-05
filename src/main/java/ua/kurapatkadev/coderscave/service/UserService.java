package ua.kurapatkadev.coderscave.service;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.kurapatkadev.coderscave.dto.RegistrationUserDTO;
import ua.kurapatkadev.coderscave.entities.User;
import ua.kurapatkadev.coderscave.repository.RoleRepository;
import ua.kurapatkadev.coderscave.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service

public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Optional<User>findByUserName(String username){
        return userRepository.findByUsername(username);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user=findByUserName(username).orElseThrow(()->new UsernameNotFoundException(
                String.format("User '%s' not found",username)
        ));
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList())
        );
    }
    public void createNewUser(RegistrationUserDTO registrationUserDTO){
        User user=new User();
        user.setRoles(List.of(roleRepository.findByName("ROLE_USER").get()));
        user.setUsername(registrationUserDTO.getUsername());
        user.setEmail(registrationUserDTO.getEmail());
        user.setPassword(passwordEncoder.encode(registrationUserDTO.getPassword()));
        userRepository.save(user);
    }
}
