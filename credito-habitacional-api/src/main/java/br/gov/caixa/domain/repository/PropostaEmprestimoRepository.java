package br.gov.caixa.domain.repository;

import br.gov.caixa.domain.entity.PropostaEmprestimo;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

public class PropostaEmprestimoRepository 
        implements PanacheRepository<PropostaEmprestimo>
        {

            public void cadastrarProposta(PropostaEmprestimo proposta) {
                persist(proposta);
            }


    
}
