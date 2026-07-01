package uesc.web.lectio.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uesc.web.lectio.dto.GeneroDTO;
import uesc.web.lectio.exception.ResourceNotFoundException;
import uesc.web.lectio.model.Genero;
import uesc.web.lectio.repository.GeneroRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class GeneroService {

    private final GeneroRepository generoRepository;

    @Transactional
    public GeneroDTO.Response criar(GeneroDTO.Request request) {
        Genero genero = new Genero();
        genero.setNome(request.nome());
        return toResponse(generoRepository.save(genero));
    }

    @Transactional(readOnly = true)
    public List<GeneroDTO.Response> listar() {
        return generoRepository.findAll().stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public GeneroDTO.Response buscarPorId(Long id) {
        return toResponse(buscarEntidade(id));
    }

    @Transactional
    public GeneroDTO.Response atualizar(Long id, GeneroDTO.Request request) {
        Genero genero = buscarEntidade(id);
        genero.setNome(request.nome());
        return toResponse(genero);
    }

    @Transactional
    public void deletar(Long id) {
        generoRepository.delete(buscarEntidade(id));
    }

    protected Genero buscarEntidade(Long id) {
        return generoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Gênero Não Encontrado: " + id));
    }

    private GeneroDTO.Response toResponse(Genero g) {
        return new GeneroDTO.Response(g.getId(), g.getNome());
    }
}
