package ua.kurapatkadev.coderscave.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ua.kurapatkadev.coderscave.dto.JwtRequest;
import ua.kurapatkadev.coderscave.dto.JwtResponse;
import ua.kurapatkadev.coderscave.dto.RegistrationUserDTO;
import ua.kurapatkadev.coderscave.entities.User;
import ua.kurapatkadev.coderscave.exception.dto.AppError;
import ua.kurapatkadev.coderscave.service.UserService;
import ua.kurapatkadev.coderscave.utils.JwtTokenUtils;

@RestController

public class AuthController {
    private final UserService userService;
    private final JwtTokenUtils jwtTokenUtils;
    private final AuthenticationManager authenticationManager;

    public AuthController(UserService userService, JwtTokenUtils jwtTokenUtils, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.jwtTokenUtils = jwtTokenUtils;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/auth")
    public ResponseEntity<?>createAuthToken(@RequestBody JwtRequest authRequest){
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(),authRequest.getPassword()));
        }catch (BadCredentialsException e){
            return new ResponseEntity<>(new AppError(HttpStatus.UNAUTHORIZED.value(),"No Correct login or password "),HttpStatus.UNAUTHORIZED);

        }
        UserDetails userDetails=userService.loadUserByUsername(authRequest.getUsername());
        String token=jwtTokenUtils.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));
    }
    @PostMapping("/registration")
    public ResponseEntity<?>createNewUser(@RequestBody RegistrationUserDTO registrationUserDTO){
        if(!registrationUserDTO.getPassword().equals(registrationUserDTO.getConfirmPassword())){
            return new ResponseEntity<>(new AppError(HttpStatus.UNAUTHORIZED.value(),"Пароли не совпадают"),HttpStatus.BAD_REQUEST);
        }
        if (userService.findByUserName(registrationUserDTO.getUsername())!=null){
            return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST.value(),"Имя занято"),HttpStatus.BAD_REQUEST);
        }
        userService.createNewUser(registrationUserDTO);
        return ResponseEntity.ok(jwtTokenUtils.)
    }
}
