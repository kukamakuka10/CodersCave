package ua.kurapatkadev.coderscave.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
public class UserController {
    @GetMapping("/unsecured")
    public String unsecuredData(){
        return "UnsecuredData";
    }
    @GetMapping("/secured")
    public String securedData(){
        return "SecuredData";

    }
    @GetMapping("/admin")
    public String adminData(){
        return "AdminData";
    }
    @GetMapping("/info")
    public String userData(Principal principal){
        return principal.getName();
    }
}
