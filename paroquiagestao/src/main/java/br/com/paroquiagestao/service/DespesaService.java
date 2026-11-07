package br.com.paroquiagestao.service;

import br.com.paroquiagestao.dto.DespesaRequest;
import br.com.paroquiagestao.model.Despesa;

public interface DespesaService {
    Despesa registrar(DespesaRequest request);
}