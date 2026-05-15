package br.gov.caixa.domain.repository;

import br.gov.caixa.domain.entity.Usuario;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;


@ApplicationScoped
public class UsuarioRepository
        implements PanacheRepository<Usuario> 
        {

            public void cadastrarUsuario(Usuario usuario) {
                persist(usuario);
            }
            
    
            public Usuario buscarPorEmail(String email) {

                return find("email", email)
                        .firstResult();
            }

            public Usuario buscarPorCpf(String cpf) {

                return find("cpf", cpf)
                        .firstResult();
            }
    }
