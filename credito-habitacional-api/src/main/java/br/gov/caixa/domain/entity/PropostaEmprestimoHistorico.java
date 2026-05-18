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

    private StatusPropostaEmprestimo statusAnterior;

    private StatusPropostaEmprestimo statusAtual;

    private LocalDateTime criadoEm;



    public String getId_proposta_emprestimo_historico() {
        return id_proposta_emprestimo_historico;
    }

    public void setId_proposta_emprestimo_historico(String id_proposta_emprestimo_historico) {
        this.id_proposta_emprestimo_historico = id_proposta_emprestimo_historico;
    }

    public PropostaEmprestimo getPropostaEmprestimo() {
        return propostaEmprestimo;
    }

    public void setPropostaEmprestimo(PropostaEmprestimo propostaEmprestimo) {
        this.propostaEmprestimo = propostaEmprestimo;
    }


    public StatusPropostaEmprestimo getStatusAnterior() {
        return statusAnterior;
    }

    public void setStatusAnterior(StatusPropostaEmprestimo statusAnterior) {
        this.statusAnterior = statusAnterior;
    }

    public StatusPropostaEmprestimo getStatusAtual() {
        return statusAtual;
    }

    public void setStatusAtual(StatusPropostaEmprestimo statusAtual) {
        this.statusAtual = statusAtual;
    }

    public LocalDateTime getCriadoEm() {
        return criadoEm;
    }

    public void setCriadoEm(LocalDateTime criadoEm) {
        this.criadoEm = criadoEm;
    }

}