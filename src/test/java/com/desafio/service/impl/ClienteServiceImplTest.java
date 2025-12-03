package com.desafio.service.impl;

import com.desafio.controller.request.ClienteRequest;
import com.desafio.controller.response.ClienteResponse;
import com.desafio.domain.Cliente;
import com.desafio.exception.ValidatorException;
import com.desafio.repository.ClienteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClienteServiceImplTest {

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteServiceImpl clienteService;

    @Test
    void cadastrar_deve_salvar_cliente_com_dados_do_request() {
        var req = new ClienteRequest("Joao aa", "12345678900", 25);
        when(clienteRepository.save(any(Cliente.class))).thenAnswer(invocation -> invocation.getArgument(0));

        clienteService.cadastrar(req);

        ArgumentCaptor<Cliente> captor = ArgumentCaptor.forClass(Cliente.class);
        verify(clienteRepository, times(1)).save(captor.capture());
        Cliente saved = captor.getValue();

        assertEquals("Joao aa", saved.getNome());
        assertEquals("12345678900", saved.getCpf());
        assertEquals(25, saved.getIdade());
    }

    @Test
    void consultarPorId_quando_existir_retorna_clienteResponse() {
        Long id = 1L;
        Cliente cliente = mock(Cliente.class);
        when(cliente.getNome()).thenReturn("Ana");
        when(cliente.getCpf()).thenReturn("98765432100");
        when(cliente.getIdade()).thenReturn(30);
        when(clienteRepository.findById(id)).thenReturn(Optional.of(cliente));

        ClienteResponse resp = clienteService.consultarPorId(id);

        assertNotNull(resp);
        assertEquals(id, resp.idCliente());
        assertEquals("Ana", resp.nome());
        assertEquals("98765432100", resp.cpf());
        assertEquals(30, resp.idade());
    }

    @Test
    void consultarPorId_quando_nao_existir_retorna_null() {
        Long id = 2L;
        when(clienteRepository.findById(id)).thenReturn(Optional.empty());

        ClienteResponse resp = clienteService.consultarPorId(id);

        assertNull(resp);
    }

    @Test
    void consultarClientes_retorna_lista_de_clienteResponse() {
        Cliente c1 = mock(Cliente.class);
        when(c1.getId()).thenReturn(1L);
        when(c1.getNome()).thenReturn("A");
        when(c1.getCpf()).thenReturn("111");
        when(c1.getIdade()).thenReturn(20);

        Cliente c2 = mock(Cliente.class);
        when(c2.getId()).thenReturn(2L);
        when(c2.getNome()).thenReturn("B");
        when(c2.getCpf()).thenReturn("222");
        when(c2.getIdade()).thenReturn(25);

        when(clienteRepository.findAll()).thenReturn(List.of(c1, c2));

        List<ClienteResponse> resps = clienteService.consultarClientes();

        assertEquals(2, resps.size());
        assertEquals(1L, resps.get(0).idCliente());
        assertEquals("A", resps.get(0).nome());
        assertEquals("111", resps.get(0).cpf());
        assertEquals(20, resps.get(0).idade());
    }

    // Adicionar ao arquivo `src/test/java/com/desafio/service/impl/ClienteServiceImplTest.java`
    @Test
    void cadastrar_quando_nome_vazio_lanca_excecao_de_validacao() {
        var req = new ClienteRequest("", "12345678900", 25);
        assertThrows(ValidatorException.class, () -> clienteService.cadastrar(req));
        verify(clienteRepository, never()).save(any());
    }

    @Test
    void cadastrar_quando_cpf_invalido_lanca_excecao_de_validacao() {
        var req = new ClienteRequest("Joao", "cpf-invalido", 30);
        assertThrows(ValidatorException.class, () -> clienteService.cadastrar(req));
        verify(clienteRepository, never()).save(any());
    }

    @Test
    void cadastrar_quando_idade_negativa_lanca_excecao_de_validacao() {
        var req = new ClienteRequest("Maria", "12345678900", -5);
        assertThrows(ValidatorException.class, () -> clienteService.cadastrar(req));
        verify(clienteRepository, never()).save(any());
    }

    // java
    @Test
    void cadastrar_quando_nome_vazio_valida_mensagem_da_validacao() {
        var req = new ClienteRequest("", "12345678900", 25);
        var expectedErro = "Nome é obrigatório.";
        ValidatorException ex = assertThrows(ValidatorException.class, () -> clienteService.cadastrar(req));
        assertEquals(expectedErro, ex.getMessage());
        verify(clienteRepository, never()).save(any());
    }

    @Test
    void cadastrar_quando_nome_menor_5_valida_mensagem_da_validacao() {
        var req = new ClienteRequest("Ana", "12345678900", 25);
        var expectedErro = "Nome deve conter mais de 5 caracteres!";

        ValidatorException ex = assertThrows(ValidatorException.class, () -> clienteService.cadastrar(req));
        assertEquals(expectedErro, ex.getMessage());
        verify(clienteRepository, never()).save(any());
    }

    @Test
    void cadastrar_quando_idade_nao_informada_valida_mensagem_da_validacao() {
        var req = new ClienteRequest("Ana Maria", "12345678900", null);
        var expectedErro = "Idade é obrigatória.";

        ValidatorException ex = assertThrows(ValidatorException.class, () -> clienteService.cadastrar(req));
        assertEquals(expectedErro, ex.getMessage());
        verify(clienteRepository, never()).save(any());
    }

    @Test
    void cadastrar_quando_idade_menor_18_valida_mensagem_da_validacao() {
        var req = new ClienteRequest("Ana Maria", "12345678900", 14);
        var expectedErro = "Idade não pode ser menor que 18!";

        ValidatorException ex = assertThrows(ValidatorException.class, () -> clienteService.cadastrar(req));
        assertEquals(expectedErro, ex.getMessage());
        verify(clienteRepository, never()).save(any());
    }
}
