package br.com.paroquiagestao.repository;

import br.com.paroquiagestao.model.Despesa;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface DespesaRepository extends JpaRepository<Despesa, Long> {
    List<Despesa> findByGrupoId(Long grupoId);
}