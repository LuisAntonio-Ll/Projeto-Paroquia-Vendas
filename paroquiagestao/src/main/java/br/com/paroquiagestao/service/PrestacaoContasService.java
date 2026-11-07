package br.com.paroquiagestao.service;

import java.time.LocalDate;

import br.com.paroquiagestao.dto.BalancoAnualDTO;
import br.com.paroquiagestao.dto.BalancoDiarioDTO;
import br.com.paroquiagestao.dto.BalancoFinanceiroDTO;
import br.com.paroquiagestao.dto.BalancoMensalDTO;

public interface PrestacaoContasService {
    BalancoFinanceiroDTO gerarBalanco(Long grupoId);
    BalancoMensalDTO gerarBalancoMensal(Long grupoId, int ano, int mes);
    BalancoAnualDTO gerarBalancoAnual(Long grupoId, int ano);
    BalancoDiarioDTO gerarBalancoDiario(Long grupoId, LocalDate data);
}