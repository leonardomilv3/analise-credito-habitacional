package br.gov.caixa.domain.entity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import br.gov.caixa.domain.enumerate.Perfil;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;


@Schema(description = "Entidade que representa um usuário do sistema, contendo informações pessoais e credenciais de acesso.")
@Entity
@Table(
    name = "usuario",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uk_usuario_cpf",
            columnNames = "cpf"
        ),
        @UniqueConstraint(
            name = "uk_usuario_email",
            columnNames = "email"
        )
    }
)
public class Usuario extends PanacheEntityBase {

    @Id
    private UUID id_usuario;

    public String nome;

    @OneToMany(mappedBy = "usuario")
    private List<PropostaEmprestimo> propostas;

    @Column(unique = true)
    public String cpf;

    @Column(unique = true)
    public String email;

    public String senha;

    @Enumerated(EnumType.STRING)
    public Perfil papel;

    public LocalDateTime criadoEm;

    public LocalDateTime atualizadoEm; 
}
