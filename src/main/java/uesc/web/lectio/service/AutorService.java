package uesc.web.lectio.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import uesc.web.lectio.dto.AutorDTO;
import uesc.web.lectio.exception.ResourceNotFoundException;
import uesc.web.lectio.model.Autor;
import uesc.web.lectio.repository.AutorRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class AutorService {

    private final AutorRepository autorRepository;

    @Transactional
    public AutorDTO.Response criar(AutorDTO.Request request) {
        Autor autor = new Autor();
        mapearParaEntidade(autor, request);
        return toResponse(autorRepository.save(autor));
    }

    @Transactional(readOnly = true)
    public List<AutorDTO.Response> listar(String nome) {
        List<Autor> autores = (nome == null || nome.isBlank())
                ? autorRepository.findAll()
                : autorRepository.findByNomeContainingIgnoreCase(nome);
        return autores.stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public AutorDTO.Response buscarPorId(Long id) {
        return toResponse(buscarEntidade(id));
    }

    @Transactional
    public AutorDTO.Response atualizar(Long id, AutorDTO.Request request) {
        Autor autor = buscarEntidade(id);
        mapearParaEntidade(autor, request);
        return toResponse(autor);
    }

    @Transactional
    public void deletar(Long id) {
        autorRepository.delete(buscarEntidade(id));
    }

    protected Autor buscarEntidade(Long id) {
        return autorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Autor Não Encontrado: " + id));
    }

    private void mapearParaEntidade(Autor autor, AutorDTO.Request request) {
        autor.setNome(request.nome());
        autor.setBiografia(request.biografia());
        autor.setDataNascimento(request.dataNascimento());
        autor.setNacionalidade(request.nacionalidade());
    }

    private AutorDTO.Response toResponse(Autor a) {
        return new AutorDTO.Response(a.getId(), a.getNome(), a.getBiografia(), a.getDataNascimento(), a.getNacionalidade());
    }
}
