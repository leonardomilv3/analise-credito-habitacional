package br.gov.caixa.application.service;

import br.gov.caixa.application.dto.LoginRequest;
import br.gov.caixa.application.dto.LoginResponse;
// import br.gov.caixa.application.dto.AuthResponse;
// import br.gov.caixa.application.dto.LoginRequest;
import br.gov.caixa.application.dto.RegistroUsuarioRequest;
import br.gov.caixa.domain.entity.Usuario;
import br.gov.caixa.domain.repository.UsuarioRepository;
import br.gov.caixa.infrastructure.exception.NaoAutorizaoExcecao;
import br.gov.caixa.infrastructure.exception.ValidacaoExcecao;
import br.gov.caixa.infrastructure.security.JwtService;
import br.gov.caixa.infrastructure.security.PasswordService;

import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;

import jakarta.inject.Inject;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@QuarkusTest
public class AuthServiceTest {

    @Inject
    AuthService authService;

    @InjectMock
    UsuarioRepository usuarioRepository;

    @InjectMock
    PasswordService passwordService;

    @InjectMock
    JwtService jwtService;

    @Test
    void deveRegistrarUsuario() {

        RegistroUsuarioRequest request =
                new RegistroUsuarioRequest();

        request.nome = "Leonardo";
        request.email = "teste@caixa.gov.br";
        request.cpf = "05782898143";
        request.senha = "123456";

        when(usuarioRepository.buscarPorEmail(
                request.email
        )).thenReturn(null);

        when(usuarioRepository.buscarPorCpf(
                request.cpf
        )).thenReturn(null);

        when(passwordService.hash(
                request.senha
        )).thenReturn("HASH");

        var response =
                authService.register(request);

        assertNotNull(response);

        verify(usuarioRepository)
                .cadastrarUsuario(any(Usuario.class));
    }


    @Test
    void deveFalharQuandoEmailExistir() {

        RegistroUsuarioRequest request =
                new RegistroUsuarioRequest();

        request.email = "teste@caixa.gov.br";

        Usuario usuario = new Usuario();

        when(usuarioRepository.buscarPorEmail(
                request.email
        )).thenReturn(usuario);

        assertThrows(
                ValidacaoExcecao.class,
                () -> authService.register(request)
        );
    }


    @Test
    void deveFazerLogin() {

        LoginRequest request =
                new LoginRequest();

        request.email = "teste@caixa.gov.br";
        request.senha = "123456";

        Usuario usuario = new Usuario();

        usuario.email = request.email;
        usuario.senha = "HASH";

        when(usuarioRepository.buscarPorEmail(
                request.email
        )).thenReturn(usuario);

        when(passwordService.matches(
                request.senha,
                usuario.senha
        )).thenReturn(true);

        when(jwtService.generateToken(
                usuario
        )).thenReturn("TOKEN");

        LoginResponse response =
                authService.login(request);

        assertNotNull(response);

        assertEquals(
                "TOKEN",
                response.token
        );
    }


    @Test
    void deveFalharQuandoSenhaForInvalida() {

        LoginRequest request =
                new LoginRequest();

        request.email = "teste@caixa.gov.br";
        request.senha = "senhaErrada";

        Usuario usuario = new Usuario();

        usuario.email = request.email;
        usuario.senha = "HASH";

        when(usuarioRepository.buscarPorEmail(
                request.email
        )).thenReturn(usuario);

        when(passwordService.matches(
                request.senha,
                usuario.senha
        )).thenReturn(false);

        assertThrows(
                NaoAutorizaoExcecao.class,
                () -> authService.login(request)
        );
    }

}