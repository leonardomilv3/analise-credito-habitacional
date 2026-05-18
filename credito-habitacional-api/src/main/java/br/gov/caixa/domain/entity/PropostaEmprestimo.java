package br.gov.caixa.domain.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import br.gov.caixa.domain.enumerate.StatusPropostaEmprestimo;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;


@Schema(description = "Entidade que representa uma proposta de empréstimo habitacional, contendo informações sobre o valor da propriedade, entrada, empréstimo solicitado, salário mensal, valor da parcela, status da proposta e histórico de alterações.")
@Entity
@Table(
    name = "proposta_emprestimo"
)
public class PropostaEmprestimo extends PanacheEntityBase {

    @Id
    private UUID id_proposta_emprestimo;

    @ManyToOne
    @JoinColumn(
        name = "id_usuario", 
        nullable = false,
        foreignKey = @ForeignKey(name = "fk_Usuario_idUsuario")
    )
    private Usuario usuario;

    @OneToMany(mappedBy = "propostaEmprestimo")
    private List<PropostaEmprestimoHistorico> historicoPropostaEmprestimo;

    private Double valorTotalPropriedade;

    private Double valorEntrada;

    private Double valorEmprestimoPropostaSolicitado;

    private Double salarioMensal;

    private Double valorParcela;

    @Enumerated(EnumType.STRING)
    private StatusPropostaEmprestimo statusPropostaEmprestimo;

    private Double analiseScore;

    private String analiseMotivo;

    private LocalDateTime criadoEm;

    private LocalDateTime atualizadoEm;



    

    public UUID getIdPropostaEmprestimo() {
        return id_proposta_emprestimo;
    }

    public void setIdPropostaEmprestimo() {
        this.id_proposta_emprestimo = UUID.randomUUID();
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<PropostaEmprestimoHistorico> getHistoricoPropostaEmprestimo() {
        return historicoPropostaEmprestimo;
    }

    public void updateHistoricoPropostaEmprestimo(PropostaEmprestimoHistorico novoHistoricoPropostaEmprestimo) {
        if (this.historicoPropostaEmprestimo == null) {
            this.historicoPropostaEmprestimo = new ArrayList<>();
        }
        historicoPropostaEmprestimo.add(novoHistoricoPropostaEmprestimo);
    }

    public Double getValorTotalPropriedade() {
        return valorTotalPropriedade;
    }

    public void setValorTotalPropriedade(Double valorTotalPropriedade) {
        this.valorTotalPropriedade = valorTotalPropriedade;
    }

    public Double getValorEntrada() {
        return valorEntrada;
    }

    public void setValorEntrada(Double valorEntrada) {
        this.valorEntrada = valorEntrada;
    }

    public Double getValorEmprestimoPropostaSolicitado() {
        return valorEmprestimoPropostaSolicitado;
    }

    public void setValorEmprestimoPropostaSolicitado(Double valorEmprestimoPropostaSolicitado) {
        this.valorEmprestimoPropostaSolicitado = valorEmprestimoPropostaSolicitado;
    }

    public Double getSalarioMensal() {
        return salarioMensal;
    }

    public void setSalarioMensal(Double salarioMensal) {
        this.salarioMensal = salarioMensal;
    }

    public Double getValorParcela() {
        return valorParcela;
    }

    public void setValorParcela(Double valorParcela) {
        this.valorParcela = valorParcela;
    }

    public StatusPropostaEmprestimo getStatusPropostaEmprestimo() {
        return statusPropostaEmprestimo;
    }

    public void setStatusPropostaEmprestimo(StatusPropostaEmprestimo status) {
        this.statusPropostaEmprestimo = status;
    }

    public Double getAnaliseScore() {
        return analiseScore;
    }

    public void setAnaliseScore(Double analiseScore) {
        this.analiseScore = analiseScore;
    }

    public String getAnaliseMotivo() {
        return analiseMotivo;
    }

    public void setAnaliseMotivo(String analiseMotivo) {
        this.analiseMotivo = analiseMotivo;
    }

    public LocalDateTime getCriadoEm() {
        return criadoEm;
    }

    public void setCriadoEm(LocalDateTime criadoEm) {
        this.criadoEm = criadoEm;
    }

    public LocalDateTime getAtualizadoEm() {
        return atualizadoEm;
    }

    public void setAtualizadoEm(LocalDateTime atualizadoEm) {
        this.atualizadoEm = atualizadoEm;
    }

}