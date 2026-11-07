package br.com.paroquiagestao.service;

import br.com.paroquiagestao.dto.*;
import br.com.paroquiagestao.exception.RecursoNaoEncontradoException;
import br.com.paroquiagestao.model.Despesa;
import br.com.paroquiagestao.model.Grupo;
import br.com.paroquiagestao.model.Venda;
import br.com.paroquiagestao.repository.DespesaRepository;
import br.com.paroquiagestao.repository.VendaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PrestacaoContasServiceImplTest {

    @Mock private VendaRepository vendaRepository;
    @Mock private DespesaRepository despesaRepository;
    @Mock private GrupoService grupoService;

    @InjectMocks
    private PrestacaoContasServiceImpl service;

    private Venda venda(LocalDate data, double valorTotal) {
        Venda v = new Venda();
        v.setData(data);
        v.setValorTotal(valorTotal);
        return v;
    }

    private Despesa despesa(LocalDate data, double valor) {
        Despesa d = new Despesa();
        d.setData(data);
        d.setValor(valor);
        return d;
    }

    @Test
    void gerarBalanco_deveCalcularLucroLiquido() {
        when(grupoService.buscarPorId(1L)).thenReturn(new Grupo(1L, "Catequese", "Maria"));
        when(vendaRepository.findByGrupoId(1L)).thenReturn(List.of(
                venda(LocalDate.of(2026, 10, 4), 250.0),
                venda(LocalDate.of(2026, 11, 1), 100.0)));
        when(despesaRepository.findByGrupoId(1L)).thenReturn(List.of(
                despesa(LocalDate.of(2026, 10, 4), 120.0)));

        BalancoFinanceiroDTO balanco = service.gerarBalanco(1L);

        assertEquals("Catequese", balanco.grupoNome());
        assertEquals(350.0, balanco.totalBruto());
        assertEquals(120.0, balanco.totalDespesas());
        assertEquals(230.0, balanco.lucroLiquido());
    }

    @Test
    void gerarBalancoDiario_deveConsiderarSomenteODia() {
        LocalDate dia = LocalDate.of(2026, 10, 4);
        when(vendaRepository.findByGrupoIdAndDataBetween(1L, dia, dia))
                .thenReturn(List.of(venda(dia, 250.0)));
        when(despesaRepository.findByGrupoIdAndDataBetween(1L, dia, dia))
                .thenReturn(List.of(despesa(dia, 100.0)));

        BalancoDiarioDTO balanco = service.gerarBalancoDiario(1L, dia);

        assertEquals(dia, balanco.data());
        assertEquals(150.0, balanco.lucroLiquido());
    }

    @Test
    void gerarBalancoMensal_deveConsiderarSomenteOMes() {
        when(vendaRepository.findByGrupoIdAndDataBetween(
                1L, LocalDate.of(2026, 10, 1), LocalDate.of(2026, 10, 31)))
                .thenReturn(List.of(venda(LocalDate.of(2026, 10, 4), 250.0)));
        when(despesaRepository.findByGrupoIdAndDataBetween(
                1L, LocalDate.of(2026, 10, 1), LocalDate.of(2026, 10, 31)))
                .thenReturn(List.of(despesa(LocalDate.of(2026, 10, 4), 100.0)));

        BalancoMensalDTO balanco = service.gerarBalancoMensal(1L, 2026, 10);

        assertEquals(10, balanco.mes());
        assertEquals(250.0, balanco.totalBruto());
        assertEquals(150.0, balanco.lucroLiquido());
    }

    @Test
    void gerarBalancoAnual_deveSomarOAnoEDetalharOs12Meses() {
        when(grupoService.buscarPorId(1L)).thenReturn(new Grupo(1L, "Catequese", "Maria"));
        when(vendaRepository.findByGrupoIdAndDataBetween(
                1L, LocalDate.of(2026, 1, 1), LocalDate.of(2026, 12, 31)))
                .thenReturn(List.of(
                        venda(LocalDate.of(2026, 10, 4), 250.0),
                        venda(LocalDate.of(2026, 11, 1), 100.0)));
        when(despesaRepository.findByGrupoIdAndDataBetween(
                1L, LocalDate.of(2026, 1, 1), LocalDate.of(2026, 12, 31)))
                .thenReturn(List.of(despesa(LocalDate.of(2026, 10, 4), 120.0)));

        BalancoAnualDTO balanco = service.gerarBalancoAnual(1L, 2026);

        assertEquals(350.0, balanco.totalBruto());
        assertEquals(120.0, balanco.totalDespesas());
        assertEquals(230.0, balanco.lucroLiquido());
        assertEquals(12, balanco.meses().size());
        assertEquals(130.0, balanco.meses().get(9).lucroLiquido());   // outubro
        assertEquals(100.0, balanco.meses().get(10).lucroLiquido());  // novembro
        assertEquals(0.0, balanco.meses().get(0).totalBruto());       // janeiro
    }

    @Test
    void gerarBalancoMensal_mesInvalido_deveLancarExcecao() {
        assertThrows(DateTimeException.class,
                () -> service.gerarBalancoMensal(1L, 2026, 13));
    }

    @Test
    void gerarBalanco_grupoInexistente_deveLancarExcecao() {
        when(grupoService.buscarPorId(99L))
                .thenThrow(new RecursoNaoEncontradoException("Grupo não encontrado: 99"));

        assertThrows(RecursoNaoEncontradoException.class, () -> service.gerarBalanco(99L));
    }
}