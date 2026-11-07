package br.com.paroquiagestao.service;

import br.com.paroquiagestao.dto.BalancoAnualDTO;
import br.com.paroquiagestao.dto.BalancoDiarioDTO;
import br.com.paroquiagestao.dto.BalancoFinanceiroDTO;
import br.com.paroquiagestao.dto.BalancoMensalDTO;
import br.com.paroquiagestao.model.Despesa;
import br.com.paroquiagestao.model.Grupo;
import br.com.paroquiagestao.model.Venda;
import br.com.paroquiagestao.repository.DespesaRepository;
import br.com.paroquiagestao.repository.VendaRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.stream.IntStream;

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

        double bruto = somarVendas(vendas);
        double gastos = somarDespesas(despesas);
        return new BalancoFinanceiroDTO(grupo.getId(), grupo.getNome(),
                bruto, gastos, bruto - gastos);
    }

    @Override
    public BalancoMensalDTO gerarBalancoMensal(Long grupoId, int ano, int mes) {
        grupoService.buscarPorId(grupoId);
        YearMonth periodo = YearMonth.of(ano, mes);

        List<Venda> vendas = vendaRepository.findByGrupoIdAndDataBetween(
                grupoId, periodo.atDay(1), periodo.atEndOfMonth());
        List<Despesa> despesas = despesaRepository.findByGrupoIdAndDataBetween(
                grupoId, periodo.atDay(1), periodo.atEndOfMonth());

        return montarMensal(ano, mes, vendas, despesas);
    }

    @Override
    public BalancoAnualDTO gerarBalancoAnual(Long grupoId, int ano) {
        Grupo grupo = grupoService.buscarPorId(grupoId);
        LocalDate inicio = LocalDate.of(ano, 1, 1);
        LocalDate fim = LocalDate.of(ano, 12, 31);

        List<Venda> vendas = vendaRepository.findByGrupoIdAndDataBetween(grupoId, inicio, fim);
        List<Despesa> despesas = despesaRepository.findByGrupoIdAndDataBetween(grupoId, inicio, fim);

        List<BalancoMensalDTO> meses = IntStream.rangeClosed(1, 12)
                .mapToObj(m -> montarMensal(ano, m,
                        vendas.stream().filter(v -> v.getData().getMonthValue() == m).toList(),
                        despesas.stream().filter(d -> d.getData().getMonthValue() == m).toList()))
                .toList();

        double bruto = somarVendas(vendas);
        double gastos = somarDespesas(despesas);
        return new BalancoAnualDTO(grupo.getId(), grupo.getNome(), ano,
                bruto, gastos, bruto - gastos, meses);
    }

    private BalancoMensalDTO montarMensal(int ano, int mes,
                                          List<Venda> vendas, List<Despesa> despesas) {
        double bruto = somarVendas(vendas);
        double gastos = somarDespesas(despesas);
        return new BalancoMensalDTO(ano, mes, bruto, gastos, bruto - gastos);
    }

    private double somarVendas(List<Venda> vendas) {
        return vendas.stream().mapToDouble(Venda::getValorTotal).sum();
    }

    private double somarDespesas(List<Despesa> despesas) {
        return despesas.stream().mapToDouble(Despesa::getValor).sum();
    }

    @Override
    public BalancoDiarioDTO gerarBalancoDiario(Long grupoId, LocalDate data) {
    grupoService.buscarPorId(grupoId);

    List<Venda> vendas = vendaRepository.findByGrupoIdAndDataBetween(grupoId, data, data);
    List<Despesa> despesas = despesaRepository.findByGrupoIdAndDataBetween(grupoId, data, data);

    double bruto = somarVendas(vendas);
    double gastos = somarDespesas(despesas);
    return new BalancoDiarioDTO(data, bruto, gastos, bruto - gastos);
    }
}