package ua.kurapatkadev.coderscave.dto.users;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class RegistrationUserDTO {
    @NotBlank(message = "The username must not be empty")
    private String username;
    @NotBlank(message = "Password must not be empty")
    private String password;
    @NotBlank(message = "Confirm password must not be empty")
    private String confirmPassword;
    @NotBlank(message = "Email must not be blank")
    @Email(message = "Incorrect email address")
    private String email;

}
