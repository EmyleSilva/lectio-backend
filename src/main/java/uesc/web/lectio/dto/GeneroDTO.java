package uesc.web.lectio.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class GeneroDTO {

    public record Request(
            @NotBlank @Size(max = 80) String nome
    ){}

    public record Response(
            Long id,
            String nome
    ){}
}
