package com.ecodescarte.backend.controller;

import com.ecodescarte.backend.model.PontoDescarte;
import com.ecodescarte.backend.repository.PontoDescarteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pontos-descarte")
@CrossOrigin(origins = "*", methods = {
        RequestMethod.GET,
        RequestMethod.POST,
        RequestMethod.PUT,
        RequestMethod.DELETE
})
public class PontoDescarteController {

    private final PontoDescarteRepository pontoDescarteRepository;

    @Autowired
    public PontoDescarteController(PontoDescarteRepository pontoDescarteRepository) {
        this.pontoDescarteRepository = pontoDescarteRepository;
    }

    // Listar todos os pontos
    @GetMapping
    public ResponseEntity<List<PontoDescarte>> listarTodos() {
        return ResponseEntity.ok(pontoDescarteRepository.findAll());
    }

    // Buscar ponto por ID
    @GetMapping("/{id}")
    public ResponseEntity<PontoDescarte> buscarPorId(@PathVariable Long id) {

        return pontoDescarteRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Cadastrar ponto
    @PostMapping
    public ResponseEntity<PontoDescarte> cadastrarPonto(
            @RequestBody PontoDescarte ponto) {

        PontoDescarte novoPonto = pontoDescarteRepository.save(ponto);

        return new ResponseEntity<>(
                novoPonto,
                HttpStatus.CREATED
        );
    }

    // Atualizar ponto
    @PutMapping("/{id}")
    public ResponseEntity<PontoDescarte> atualizarPonto(
            @PathVariable Long id,
            @RequestBody PontoDescarte dados) {

        return pontoDescarteRepository.findById(id)
                .map(ponto -> {

                    ponto.setNome(dados.getNome());
                    ponto.setEndereco(dados.getEndereco());
                    ponto.setLatitude(dados.getLatitude());
                    ponto.setLongitude(dados.getLongitude());
                    ponto.setResiduosAceitos(dados.getResiduosAceitos());

                    return ResponseEntity.ok(
                            pontoDescarteRepository.save(ponto)
                    );
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Excluir ponto
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirPonto(@PathVariable Long id) {

        if (!pontoDescarteRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        pontoDescarteRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}