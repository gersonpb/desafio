package com.desafio.service.impl;

import com.desafio.controller.request.ClienteRequest;
import com.desafio.controller.response.ClienteResponse;
import com.desafio.domain.Cliente;
import com.desafio.repository.ClienteRepository;
import com.desafio.service.ClienteService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteServiceImpl implements ClienteService {
    private final ClienteRepository clienteRepository;

    public ClienteServiceImpl(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Override
    public void cadastrar(ClienteRequest clienteRequest) {


        Cliente cliente = new Cliente(
                clienteRequest.nome(),
                clienteRequest.cpf(),
                clienteRequest.idade());
        cliente.validar();

        clienteRepository.save(cliente);
    }

    @Override
    public ClienteResponse consultarPorId(Long idCliente) {
        Optional<Cliente> cli = clienteRepository.findById(idCliente);
        if (cli.isPresent()) {
            return  new ClienteResponse(idCliente,
                    cli.get().getNome(),
                    cli.get().getCpf(),
                    cli.get().getIdade());
        }
        return null;
    }

    @Override
    public List<ClienteResponse> consultarClientes() {

        List<Cliente> clientes = clienteRepository.findAll();

        return clientes.stream()
                .map(c -> new ClienteResponse(
                    c.getId(),
                    c.getNome(),
                    c.getCpf(),
                    c.getIdade()))
                .collect(java.util.stream.Collectors.toList());
    }
}
