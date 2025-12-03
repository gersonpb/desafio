package com.desafio.service;

import com.desafio.controller.request.ClienteRequest;
import com.desafio.controller.response.ClienteResponse;
import com.desafio.domain.Cliente;

import java.util.List;

public interface ClienteService {
    void cadastrar(ClienteRequest clienteRequest);
    ClienteResponse consultarPorId(Long idCliente);
    List<ClienteResponse> consultarClientes();
}
