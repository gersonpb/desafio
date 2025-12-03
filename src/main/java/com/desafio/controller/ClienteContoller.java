package com.desafio.controller;

import com.desafio.controller.request.ClienteRequest;
import com.desafio.controller.response.ClienteResponse;
import com.desafio.service.ClienteService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping (value = "/cliente")
@RestController
public class ClienteContoller {

    private final ClienteService clienteService;

    public ClienteContoller(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void cadastrar(@RequestBody ClienteRequest request){
        clienteService.cadastrar(request);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ClienteResponse consultarCiente (@RequestParam("idCliente") Long idCliente) {
        return clienteService.consultarPorId(idCliente);
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<ClienteResponse> consultarAllCiente () {
        return clienteService.consultarClientes();
    }
}
