package uesc.web.lectio.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class EditoraDTO {

    public record Request(
            @NotBlank @Size (max = 120) String nome
    ){}

    public record Response(
            Long id,
            String nome
    ){}
}
