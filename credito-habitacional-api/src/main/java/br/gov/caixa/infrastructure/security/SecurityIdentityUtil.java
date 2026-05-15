package br.gov.caixa.infrastructure.security;

import io.quarkus.security.identity.SecurityIdentity;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

@RequestScoped
public class SecurityIdentityUtil {

    @Inject
    SecurityIdentity securityIdentity;

    public String getUserId() {

        return securityIdentity
                .getPrincipal()
                .getName();
    }

    public boolean hasRole(String role) {
        return securityIdentity.hasRole(role);
    }
}

// RegistroUsuarioRequisicao