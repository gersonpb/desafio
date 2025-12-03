package com.desafio.controller.response;

public record ClienteResponse(
    Long idCliente,
    String nome,
    String cpf,
    Integer idade
) {
}
