package br.com.paroquiagestao.service;

import br.com.paroquiagestao.dto.VendaRequest;
import br.com.paroquiagestao.model.Venda;

public interface VendaService {
    Venda registrar(VendaRequest request);
}