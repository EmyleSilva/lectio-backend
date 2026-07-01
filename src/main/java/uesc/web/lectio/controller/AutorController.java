package uesc.web.lectio.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uesc.web.lectio.dto.AutorDTO;
import uesc.web.lectio.service.AutorService;

import java.util.List;

@RestController
@RequestMapping("/autores")
@AllArgsConstructor
public class AutorController {

    private final AutorService autorService;

    @PostMapping
    public ResponseEntity<AutorDTO.Response> criar(@Valid @RequestBody AutorDTO.Request request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(autorService.criar(request));
    }

    @GetMapping
    public List<AutorDTO.Response> listar(@RequestParam(required = false) String nome) {
        return autorService.listar(nome);
    }

    @GetMapping("/{id}")
    public AutorDTO.Response buscarPorId(@PathVariable Long id) {
        return autorService.buscarPorId(id);
    }

    @PutMapping("/{id}")
    public AutorDTO.Response atualizar(@PathVariable Long id, @Valid @RequestBody AutorDTO.Request request) {
        return autorService.atualizar(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        autorService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
