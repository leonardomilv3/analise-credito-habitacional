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


        validarDadosCliente(request);
        

        Usuario usuario = new Usuario();

        usuario.setIdUsuario(UUID.randomUUID());
        usuario.setNome(request.nome);
        usuario.setEmail(request.email);
        usuario.setCpf(request.cpf);

        usuario.setSenha(
                passwordService.hash(request.senha)
        );

        usuario.setPerfil(Perfil.CLIENTE);

        usuario.setCriadoEm(LocalDateTime.now());
        usuario.setAtualizadoEm(LocalDateTime.now());

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
                        usuario.getSenha()
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

    private void validarDadosCliente(
                RegistroUsuarioRequest request
        ) throws ValidacaoExcecao {
                validarEmailDuplicado(request.email);
                validarCpfDuplicado(request.cpf);
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