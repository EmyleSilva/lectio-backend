package uesc.web.lectio.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uesc.web.lectio.dto.EstanteLivroDTO;
import uesc.web.lectio.exception.ResourceNotFoundException;
import uesc.web.lectio.model.Estante;
import uesc.web.lectio.model.EstanteLivro;
import uesc.web.lectio.model.Livro;
import uesc.web.lectio.model.enums.StatusLeitura;
import uesc.web.lectio.repository.EstanteLivroRepository;
import uesc.web.lectio.repository.EstanteRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class EstanteLivroService {

    private final EstanteLivroRepository estanteLivroRepository;
    private final EstanteRepository estanteRepository;
    private final LivroService livroService;

    @Transactional
    public EstanteLivroDTO.Response adicionar(Long estanteId, EstanteLivroDTO.Request request) {
        Estante estante = buscarEstante(estanteId);
        Livro livro = livroService.buscarEntidade(request.livroId());

        if (estanteLivroRepository.existsByEstanteIdAndLivroId(estanteId, livro.getId())) {
            throw new IllegalArgumentException("Esse livro já está nessa estante");
        }

        EstanteLivro estanteLivro = new EstanteLivro();
        estanteLivro.setEstante(estante);
        estanteLivro.setLivro(livro);
        estanteLivro.setStatusLeitura(request.statusLeitura() != null ? request.statusLeitura() : StatusLeitura.QUERO_LER);
        return toResponse(estanteLivroRepository.save(estanteLivro));
    }

    @Transactional(readOnly = true)
    public List<EstanteLivroDTO.Response> listarPorEstante(Long estanteId) {
        buscarEstante(estanteId); //Para validar a existência da estante
        return estanteLivroRepository.findByEstanteId(estanteId).stream().map(this::toResponse).toList();
    }

    @Transactional
    public EstanteLivroDTO.Response atualizarProgresso(Long estanteId, Long estanteLivroId, EstanteLivroDTO.UpdateRequest request) {
        EstanteLivro estanteLivro = buscarEntidade(estanteLivroId, estanteId);

        if (request.statusLeitura() != null) {
            estanteLivro.setStatusLeitura(request.statusLeitura());
        }
        if (request.dataInicioLeitura() != null) {
            estanteLivro.setDataInicioLeitura(request.dataInicioLeitura());
        }

        if (request.dataFimLeitura() != null) {
            estanteLivro.setDataFimLeitura(request.dataFimLeitura());
        }
        if (request.paginaAtual() != null) {
            estanteLivro.setPaginaAtual(request.paginaAtual());
        }
        return toResponse(estanteLivro);
    }

    @Transactional
    public void remover(Long estanteId, Long estanteLivroId) {
        estanteLivroRepository.delete(buscarEntidade(estanteLivroId, estanteId));
    }

    private Estante buscarEstante(Long estanteId) {
        return estanteRepository.findById(estanteId)
                .orElseThrow(()-> new ResourceNotFoundException("Estante Não Encontrada: " + estanteId));
    }

    private EstanteLivro buscarEntidade(Long id, Long estanteId) {
        return estanteLivroRepository.findByIdAndEstanteId(id, estanteId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Registro " + id + " não encontrado na estante " + estanteId
                ));
    }

    private EstanteLivroDTO.Response toResponse(EstanteLivro el) {
        return new EstanteLivroDTO.Response(
                el.getId(),
                el.getEstante().getId(),
                livroService.toResumo(el.getLivro()),
                el.getStatusLeitura(),
                el.getDataAdicionado(),
                el.getDataInicioLeitura(),
                el.getDataFimLeitura(),
                el.getPaginaAtual()
        );
    }
}
