package br.gov.caixa.application.service;

import java.time.LocalDateTime;
import br.gov.caixa.domain.entity.PropostaEmprestimoHistorico;
import br.gov.caixa.domain.enumerate.StatusPropostaEmprestimo;
import br.gov.caixa.domain.repository.PropostaEmprestimoHistoricorRepository;
import jakarta.inject.Inject;

public class PropostaEmprestimoHistoricoService {


    @Inject
    PropostaEmprestimoHistoricorRepository historicoPropostaEmprestimoRepository;
    

    public PropostaEmprestimoHistorico criarHistorico() {
        PropostaEmprestimoHistorico historico = new PropostaEmprestimoHistorico();
        historico.setStatusAnterior(StatusPropostaEmprestimo.PENDENTE);
        historico.setStatusAtual(StatusPropostaEmprestimo.EM_ANALISE);
        historico.setCriadoEm(LocalDateTime.now());

        historicoPropostaEmprestimoRepository.cadastrarHistoricoPropostaEmprestimo(historico);
        return historico;
    }


}
