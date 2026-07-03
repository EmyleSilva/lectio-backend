package uesc.web.lectio.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uesc.web.lectio.dto.AuthDTO;
import uesc.web.lectio.service.UsuarioService;

@RestController
@RequestMapping("/login")
@AllArgsConstructor
public class AuthController {
    
    private final UsuarioService usuarioService;

    @PostMapping
    public AuthDTO.LoginResponse login(@Valid @RequestBody AuthDTO.LoginRequest request) {
        return usuarioService.autenticar(request);
    }
}