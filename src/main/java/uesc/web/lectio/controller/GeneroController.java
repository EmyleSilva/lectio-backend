package uesc.web.lectio.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uesc.web.lectio.dto.GeneroDTO;
import uesc.web.lectio.service.GeneroService;

import java.util.List;

@RestController
@RequestMapping("/generos")
@AllArgsConstructor
public class GeneroController {

    private final GeneroService generoService;

    @PostMapping
    public ResponseEntity<GeneroDTO.Response> criar(@Valid @RequestBody GeneroDTO.Request request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(generoService.criar(request));
    }

    @GetMapping
    public List<GeneroDTO.Response> listar() {
        return generoService.listar();
    }

    @GetMapping("/{id}")
    public GeneroDTO.Response buscarPorId(@PathVariable Long id) {
        return generoService.buscarPorId(id);
    }

    @PutMapping("/{id}")
    public GeneroDTO.Response atualizar(@PathVariable Long id, @Valid @RequestBody GeneroDTO.Request request) {
        return generoService.atualizar(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        generoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
