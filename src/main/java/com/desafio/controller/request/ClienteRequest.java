package com.desafio.controller.request;

public record ClienteRequest(
        String nome,
        String cpf,
        Integer idade
) {
}
