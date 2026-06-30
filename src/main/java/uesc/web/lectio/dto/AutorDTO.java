package uesc.web.lectio.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public class AutorDTO {

    public record Request(
            @NotBlank @Size(max = 160) String nome,
            String biografia,
            LocalDate dataNascimento,
            @Size(max = 80) String nacionalidade
    ){}

    public record Response(
       Long id,
       String nome,
       String biografia,
       LocalDate dataNascimento,
       String nacionalidade
    ){}
}
