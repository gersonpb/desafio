package com.desafio.domain;

import com.desafio.exception.ValidatorException;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.UUID;

@Entity
@Table(name = "tbClientes")
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome", nullable = false, length = 100)
    String nome;

    @Column(name = "cpf", nullable = false, unique = true, length = 14)
    String cpf;

    @Column(name = "idade", nullable = true)
    Integer idade;

    public Cliente() {
    }

    public Cliente(String nome, String cpf, Integer idade) {
        this.nome = nome;
        this.cpf = cpf;
        this.idade = idade;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Integer getIdade() {
        return idade;
    }

    public void setIdade(Integer idade) {
        this.idade = idade;
    }

    public void validar2() {
        if (this.nome.length() < 5 ) {
            throw new ValidatorException("Nome deve conter mais de 5 caracteres!");
        }
        if (idade < 18) {
            throw new ValidatorException("Idade não pode ser menor que 18!");
        }
    }

    public void validar() {
        java.util.List<String> erros = new java.util.ArrayList<>();

        if (this.nome == null || this.nome.trim().isEmpty()) {
            erros.add("Nome é obrigatório.");
        } else if (this.nome.trim().length() < 5) {
            erros.add("Nome deve conter mais de 5 caracteres!");
        }

        if (this.idade == null) {
            erros.add("Idade é obrigatória.");
        } else if (this.idade < 18) {
            erros.add("Idade não pode ser menor que 18!");
        }

        if (!erros.isEmpty()) {
            throw new ValidatorException(String.join(" ; ", erros));
        }
    }

}