package uesc.web.lectio.service;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uesc.web.lectio.dto.AuthDTO;
import uesc.web.lectio.exception.AuthenticationFailedException;
import org.springframework.transaction.annotation.Transactional;
import uesc.web.lectio.dto.UsuarioDTO;
import uesc.web.lectio.exception.ResourceNotFoundException;
import uesc.web.lectio.model.Usuario;
import uesc.web.lectio.repository.UsuarioRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UsuarioDTO.Response criar(UsuarioDTO.Request request) {
        if (usuarioRepository.existsByEmail(request.email())) {
            throw new IllegalArgumentException("Já existe um usuário cadastrado com esse e-mail");
        }
        Usuario usuario = new Usuario();
        usuario.setNome(request.nome());
        usuario.setEmail(request.email());
        usuario.setSenhaHash(passwordEncoder.encode(request.senha()));
        return toResponse(usuarioRepository.save(usuario));
    }

    @Transactional(readOnly = true)
    public List<UsuarioDTO.Response> listar() {
        return usuarioRepository.findAll().stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public UsuarioDTO.Response buscarPorId(Long id) {
        return toResponse(buscarEntidade(id));
    }

    @Transactional
    public UsuarioDTO.Response atualizar(Long id, UsuarioDTO.UpdateRequest request) {
        Usuario usuario = buscarEntidade(id);
        usuario.setNome(request.nome());
        usuario.setFotoPerfilUrl(request.fotoPerfilUrl());
        usuario.setBio(request.bio());
        return toResponse(usuario);
    }

    @Transactional
    public void deletar(Long id) {
        Usuario usuario = buscarEntidade(id);
        usuarioRepository.delete(usuario);
    }

    
    @Transactional(readOnly = true)
    public AuthDTO.LoginResponse autenticar(AuthDTO.LoginRequest request) {
        // 1. Busca pelo email
        Usuario usuario = usuarioRepository.findByEmail(request.email())
                .orElseThrow(() -> new AuthenticationFailedException("E-mail ou senha inválidos"));
        
        // 2. Compara a senha digitada com o Hash do banco
        if (!passwordEncoder.matches(request.senha(), usuario.getSenhaHash())) {
            throw new AuthenticationFailedException("E-mail ou senha inválidos");
        }
        
        // 3. Devolve a resposta limpa e segura
        return new AuthDTO.LoginResponse(usuario.getId(), usuario.getNome(), usuario.getEmail());
    }

    protected Usuario buscarEntidade(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário Não Encontrado: " + id));
    }

    private UsuarioDTO.Response toResponse(Usuario u) {
        return new UsuarioDTO.Response(
                u.getId(), u.getNome(), u.getEmail(), u.getFotoPerfilUrl(), u.getBio(), u.getDataCadastro()
        );
    }
}
