package br.gov.caixa.application.dto;

import jakarta.validation.constraints.NotBlank;

public class CriaPropostaEmprestimoRequest {
    


    @NotBlank(message = "CPF é obrigatório")
    public String cpfCliente;

    @NotBlank(message = "Valor total da propriedade é obrigatório")
    public Double valorTotalPropriedade;

    @NotBlank(message = "Valor de entrada é obrigatório")
    public Double valorEntrada;

    @NotBlank(message = "Valor do empréstimo solicitado é obrigatório")
    public Double valorEmprestimoPropostaSolicitado;

    @NotBlank(message = "Salário mensal é obrigatório")
    public Double salarioMensal;

    @NotBlank(message = "Valor da parcela é obrigatório")
    public Double valorParcela;

    // @Enumerated(EnumType.STRING)
    // public StatusPropostaEmprestimo statusPropostaEmprestimo;

    // public Double analiseScore;

    // public String analiseMotivo;

    // public LocalDateTime criadoEm;

    // public LocalDateTime atualizadoEm; 
}
