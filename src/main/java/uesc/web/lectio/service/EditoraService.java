package uesc.web.lectio.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uesc.web.lectio.dto.EditoraDTO;
import uesc.web.lectio.exception.ResourceNotFoundException;
import uesc.web.lectio.model.Editora;
import uesc.web.lectio.repository.EditoraRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class EditoraService {

    private final EditoraRepository editoraRepository;

    @Transactional
    public EditoraDTO.Response criar(EditoraDTO.Request request) {
        Editora editora = new Editora();
        editora.setNome(request.nome());
        return toResponse(editoraRepository.save(editora));
    }

    @Transactional(readOnly = true)
    public List<EditoraDTO.Response> listar() {
        return editoraRepository.findAll().stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public EditoraDTO.Response buscarPorId(Long id) {
        return toResponse(buscarEntidade(id));
    }

    @Transactional
    public EditoraDTO.Response atualizar(Long id, EditoraDTO.Request request) {
        Editora editora = new Editora();
        editora.setNome(request.nome());
        return toResponse(editora);
    }

    @Transactional
    public void deletar(Long id) {
        editoraRepository.delete(buscarEntidade(id));
    }

    protected Editora buscarEntidade(Long id) {
        return editoraRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Editora Não Encontrada: " + id));
    }

    private EditoraDTO.Response toResponse(Editora e) {
        return new EditoraDTO.Response(e.getId(), e.getNome());
    }
}
