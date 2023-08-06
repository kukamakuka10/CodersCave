package ua.kurapatkadev.coderscave.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ua.kurapatkadev.coderscave.dto.jwt.JwtRequest;
import ua.kurapatkadev.coderscave.dto.jwt.JwtResponse;
import ua.kurapatkadev.coderscave.dto.users.RegistrationUserDTO;
import ua.kurapatkadev.coderscave.dto.exception.AppError;
import ua.kurapatkadev.coderscave.dto.users.UserDTO;
import ua.kurapatkadev.coderscave.exceptions.PasswordConfirmationException;
import ua.kurapatkadev.coderscave.exceptions.UsernameAlreadyExistsException;
import ua.kurapatkadev.coderscave.utils.JwtTokenUtils;
@Service
public class AuthService {
    private final UserService userService;
    private final JwtTokenUtils jwtTokenUtils;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthService(UserService userService, JwtTokenUtils jwtTokenUtils, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.jwtTokenUtils = jwtTokenUtils;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    public JwtResponse createAuthToken(JwtRequest authRequest){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(),authRequest.getPassword()));
        UserDetails userDetails=userService.loadUserByUsername(authRequest.getUsername());
        String token=jwtTokenUtils.generateToken(userDetails);
        return new JwtResponse(token);
    }

    public UserDTO createNewUser(RegistrationUserDTO registrationUserDTO){
        if(!registrationUserDTO.getPassword().equals(registrationUserDTO.getConfirmPassword())){
           throw new PasswordConfirmationException("Passwords don't match!");
        }
        if (userService.findByUserName(registrationUserDTO.getUsername()).isPresent()){
            throw new UsernameAlreadyExistsException("This username is already taken");
        }
        registrationUserDTO.setPassword(passwordEncoder.encode(registrationUserDTO.getPassword()));
        return userService.createNewUser(registrationUserDTO);
    }

}
