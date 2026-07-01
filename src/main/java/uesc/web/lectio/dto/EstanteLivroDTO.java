package uesc.web.lectio.dto;

import jakarta.validation.constraints.NotNull;
import uesc.web.lectio.model.enums.StatusLeitura;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class EstanteLivroDTO {

    // Usado para ADICIONAR um livro a uma estante. estanteId vem do path.
    public record Request(
            @NotNull Long livroId,
            StatusLeitura statusLeitura // se nulo, o service assume QUERO_LER
    ){}

    // Usado para atualizar status/progresso de leitura (PATCH)
    public record UpdateRequest(
            StatusLeitura statusLeitura,
            LocalDate dataInicioLeitura,
            LocalDate dataFimLeitura,
            Integer paginaAtual
    ){}

    public record Response(
            Long id,
            Long estanteId,
            LivroDTO.Resumo livro,
            StatusLeitura statusLeitura,
            LocalDateTime dataAdicionado,
            LocalDate dataInicioLeitura,
            LocalDate dataFimLeitura,
            Integer paginaAtual
    ){}
}
