package uesc.web.lectio.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uesc.web.lectio.dto.EditoraDTO;
import uesc.web.lectio.service.EditoraService;

import java.util.List;

@RestController
@RequestMapping("/editoras")
@AllArgsConstructor
public class EditoraController {

    private final EditoraService editoraService;

    @PostMapping
    public ResponseEntity<EditoraDTO.Response> criar(@Valid @RequestBody EditoraDTO.Request request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(editoraService.criar(request));
    }

    @GetMapping
    public List<EditoraDTO.Response> listar() {
        return editoraService.listar();
    }

    @GetMapping("/{id}")
    public EditoraDTO.Response buscarPorId(@PathVariable Long id) {
        return editoraService.buscarPorId(id);
    }

    @PutMapping("/{id}")
    public EditoraDTO.Response atualizar(@PathVariable Long id, @Valid @RequestBody EditoraDTO.Request request) {
        return editoraService.atualizar(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        editoraService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
