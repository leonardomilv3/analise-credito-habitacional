package br.gov.caixa.infrastructure.security;

import br.gov.caixa.domain.entity.Usuario;
import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;

import java.time.Duration;
import java.util.Set;

@ApplicationScoped
public class JwtService {

    public String generateToken(Usuario usuario) {

        return Jwt
                .issuer("caixa-api")
                .subject(usuario.getIdUsuario().toString())
                .groups(
                    Set.of(
                        usuario.getPerfil().name()
                    )
                )
                .expiresIn(Duration.ofHours(2))
                .sign();
    }
}