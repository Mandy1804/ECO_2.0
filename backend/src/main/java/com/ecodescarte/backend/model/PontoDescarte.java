package com.ecodescarte.backend.model;

import jakarta.persistence.*;
import lombok.Data;

/**
 * Entidade que representa um ponto de descarte de resíduos eletrônicos.
 * Mapeada para a tabela "ponto_descarte" no banco de dados PostgreSQL.
 * Os dados desta entidade são utilizados para exibição no mapa interativo do EcoDescarte.
 * Utiliza Lombok @Data para geração automática de getters, setters e toString.
 */
@Entity
@Table(name = "ponto_descarte")
@Data
public class PontoDescarte {

    // Identificador único gerado automaticamente pelo banco
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Nome do local de descarte (ex: Prefeitura de Maringá, SESI Maringá)
    @Column(nullable = false, length = 150)
    private String nome;

    // Endereço completo do ponto de descarte
    @Column(nullable = false, length = 255)
    private String endereco;

    // Coordenadas geográficas para exibição no mapa via Leaflet
    @Column(name = "latitude")
    private double latitude;

    @Column(name = "longitude")
    private double longitude;

    // Tipos de resíduos aceitos no ponto (ex: Sucatas eletrônicas, Pilhas, Baterias)
    @Column(name = "residuos_aceitos", columnDefinition = "TEXT")
    private String residuosAceitos;
}