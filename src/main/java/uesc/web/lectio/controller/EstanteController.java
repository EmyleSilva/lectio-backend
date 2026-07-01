package uesc.web.lectio.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uesc.web.lectio.dto.EstanteDTO;
import uesc.web.lectio.service.EstanteService;

import java.util.List;

/**
 * Estante sempre pertence a um usuário, por isso fica aninhada sob /usuarios/{usuarioId}.
 */
@RestController
@RequestMapping("usuarios/{usuarioId}/estantes")
@AllArgsConstructor
public class EstanteController {

    private final EstanteService estanteService;

    @PostMapping
    public ResponseEntity<EstanteDTO.Response> criar(@PathVariable Long usuarioId, @Valid @RequestBody EstanteDTO.Request request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(estanteService.criar(usuarioId, request));
    }

    @GetMapping
    public List<EstanteDTO.Response> listar(@PathVariable Long usuarioId) {
        return estanteService.listarPorUsuario(usuarioId);
    }

    @GetMapping("/{id}")
    public EstanteDTO.Response buscarPorId(@PathVariable Long usuarioId, @PathVariable Long id) {
        return estanteService.buscarPorIdEUsuario(id, usuarioId);
    }

    @PutMapping("/{id}")
    public EstanteDTO.Response atualizar(@PathVariable Long usuarioId, @PathVariable Long id,
                                         @Valid @RequestBody EstanteDTO.Request request) {
        return estanteService.atualizar(id, usuarioId, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long usuarioId, @PathVariable Long id) {
        estanteService.deletar(id, usuarioId);
        return ResponseEntity.noContent().build();
    }
}
