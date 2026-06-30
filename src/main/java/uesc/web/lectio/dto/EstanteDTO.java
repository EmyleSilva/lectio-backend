package uesc.web.lectio.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import uesc.web.lectio.model.enums.TipoEstante;

import java.time.LocalDateTime;

public class EstanteDTO {

    /** usuarioId vem do path (/usuarios/{usuarioId}/estantes), não do corpo */
    public record Request(
            @NotBlank @Size(max = 80) String nome,
            @NotNull TipoEstante tipo,
            String descricao
    ){}

    public record Response(
            Long id,
            Long usuarioId,
            String nome,
            TipoEstante tipo,
            String descricao,
            LocalDateTime dataCriacao
    ){}

}
