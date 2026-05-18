package br.gov.caixa.application.resource;

import br.gov.caixa.application.dto.LoginResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import br.gov.caixa.application.dto.LoginRequest;
import br.gov.caixa.application.dto.RegistroUsuarioRequest;
import br.gov.caixa.application.dto.RegistroUsuarioResponse;
import br.gov.caixa.application.service.AuthService;
import br.gov.caixa.infrastructure.exception.NaoAutorizaoExcecao;
import br.gov.caixa.infrastructure.exception.ValidacaoExcecao;
import br.gov.caixa.infrastructure.security.JwtService;
import br.gov.caixa.infrastructure.security.PasswordService;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

@Path("/auth")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AuthResource {

    @Inject
    PasswordService passwordService;

    @Inject
    AuthService authService;

    @Inject
    JwtService jwtService;

    @APIResponse
    @POST
    @Path("/register")
    public RegistroUsuarioResponse register(
        @Valid RegistroUsuarioRequest request
        ) throws ValidacaoExcecao {
        return authService.register(request);
    }

    @APIResponse
    @POST
    @Path("/login")
    public LoginResponse login(
        @Valid LoginRequest request
        ) throws NaoAutorizaoExcecao {
        return authService.login(request);
    }
}
