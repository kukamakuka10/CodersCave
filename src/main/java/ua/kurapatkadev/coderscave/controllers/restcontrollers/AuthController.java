package ua.kurapatkadev.coderscave.controllers.restcontrollers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ua.kurapatkadev.coderscave.dto.jwt.JwtRequest;
import ua.kurapatkadev.coderscave.dto.jwt.JwtResponse;
import ua.kurapatkadev.coderscave.dto.users.RegistrationUserDTO;
import ua.kurapatkadev.coderscave.dto.users.UserDTO;
import ua.kurapatkadev.coderscave.service.AuthService;

import javax.validation.Valid;

@RestController
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/auth")
    public JwtResponse createAuthToken(@RequestBody JwtRequest authRequest) {
        return authService.createAuthToken(authRequest);
    }

    @PostMapping("/registration")
    public UserDTO createNewUser(@Valid @RequestBody RegistrationUserDTO registrationUserDTO) {
        return authService.createNewUser(registrationUserDTO);
    }
}
