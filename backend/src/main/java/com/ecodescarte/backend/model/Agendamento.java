package com.ecodescarte.backend.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

/**
 * Entidade que representa um agendamento de coleta de resíduos eletrônicos.
 * Mapeada para a tabela "agendamento" no banco de dados PostgreSQL.
 * Utiliza Lombok @Data para geração automática de getters, setters e toString.
 */
@Entity
@Table(name = "agendamento")
@Data
public class Agendamento {

    // Identificador único gerado automaticamente pelo banco
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Dados do solicitante
    @Column(nullable = false, length = 100)
    private String nome;

    @Column(nullable = false, length = 100)
    private String email;

    @Column(length = 20)
    private String telefone;

    @Column(nullable = false, length = 255)
    private String endereco;

    // Dados do agendamento
    @Column(nullable = false, length = 100)
    private String tipoResiduo; // Ex: Celular, Computador, Bateria

    @Column(nullable = false)
    private LocalDate dataColeta; // Data desejada para a coleta

    @Column(columnDefinition = "TEXT")
    private String observacoes; // Informações adicionais do solicitante

    // Campo para gestão interna do status da coleta
    // Valores possíveis: PENDENTE, CONFIRMADO, CONCLUIDO, CANCELADO
    private String status;
}