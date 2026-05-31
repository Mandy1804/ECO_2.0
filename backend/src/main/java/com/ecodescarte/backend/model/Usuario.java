package com.ecodescarte.backend.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

/**
 * Entidade que representa um usuário cadastrado no sistema EcoDescarte.
 * Mapeada para a tabela "usuario" no banco de dados PostgreSQL.
 * Utiliza Lombok @Data para geração automática de getters, setters e toString.
 */
@Entity
@Table(name = "usuario")
@Data
public class Usuario {

    // Identificador único gerado automaticamente pelo banco
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Nome completo do usuário
    @Column(nullable = false, length = 100)
    private String nome;

    // Email único utilizado para login e identificação do usuário
    @Column(nullable = false, unique = true, length = 100)
    private String email;

    // Senha de acesso do usuário
    @Column(nullable = false)
    private String senha;

    // Data de cadastro do usuário no sistema
    private LocalDate dataCadastro;

    // Telefone de contato do usuário
    @Column(length = 20)
    private String telefone;

    // Endereço principal do usuário para coleta de resíduos
    @Column(nullable = false, length = 255)
    private String endereco;

    // Tipo de material eletrônico de interesse do usuário
    // Ex: Celular, Computador, Eletrodoméstico
    private String tipoMaterialInteresse;
}