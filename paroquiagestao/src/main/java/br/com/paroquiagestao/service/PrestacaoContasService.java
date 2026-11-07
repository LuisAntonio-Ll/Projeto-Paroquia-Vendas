package br.com.paroquiagestao.service;

import br.com.paroquiagestao.dto.BalancoFinanceiroDTO;

public interface PrestacaoContasService {
    BalancoFinanceiroDTO gerarBalanco(Long grupoId);
}