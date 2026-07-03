package uesc.web.lectio.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class AuthDTO {
    public record LoginRequest(@NotBlank @Email String email, @NotBlank String senha) {}
    public record LoginResponse(Long id, String nome, String email) {}
}