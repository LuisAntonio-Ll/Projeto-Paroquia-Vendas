package br.com.paroquiagestao.service;

import br.com.paroquiagestao.dto.BalancoFinanceiroDTO;
import br.com.paroquiagestao.model.Despesa;
import br.com.paroquiagestao.model.Grupo;
import br.com.paroquiagestao.model.Venda;
import br.com.paroquiagestao.repository.DespesaRepository;
import br.com.paroquiagestao.repository.VendaRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PrestacaoContasServiceImpl implements PrestacaoContasService {

    private final VendaRepository vendaRepository;
    private final DespesaRepository despesaRepository;
    private final GrupoService grupoService;

    public PrestacaoContasServiceImpl(VendaRepository vendaRepository,
                                      DespesaRepository despesaRepository,
                                      GrupoService grupoService) {
        this.vendaRepository = vendaRepository;
        this.despesaRepository = despesaRepository;
        this.grupoService = grupoService;
    }

    @Override
    public BalancoFinanceiroDTO gerarBalanco(Long grupoId) {
        Grupo grupo = grupoService.buscarPorId(grupoId);

        List<Venda> vendas = vendaRepository.findByGrupoId(grupoId);
        List<Despesa> despesas = despesaRepository.findByGrupoId(grupoId);

        double totalBruto = vendas.stream()
                .mapToDouble(Venda::getValorTotal).sum();
        double totalDespesas = despesas.stream()
                .mapToDouble(Despesa::getValor).sum();
        double lucroLiquido = totalBruto - totalDespesas;

        return new BalancoFinanceiroDTO(
                grupo.getId(), grupo.getNome(),
                totalBruto, totalDespesas, lucroLiquido);
    }
}