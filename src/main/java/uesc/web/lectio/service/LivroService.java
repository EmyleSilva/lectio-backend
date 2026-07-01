package uesc.web.lectio.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uesc.web.lectio.dto.AutorDTO;
import uesc.web.lectio.dto.EditoraDTO;
import uesc.web.lectio.dto.GeneroDTO;
import uesc.web.lectio.dto.LivroDTO;
import uesc.web.lectio.exception.ResourceNotFoundException;
import uesc.web.lectio.model.Autor;
import uesc.web.lectio.model.Editora;
import uesc.web.lectio.model.Genero;
import uesc.web.lectio.model.Livro;
import uesc.web.lectio.repository.AutorRepository;
import uesc.web.lectio.repository.EditoraRepository;
import uesc.web.lectio.repository.GeneroRepository;
import uesc.web.lectio.repository.LivroRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class LivroService {

    private final LivroRepository livroRepository;
    private final EditoraRepository editoraRepository;
    private final AutorRepository autorRepository;
    private final GeneroRepository generoRepository;

    @Transactional
    public LivroDTO.Response criar(LivroDTO.Request request) {
        Livro livro = new Livro();
        System.out.println("Passa antes?");
        mapearParaCamposSimples(livro, request);
        System.out.println("Chega depois de campos simples?");
        aplicarRelacionamentos(livro, request);
        System.out.println("aplicou relacionamentos");
        return toResponse(livroRepository.save(livro));
    }

    @Transactional(readOnly = true)
    public List<LivroDTO.Response> listar(String titulo) {
        List<Livro> livros = (titulo == null || titulo.isBlank())
                ? livroRepository.findAll()
                : livroRepository.findByTituloContainingIgnoreCase(titulo);
        return livros.stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public LivroDTO.Response buscarPorId(Long id) {
        return toResponse(buscarEntidade(id));
    }

    @Transactional
    public LivroDTO.Response atualizar(Long id, LivroDTO.Request request) {
        Livro livro = buscarEntidade(id);
        mapearParaCamposSimples(livro, request);
        aplicarRelacionamentos(livro, request);
        return toResponse(livro);
    }

    @Transactional
    public void deletar(Long id) {
        livroRepository.delete(buscarEntidade(id));
    }

    protected Livro buscarEntidade(Long id) {
        return livroRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Livro Não Encontrado: " + id));
    }

    private void mapearParaCamposSimples(Livro livro, LivroDTO.Request request) {
        livro.setTitulo(request.titulo());
        livro.setSinopse(request.sinopse());
        livro.setIsbn(request.isbn());
        livro.setAnoPublicacao(request.anoPublicacao());
        livro.setIdioma(request.idioma());
        livro.setNumeroPaginas(request.numeroPaginas());
        livro.setCapaUrl(request.capaUrl());
    }

    private void aplicarRelacionamentos(Livro livro, LivroDTO.Request request) {
        if (request.editoraId() != null) {
            Editora editora = editoraRepository.findById(request.editoraId())
                    .orElseThrow(()-> new ResourceNotFoundException("Editora Não Encontrada: " + request.editoraId()));
            livro.setEditora(editora);
        }else {
            livro.setEditora(null);
        }

        System.out.println("========================================");
        System.out.println("AUTORES ID: ");
        for (Long autorId : request.autorIds()) {
            System.out.println("ID: " + autorId);
        }
        System.out.println("========================================");

        Set<Autor> autores = new HashSet<>();
        if (request.autorIds() != null) {
            for (Long autorId : request.autorIds()) {
                autores.add(autorRepository.findById(autorId)
                        .orElseThrow(() -> new ResourceNotFoundException("Autor Não Encontrado: " + autorId)));
            }
        }
        livro.setAutores(autores);

        Set<Genero> generos = new HashSet<>();
        if (request.generoIds() != null) {
            for (Long generoId : request.generoIds()) {
                generos.add(generoRepository.findById(generoId)
                        .orElseThrow(()-> new ResourceNotFoundException("Gênero Não Encontrado" + generoId)));

            }
        }
        livro.setGeneros(generos);
    }

    private LivroDTO.Response toResponse(Livro l) {
        EditoraDTO.Response editoraDTO = l.getEditora() == null ? null
                : new EditoraDTO.Response(l.getEditora().getId(), l.getEditora().getNome());

        Set<AutorDTO.Response> autoresDTO = l.getAutores().stream()
                .map(a -> new AutorDTO.Response(a.getId(), a.getNome(), a.getBiografia(), a.getDataNascimento(), a.getNacionalidade()))
                .collect(java.util.stream.Collectors.toSet());

        Set<GeneroDTO.Response> generosDTO = l.getGeneros().stream()
                .map(g -> new GeneroDTO.Response(g.getId(), g.getNome()))
                .collect(java.util.stream.Collectors.toSet());

        return new LivroDTO.Response(
                l.getId(), l.getTitulo(), l.getSinopse(), l.getIsbn(), l.getAnoPublicacao(),
                l.getIdioma(), l.getNumeroPaginas(), l.getCapaUrl(), editoraDTO, autoresDTO, generosDTO
        );
    }

    // Usado pelo EstanteLivroService para montar o resumo aninhado
    protected LivroDTO.Resumo toResumo(Livro l) {
        return new LivroDTO.Resumo(l.getId(), l.getTitulo(), l.getCapaUrl(), l.getNumeroPaginas());
    }
}
