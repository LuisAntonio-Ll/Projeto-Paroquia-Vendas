package br.com.paroquiagestao.service;

import br.com.paroquiagestao.dto.GrupoRequest;
import br.com.paroquiagestao.model.Grupo;
import java.util.List;

public interface GrupoService {
    Grupo cadastrar(GrupoRequest request);
    List<Grupo> listar();
    Grupo buscarPorId(Long id);
}