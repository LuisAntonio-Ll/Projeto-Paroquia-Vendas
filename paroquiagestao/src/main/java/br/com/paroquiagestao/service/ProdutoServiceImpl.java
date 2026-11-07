package br.com.paroquiagestao.service;

import br.com.paroquiagestao.dto.ProdutoRequest;
import br.com.paroquiagestao.exception.RecursoNaoEncontradoException;
import br.com.paroquiagestao.model.Produto;
import br.com.paroquiagestao.repository.ProdutoRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProdutoServiceImpl implements ProdutoService {

    private final ProdutoRepository produtoRepository;

    public ProdutoServiceImpl(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    @Override
    public Produto cadastrar(ProdutoRequest request) {
        Produto produto = new Produto();
        produto.setNome(request.nome());
        produto.setPrecoVenda(request.precoVenda());
        return produtoRepository.save(produto);
    }

    @Override
    public List<Produto> listar() {
        return produtoRepository.findAll();
    }

    @Override
    public Produto buscarPorId(Long id) {
        return produtoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Produto não encontrado: " + id));
    }
}