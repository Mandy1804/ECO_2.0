package com.ecodescarte.backend.controller;

import com.ecodescarte.backend.model.PontoDescarte;
import com.ecodescarte.backend.repository.PontoDescarteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller responsável por gerenciar os pontos de descarte de resíduos eletrônicos.
 * Expõe endpoints REST para listagem, busca e cadastro de pontos via API.
 */
@RestController
@RequestMapping("/api/pontos-descarte")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST})
public class PontoDescarteController {

    // Repositório injetado via construtor (boas práticas Spring)
    private final PontoDescarteRepository pontoDescarteRepository;

    /**
     * Construtor com injeção de dependência do repositório de pontos de descarte.
     * @param pontoDescarteRepository repositório JPA para operações no banco de dados
     */
    @Autowired
    public PontoDescarteController(PontoDescarteRepository pontoDescarteRepository) {
        this.pontoDescarteRepository = pontoDescarteRepository;
    }

    /**
     * Lista todos os pontos de descarte cadastrados no sistema.
     * @return lista de pontos de descarte com HTTP 200 (OK)
     */
    @GetMapping
    public ResponseEntity<List<PontoDescarte>> listarTodos() {
        List<PontoDescarte> pontos = pontoDescarteRepository.findAll();
        return new ResponseEntity<>(pontos, HttpStatus.OK);
    }

    /**
     * Busca um ponto de descarte específico pelo seu ID.
     * @param id identificador único do ponto de descarte
     * @return ponto encontrado com HTTP 200, ou HTTP 404 se não existir
     */
    @GetMapping("/{id}")
    public ResponseEntity<PontoDescarte> buscarPorId(@PathVariable Long id) {
        return pontoDescarteRepository.findById(id)
                .map(ponto -> new ResponseEntity<>(ponto, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Cadastra um novo ponto de descarte no sistema.
     * Útil para administração e expansão da rede de coleta.
     * @param ponto objeto com os dados do ponto recebido no corpo da requisição
     * @return ponto cadastrado com HTTP 201 (Created)
     */
    @PostMapping
    public ResponseEntity<PontoDescarte> cadastrarPonto(@RequestBody PontoDescarte ponto) {
        PontoDescarte novoPonto = pontoDescarteRepository.save(ponto);
        return new ResponseEntity<>(novoPonto, HttpStatus.CREATED);
    }
}