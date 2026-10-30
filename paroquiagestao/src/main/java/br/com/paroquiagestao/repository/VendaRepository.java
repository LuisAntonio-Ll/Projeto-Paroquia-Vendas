package br.com.paroquiagestao.repository;

import br.com.paroquiagestao.model.Venda;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface VendaRepository extends JpaRepository<Venda, Long> {
    List<Venda> findByGrupoId(Long grupoId);
}