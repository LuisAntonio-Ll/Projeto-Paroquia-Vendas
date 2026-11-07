package br.com.paroquiagestao.service;

import br.com.paroquiagestao.dto.VendaRequest;
import br.com.paroquiagestao.model.Grupo;
import br.com.paroquiagestao.model.Produto;
import br.com.paroquiagestao.model.Venda;
import br.com.paroquiagestao.repository.VendaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VendaServiceImplTest {

    @Mock private VendaRepository vendaRepository;
    @Mock private GrupoService grupoService;
    @Mock private ProdutoService produtoService;

    @InjectMocks
    private VendaServiceImpl service;

    private void prepararMocks() {
        when(grupoService.buscarPorId(1L)).thenReturn(new Grupo(1L, "Catequese", "Maria"));
        when(produtoService.buscarPorId(1L)).thenReturn(new Produto(1L, "Feijoada", 25.0));
        when(vendaRepository.save(any(Venda.class))).thenAnswer(i -> i.getArgument(0));
    }

    @Test
    void registrar_deveCalcularValorTotal() {
        prepararMocks();

        Venda venda = service.registrar(new VendaRequest(1L, 1L, 10, LocalDate.of(2026, 10, 4)));

        assertEquals(250.0, venda.getValorTotal());
        assertEquals(LocalDate.of(2026, 10, 4), venda.getData());
    }

    @Test
    void registrar_semData_deveUsarDataDeHoje() {
        prepararMocks();

        Venda venda = service.registrar(new VendaRequest(1L, 1L, 2, null));

        assertEquals(LocalDate.now(), venda.getData());
        assertEquals(50.0, venda.getValorTotal());
    }
}