package br.gov.caixa.domain.repository;

import br.gov.caixa.domain.entity.PropostaEmprestimoHistorico;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

public class PropostaEmprestimoHistoricorRepository 
    implements PanacheRepository<PropostaEmprestimoHistorico> 
    {
    

        public void cadastrarHistoricoPropostaEmprestimo(PropostaEmprestimoHistorico historico) {
            persist(historico);
        }



}
