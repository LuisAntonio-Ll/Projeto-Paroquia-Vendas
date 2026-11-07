package br.com.paroquiagestao.repository;

import br.com.paroquiagestao.model.Venda;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;

public interface VendaRepository extends JpaRepository<Venda, Long> {
    List<Venda> findByGrupoId(Long grupoId);
    List<Venda> findByGrupoIdAndDataBetween(Long grupoId, LocalDate inicio, LocalDate fim);
}