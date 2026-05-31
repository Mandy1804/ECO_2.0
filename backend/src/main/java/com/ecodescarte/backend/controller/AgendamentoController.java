package com.ecodescarte.backend.controller;

import com.ecodescarte.backend.model.Agendamento;
import com.ecodescarte.backend.repository.AgendamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller responsável por gerenciar os agendamentos de coleta de resíduos eletrônicos.
 * Expõe endpoints REST para criação e consulta de agendamentos via API.
 */
@RestController
@RequestMapping("/api/agendamentos")
@CrossOrigin(origins = "*", methods = {RequestMethod.POST, RequestMethod.GET, RequestMethod.PUT, RequestMethod.DELETE})
public class AgendamentoController {

    // Repositório injetado via construtor (boas práticas Spring)
    private final AgendamentoRepository agendamentoRepository;

    /**
     * Construtor com injeção de dependência do repositório de agendamentos.
     * @param agendamentoRepository repositório JPA para operações no banco de dados
     */
    @Autowired
    public AgendamentoController(AgendamentoRepository agendamentoRepository) {
        this.agendamentoRepository = agendamentoRepository;
    }

    /**
     * Cria um novo agendamento de coleta de resíduos eletrônicos.
     * Caso o status não seja informado, define automaticamente como "PENDENTE".
     * @param agendamento objeto com os dados do agendamento recebido no corpo da requisição
     * @return agendamento criado com HTTP 201 (Created)
     */
    @PostMapping
    public ResponseEntity<Agendamento> criarAgendamento(@RequestBody Agendamento agendamento) {
        // Define status padrão como PENDENTE se não informado
        if (agendamento.getStatus() == null || agendamento.getStatus().isEmpty()) {
            agendamento.setStatus("PENDENTE");
        }

        // Persiste o agendamento no banco de dados
        Agendamento novoAgendamento = agendamentoRepository.save(agendamento);
        return new ResponseEntity<>(novoAgendamento, HttpStatus.CREATED);
    }
}