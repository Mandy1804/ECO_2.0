package com.ecodescarte.backend.controller;

import com.ecodescarte.backend.model.Agendamento;
import com.ecodescarte.backend.repository.AgendamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * Controller responsável por gerenciar os agendamentos de coleta de resíduos eletrônicos.
 * Expõe endpoints REST para criação e consulta de agendamentos via API.
 */
@RestController
@RequestMapping("/api/agendamentos")
@CrossOrigin(origins = "*", methods = {RequestMethod.POST, RequestMethod.GET, RequestMethod.PUT, RequestMethod.DELETE})
public class AgendamentoController {

    private final AgendamentoRepository agendamentoRepository;

    @Autowired
    public AgendamentoController(AgendamentoRepository agendamentoRepository) {
        this.agendamentoRepository = agendamentoRepository;
    }

    @PostMapping
    public ResponseEntity<Agendamento> criarAgendamento(@RequestBody Agendamento agendamento) {

        if (agendamento.getStatus() == null || agendamento.getStatus().isEmpty()) {
            agendamento.setStatus("PENDENTE");
        }

        Agendamento novoAgendamento = agendamentoRepository.save(agendamento);
        return new ResponseEntity<>(novoAgendamento, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Agendamento>> listarAgendamentos() {
        return ResponseEntity.ok(agendamentoRepository.findAll());
    }
}