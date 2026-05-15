package br.gov.caixa.application.service;

import br.gov.caixa.application.dto.*;
import br.gov.caixa.domain.entity.Usuario;
import br.gov.caixa.domain.enumerate.Perfil;
import br.gov.caixa.domain.repository.UsuarioRepository;
import br.gov.caixa.infrastructure.exception.NaoAutorizaoExcecao;
import br.gov.caixa.infrastructure.exception.ValidacaoExcecao;
import br.gov.caixa.infrastructure.security.JwtService;
import br.gov.caixa.infrastructure.security.PasswordService;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@ApplicationScoped
public class AuthService {

    @Inject
    UsuarioRepository usuarioRepository;

    @Inject
    PasswordService passwordService;

    @Inject
    JwtService jwtService;

    @Transactional
    public RegistroUsuarioResponse register(
            RegistroUsuarioRequest request
    ) throws ValidacaoExcecao {

        validarEmailDuplicado(request.email);

        validarCpfDuplicado(request.cpf);

        Usuario usuario = new Usuario();

        usuario.id_usuario = UUID.randomUUID();
        usuario.nome = request.nome;
        usuario.email = request.email;
        usuario.cpf = request.cpf;

        usuario.senha =
                passwordService.hash(request.senha);

        usuario.perfil = Perfil.CLIENTE;

        usuario.criadoEm = LocalDateTime.now();
        usuario.atualizadoEm = LocalDateTime.now();

        usuarioRepository.cadastrarUsuario(usuario);

        return new RegistroUsuarioResponse(
                "Usuário " + request.nome + " registrado com sucesso!"
        );
    }

    public LoginResponse login(LoginRequest request) 
            throws NaoAutorizaoExcecao {

        Usuario usuario =
                usuarioRepository.buscarPorEmail(
                        request.email
                );

        if (usuario == null) {
            throw new NaoAutorizaoExcecao(
                    "Credenciais inválidas"
            );
        }

        boolean senhaValida =
                passwordService.matches(
                        request.senha,
                        usuario.senha
                );

        if (!senhaValida) {
            throw new NaoAutorizaoExcecao(
                    "Credenciais inválidas"
            );
        }

        String token =
                jwtService.generateToken(usuario);

        return new LoginResponse(token);
    }

    private void validarEmailDuplicado(
            String email
    ) throws ValidacaoExcecao {

        Usuario usuario =
                usuarioRepository.buscarPorEmail(email);

        if (usuario != null) {
            throw new ValidacaoExcecao(
                    "Email já cadastrado"
            );
        }
    }

    private void validarCpfDuplicado(
            String cpf
    ) throws ValidacaoExcecao {

        Usuario usuario =
                usuarioRepository.buscarPorCpf(cpf);

        if (usuario != null) {
            throw new ValidacaoExcecao(
                    "CPF já cadastrado"
            );
        }
    }
}