package br.com.paroquiagestao.repository;

import br.com.paroquiagestao.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
}