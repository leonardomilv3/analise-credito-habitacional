package br.gov.caixa.infrastructure.security;

import io.quarkus.elytron.security.common.BcryptUtil;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PasswordService {

    public String hash(String password) {
        return BcryptUtil.bcryptHash(password);
    }

    public boolean matches(
            String rawPassword,
            String hashedPassword
    ) {
        return BcryptUtil.matches(
                rawPassword,
                hashedPassword
        );
    }
}