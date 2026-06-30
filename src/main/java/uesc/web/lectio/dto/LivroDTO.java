package uesc.web.lectio.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.ISBN;

import java.util.Set;

public class LivroDTO {

    public record Request(
            @NotBlank @Size(max = 255) String titulo,
            String sinopse,
            @Size(max = 20) @ISBN String isbn,
            Integer anoPublicacao,
            @Size(max = 40) String idioma,
            Integer numeroPaginas,
            String capaUrl,
            Long editoraId,
            Set<Long> autorIds,
            Set<Long> generoIds
    ){}

    public record Response(
            Long id,
            String titulo,
            String sinopse,
            String isbn,
            Integer anoPublicacao,
            String idioma,
            Integer numeroPaginas,
            String capaUrl,
            EditoraDTO.Response editora,
            Set<AutorDTO.Response> autores,
            Set<GeneroDTO.Response> generos
    ){}

    // Versão enxuta, usada quando o livro aparece aninhado dentro de outra resposta (ex: EstanteLivro)
    public record Resumo(
            Long id,
            String titulo,
            String capaUrl,
            Integer numeroPaginas
    ){}
}
