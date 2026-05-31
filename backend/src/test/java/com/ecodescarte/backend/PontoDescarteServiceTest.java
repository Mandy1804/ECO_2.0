package com.ecodescarte.backend;

import com.ecodescarte.backend.controller.PontoDescarteController;
import com.ecodescarte.backend.model.PontoDescarte;
import com.ecodescarte.backend.repository.PontoDescarteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes do PontoDescarteController")
class PontoDescarteServiceTest {

    @Mock
    private PontoDescarteRepository pontoDescarteRepository;

    @InjectMocks
    private PontoDescarteController pontoDescarteController;

    private PontoDescarte ponto;

    @BeforeEach
    void setUp() {
        ponto = new PontoDescarte();
        ponto.setId(1L);
        ponto.setNome("Prefeitura de Maringá");
        ponto.setEndereco("Avenida XV de Novembro, 701, Maringá");
        ponto.setLatitude(-23.42385);
        ponto.setLongitude(-51.93946);
        ponto.setResiduosAceitos("Sucatas eletrônicas");
    }

    @Test
    @DisplayName("Deve listar todos os pontos de descarte")
    void deveListarTodosOsPontos() {
        // Arrange
        List<PontoDescarte> pontos = Arrays.asList(ponto);
        when(pontoDescarteRepository.findAll()).thenReturn(pontos);

        // Act
        ResponseEntity<List<PontoDescarte>> response = pontoDescarteController.listarTodos();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals("Prefeitura de Maringá", response.getBody().get(0).getNome());
    }

    @Test
    @DisplayName("Deve buscar ponto por ID com sucesso")
    void deveBuscarPontoPorId() {
        // Arrange
        when(pontoDescarteRepository.findById(1L)).thenReturn(Optional.of(ponto));

        // Act
        ResponseEntity<PontoDescarte> response = pontoDescarteController.buscarPorId(1L);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody().getId());
    }

    @Test
    @DisplayName("Deve retornar 404 quando ponto não encontrado")
    void deveRetornar404QuandoPontoNaoEncontrado() {
        // Arrange
        when(pontoDescarteRepository.findById(99L)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<PontoDescarte> response = pontoDescarteController.buscarPorId(99L);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    @DisplayName("Deve cadastrar novo ponto de descarte")
    void deveCadastrarNovoPonto() {
        // Arrange
        when(pontoDescarteRepository.save(any(PontoDescarte.class))).thenReturn(ponto);

        // Act
        ResponseEntity<PontoDescarte> response = pontoDescarteController.cadastrarPonto(ponto);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Prefeitura de Maringá", response.getBody().getNome());
        verify(pontoDescarteRepository, times(1)).save(any(PontoDescarte.class));
    }
}