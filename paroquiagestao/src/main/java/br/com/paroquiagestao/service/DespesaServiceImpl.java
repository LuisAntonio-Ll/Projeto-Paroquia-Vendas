package br.com.paroquiagestao.service;

import br.com.paroquiagestao.dto.DespesaRequest;
import br.com.paroquiagestao.model.Despesa;
import br.com.paroquiagestao.model.Grupo;
import br.com.paroquiagestao.repository.DespesaRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDate;

@Service
public class DespesaServiceImpl implements DespesaService {

    private final DespesaRepository despesaRepository;
    private final GrupoService grupoService;

    public DespesaServiceImpl(DespesaRepository despesaRepository,
                              GrupoService grupoService) {
        this.despesaRepository = despesaRepository;
        this.grupoService = grupoService;
    }

    @Override
    public Despesa registrar(DespesaRequest request) {
        Grupo grupo = grupoService.buscarPorId(request.grupoId());

        Despesa despesa = new Despesa();
        despesa.setGrupo(grupo);
        despesa.setDescricao(request.descricao());
        despesa.setValor(request.valor());
        despesa.setData(request.data() != null ? request.data() : LocalDate.now());

        return despesaRepository.save(despesa);
    }
}