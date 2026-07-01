package uesc.web.lectio.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uesc.web.lectio.dto.EstanteDTO;
import uesc.web.lectio.exception.ResourceNotFoundException;
import uesc.web.lectio.model.Estante;
import uesc.web.lectio.model.Usuario;
import uesc.web.lectio.repository.EstanteRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class EstanteService {

    private final EstanteRepository estanteRepository;
    private final UsuarioService usuarioService;

    @Transactional
    public EstanteDTO.Response criar(Long usuarioId, EstanteDTO.Request request) {
        Usuario usuario = usuarioService.buscarEntidade(usuarioId);
        Estante estante = new Estante();
        estante.setUsuario(usuario);
        estante.setNome(request.nome());
        estante.setTipo(request.tipo());
        estante.setDescricao(request.descricao());
        return toResponse(estanteRepository.save(estante));
    }

    @Transactional(readOnly = true)
    public List<EstanteDTO.Response> listarPorUsuario(Long usuarioId) {
        usuarioService.buscarEntidade(usuarioId); // valida que o usuário existe
        return estanteRepository.findByUsuarioId(usuarioId).stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public EstanteDTO.Response buscarPorIdEUsuario(Long id, Long usuarioId) {
        return toResponse(buscarEntidade(id, usuarioId));
    }

    @Transactional
    public EstanteDTO.Response atualizar(Long id, Long usuarioId, EstanteDTO.Request request) {
        Estante estante = buscarEntidade(id, usuarioId);
        estante.setNome(request.nome());
        estante.setTipo(request.tipo());
        estante.setDescricao(request.descricao());
        return toResponse(estante);
    }

    @Transactional
    public void deletar(Long id, Long usuarioId) {
        estanteRepository.delete(buscarEntidade(id, usuarioId));
    }

    protected Estante buscarEntidade(Long id, Long usuarioId) {
        return estanteRepository.findByIdAndUsuarioId(id, usuarioId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Estante " + id + " não encontrada para o usuário " + usuarioId));
    }

    private EstanteDTO.Response toResponse(Estante e) {
        return new EstanteDTO.Response(
                e.getId(), e.getUsuario().getId(), e.getNome(), e.getTipo(), e.getDescricao(), e.getDataCriacao()
        );
    }
}
