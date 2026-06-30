package uesc.web.lectio.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public class UsuarioDTO {

    //Criacao (POST)
    public record Request(
            @NotBlank @Size(max = 120) String nome,
            @NotBlank @Email @Size(max = 160) String email,
            @NotBlank @Size(min = 6, max = 100) String senha
    ){}

    //Atualizacao (PUT) - sem senha
    public record UpdateRequest(
            @NotBlank @Size(max = 120) String nome,
            String fotoPerfilUrl,
            String bio
    ) {}

    public record Response(
            Long id,
            String nome,
            String email,
            String fotoPerfilUrl,
            String bio,
            LocalDateTime dataCadastro
    ){}
}
