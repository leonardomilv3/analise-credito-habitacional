package br.gov.caixa.infrastructure.exception;

import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class GlobalMapeadorExcecao
        implements ExceptionMapper<Exception> {

    @Context
    UriInfo uriInfo;

    @Override
    public Response toResponse(Exception exception) {

        if (exception instanceof ValidacaoExcecao ex) {

            return buildResponse(
                    Response.Status.BAD_REQUEST,
                    "Validation Error",
                    ex.getMessage()
            );
        }

        if (exception instanceof NaoEncontradoExcecao ex) {

            return buildResponse(
                    Response.Status.NOT_FOUND,
                    "Not Found",
                    ex.getMessage()
            );
        }

        if (exception instanceof NaoAutorizaoExcecao ex) {

            return buildResponse(
                    Response.Status.UNAUTHORIZED,
                    "Unauthorized",
                    ex.getMessage()
            );
        }

        if (exception instanceof RegraNegocioExcecao ex) {

            return buildResponse(
                    Response.Status.BAD_REQUEST,
                    "Business Error",
                    ex.getMessage()
            );
        }

        return buildResponse(
                Response.Status.INTERNAL_SERVER_ERROR,
                "Internal Server Error",
                exception.getMessage()
        );
    }

    private Response buildResponse(
            Response.Status status,
            String error,
            String message
    ) {

        ApiRespostaExcecao response =
                new ApiRespostaExcecao(
                        status.getStatusCode(),
                        error,
                        message,
                        uriInfo.getPath()
                );

        return Response
                .status(status)
                .entity(response)
                .build();
    }
}