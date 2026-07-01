package uesc.web.lectio.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uesc.web.lectio.dto.EstanteLivroDTO;
import uesc.web.lectio.service.EstanteLivroService;

import java.util.List;

/**
 * Relação livro <-> estante. Aninhado sob /estantes/{estanteId}/livros.
 * Use PATCH /{estanteLivroId} para atualizar status de leitura e progresso de página.
 */
@RestController
@RequestMapping("/estantes/{estanteId}/livros")
@AllArgsConstructor
public class EstanteLivroController {

    private final EstanteLivroService estanteLivroService;

    @PostMapping
    public ResponseEntity<EstanteLivroDTO.Response> adicionar(@PathVariable Long estanteId, @Valid @RequestBody EstanteLivroDTO.Request request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(estanteLivroService.adicionar(estanteId, request));
    }

    @GetMapping
    public List<EstanteLivroDTO.Response> listar(@PathVariable Long estanteId) {
        return estanteLivroService.listarPorEstante(estanteId);
    }

    @PatchMapping("/{estanteLivroId}")
    public EstanteLivroDTO.Response atualizarProgresso(@PathVariable Long estanteId, @PathVariable Long estanteLivroId, @RequestBody EstanteLivroDTO.UpdateRequest request) {
        return estanteLivroService.atualizarProgresso(estanteId, estanteLivroId, request);
    }

    @DeleteMapping("/{estanteLivroId}")
    public ResponseEntity<Void> remover(@PathVariable Long estanteId, @PathVariable Long estanteLivroId) {
        estanteLivroService.remover(estanteId, estanteLivroId);
        return ResponseEntity.noContent().build();
    }
}
