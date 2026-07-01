package uesc.web.lectio.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uesc.web.lectio.dto.LivroDTO;
import uesc.web.lectio.service.LivroService;

import java.util.List;

@RestController
@RequestMapping("/livros")
@AllArgsConstructor
public class LivroController {

    private final LivroService livroService;

    @PostMapping
    public ResponseEntity<LivroDTO.Response> criar(@Valid @RequestBody LivroDTO.Request request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(livroService.criar(request));
    }

    @GetMapping
    public List<LivroDTO.Response> listar(@RequestParam(required = false) String titulo) {
        return livroService.listar(titulo);
    }

    @GetMapping("/{id}")
    public LivroDTO.Response buscarPorId(@PathVariable Long id) {
        return livroService.buscarPorId(id);
    }

    @PutMapping("/{id}")
    public LivroDTO.Response atualizar(@PathVariable Long id, @Valid @RequestBody LivroDTO.Request request) {
        return livroService.atualizar(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        livroService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
