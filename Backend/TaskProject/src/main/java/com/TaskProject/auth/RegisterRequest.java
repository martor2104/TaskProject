package com.TaskProject.auth;

import com.TaskProject.Entity.SystemRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RegisterRequest {

    @NotBlank(message = "Username obligatorio")
    private String username;

    @NotBlank(message = "Email obligatorio")
    @Email(message = "Email inválido")
    private String email;

    @NotBlank(message = "Password obligatoria")
    private String password;

    private SystemRole systemRole;

    @Override
    public String toString() {
        return "RegisterRequest{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
