package br.com.paroquiagestao.service;

import br.com.paroquiagestao.dto.ProdutoRequest;
import br.com.paroquiagestao.model.Produto;
import java.util.List;

public interface ProdutoService {
    Produto cadastrar(ProdutoRequest request);
    List<Produto> listar();
    Produto buscarPorId(Long id);
}