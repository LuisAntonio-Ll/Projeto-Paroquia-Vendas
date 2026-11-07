package br.com.paroquiagestao.service;

import br.com.paroquiagestao.dto.VendaRequest;
import br.com.paroquiagestao.model.Grupo;
import br.com.paroquiagestao.model.Produto;
import br.com.paroquiagestao.model.Venda;
import br.com.paroquiagestao.repository.VendaRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDate;

@Service
public class VendaServiceImpl implements VendaService {

    private final VendaRepository vendaRepository;
    private final GrupoService grupoService;
    private final ProdutoService produtoService;

    public VendaServiceImpl(VendaRepository vendaRepository,
                            GrupoService grupoService,
                            ProdutoService produtoService) {
        this.vendaRepository = vendaRepository;
        this.grupoService = grupoService;
        this.produtoService = produtoService;
    }

    @Override
    public Venda registrar(VendaRequest request) {
        Grupo grupo = grupoService.buscarPorId(request.grupoId());
        Produto produto = produtoService.buscarPorId(request.produtoId());

        Venda venda = new Venda();
        venda.setGrupo(grupo);
        venda.setProduto(produto);
        venda.setQuantidade(request.quantidade());
        venda.setData(request.data() != null ? request.data() : LocalDate.now());
        venda.setValorTotal(venda.calcularTotal());

        return vendaRepository.save(venda);
    }
}