package br.com.paroquiagestao.service;

import br.com.paroquiagestao.dto.GrupoRequest;
import br.com.paroquiagestao.exception.RecursoNaoEncontradoException;
import br.com.paroquiagestao.model.Grupo;
import br.com.paroquiagestao.repository.GrupoRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class GrupoServiceImpl implements GrupoService {

    private final GrupoRepository grupoRepository;

    public GrupoServiceImpl(GrupoRepository grupoRepository) {
        this.grupoRepository = grupoRepository;
    }

    @Override
    public Grupo cadastrar(GrupoRequest request) {
        Grupo grupo = new Grupo();
        grupo.setNome(request.nome());
        grupo.setResponsavel(request.responsavel());
        return grupoRepository.save(grupo);
    }

    @Override
    public List<Grupo> listar() {
        return grupoRepository.findAll();
    }

    @Override
    public Grupo buscarPorId(Long id) {
        return grupoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Grupo não encontrado: " + id));
    }
}