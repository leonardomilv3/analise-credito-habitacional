package br.gov.caixa.application.service;

import br.gov.caixa.application.dto.CriaPropostaEmprestimoRequest;
import br.gov.caixa.application.dto.CriaPropostaEmprestimoResponse;
import br.gov.caixa.domain.entity.PropostaEmprestimo;
import br.gov.caixa.domain.entity.PropostaEmprestimoHistorico;
import br.gov.caixa.domain.entity.Usuario;
import br.gov.caixa.domain.repository.PropostaEmprestimoRepository;
import br.gov.caixa.domain.repository.UsuarioRepository;
import br.gov.caixa.infrastructure.exception.ValidacaoExcecao;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class PropostaEmprestimoService {

    @Inject
    PropostaEmprestimoRepository propostaEmprestimoRepository;  

    @Inject
    UsuarioRepository usuarioRepository;

    @Inject
    PropostaEmprestimoHistoricoService propostaEmprestimoHistoricoService;

    
    @Transactional
    public CriaPropostaEmprestimoResponse cadastrarPropostaEmprestimo(CriaPropostaEmprestimoRequest request) {
        // Lógica para cadastrar proposta de empréstimo


        // validar dados da request proposta.
        // valida cpf do cliente, 
        // valor total da propriedade, 
        // valor de entrada, 
        // valor do empréstimo solicitado, 
        // salário mensal e 
        // valor da parcela.
        validarDadosPropostaEmprestimo(request);

        PropostaEmprestimo propostaEmprestimo = new PropostaEmprestimo();

        propostaEmprestimo.setIdPropostaEmprestimo();
        propostaEmprestimo.setValorTotalPropriedade(request.valorTotalPropriedade);
        propostaEmprestimo.setValorEntrada(request.valorEntrada);
        propostaEmprestimo.setValorEmprestimoPropostaSolicitado(request.valorEmprestimoPropostaSolicitado);
        propostaEmprestimo.setSalarioMensal(request.salarioMensal);
        propostaEmprestimo.setValorParcela(request.valorParcela);
        
        

        Usuario usuario = usuarioRepository.buscarPorCpf(request.cpfCliente);
        propostaEmprestimo.setUsuario(usuario);


        PropostaEmprestimoHistorico propostaEmprestimoHistorico = propostaEmprestimoHistoricoService.criarHistorico();
        propostaEmprestimo.updateHistoricoPropostaEmprestimo(propostaEmprestimoHistorico);


        propostaEmprestimoRepository.cadastrarProposta(propostaEmprestimo);       

        
        return new CriaPropostaEmprestimoResponse("Proposta de empréstimo cadastrada com sucesso!");

    }

    private void validarDadosPropostaEmprestimo(CriaPropostaEmprestimoRequest request) 
        throws ValidacaoExcecao {
        // Implementar validação dos dados da proposta de empréstimo
        // Exemplo: validar se o valor total da propriedade é maior que zero, etc.
        validarCpfExiste(request.cpfCliente);
    }

    private void validarCpfExiste(String cpf) 
        throws ValidacaoExcecao {
                Usuario usuario =
                usuarioRepository.buscarPorCpf(cpf);

                if (usuario == null) {
                        throw new ValidacaoExcecao(
                                "CPF não encontrado"
                        );
                }
        }




}
