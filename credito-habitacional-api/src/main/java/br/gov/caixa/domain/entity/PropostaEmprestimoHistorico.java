package br.gov.caixa.domain.entity;

import java.time.LocalDateTime;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import br.gov.caixa.domain.enumerate.StatusPropostaEmprestimo;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.ForeignKey;


@Schema(description = "Entidade que representa o histórico de alterações de uma proposta de empréstimo, contendo informações sobre o status anterior, status atual e data da alteração.")
@Entity
@Table(name = "proposta_emprestimo_historico")
public class PropostaEmprestimoHistorico {
    

    @Id
    private String id_proposta_emprestimo_historico;


    @ManyToOne
    @JoinColumn(
        name = "id_proposta_emprestimo", 
        nullable = false,
        foreignKey = @ForeignKey(name = "fk_id_proposta_emprestimo")
    )
    private PropostaEmprestimo propostaEmprestimo;

    public StatusPropostaEmprestimo statusAnterior;

    public StatusPropostaEmprestimo statusAtual;

    public LocalDateTime criadoEm;

}


