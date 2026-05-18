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

    private String nome;

    @OneToMany(mappedBy = "usuario")
    private List<PropostaEmprestimo> propostas;

    @Column(unique = true)
    private String cpf;

    @Column(unique = true)
    private String email;

    private String senha;

    @Enumerated(EnumType.STRING)
    private Perfil perfil;

    private LocalDateTime criadoEm;

    private LocalDateTime atualizadoEm;


    

    public UUID getIdUsuario() {
        return this.id_usuario;
    }

    public void setIdUsuario(UUID id) {
        this.id_usuario = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<PropostaEmprestimo> getPropostas() {
        return propostas;
    }

    public void setPropostas(List<PropostaEmprestimo> propostas) {
        this.propostas = propostas;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
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
