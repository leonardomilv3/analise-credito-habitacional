package br.gov.caixa.domain.entity;

import java.time.LocalDateTime;
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

    public Double valorTotalPropriedade;

    public Double valorEntrada;

    public Double valorEmprestimoPropostaSolicitado;

    public Double salarioMensal;

    public Double valorParcela;

    @Enumerated(EnumType.STRING)
    public StatusPropostaEmprestimo statusPropostaEmprestimo;

    public Double analiseScore;

    public String analiseMotivo;

    public LocalDateTime criadoEm;

    public LocalDateTime atualizadoEm; 

}