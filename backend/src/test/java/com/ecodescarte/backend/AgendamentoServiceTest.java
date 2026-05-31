package com.ecodescarte.backend;

import com.ecodescarte.backend.model.Agendamento;
import com.ecodescarte.backend.repository.AgendamentoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.ecodescarte.backend.controller.AgendamentoController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes do AgendamentoController")
class AgendamentoServiceTest {

    @Mock
    private AgendamentoRepository agendamentoRepository;

    @InjectMocks
    private AgendamentoController agendamentoController;

    private Agendamento agendamento;

    @BeforeEach
    void setUp() {
        agendamento = new Agendamento();
        agendamento.setId(1L);
        agendamento.setNome("Ana Souza");
        agendamento.setEmail("ana@email.com");
        agendamento.setTelefone("44999999999");
        agendamento.setEndereco("Av. Brasil, 100, Maringá");
        agendamento.setTipoResiduo("Celular");
        agendamento.setDataColeta(LocalDate.of(2026, 6, 20));
        agendamento.setObservacoes("Dois celulares antigos");
    }

    @Test
    @DisplayName("Deve criar agendamento com status PENDENTE quando status não informado")
    void deveCriarAgendamentoComStatusPendente() {
        // Arrange
        agendamento.setStatus(null);
        when(agendamentoRepository.save(any(Agendamento.class))).thenReturn(agendamento);

        // Act
        ResponseEntity<Agendamento> response = agendamentoController.criarAgendamento(agendamento);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("PENDENTE", response.getBody().getStatus());
        verify(agendamentoRepository, times(1)).save(any(Agendamento.class));
    }

    @Test
    @DisplayName("Deve manter status informado quando já definido")
    void deveMaterStatusDefinido() {
        // Arrange
        agendamento.setStatus("CONFIRMADO");
        when(agendamentoRepository.save(any(Agendamento.class))).thenReturn(agendamento);

        // Act
        ResponseEntity<Agendamento> response = agendamentoController.criarAgendamento(agendamento);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("CONFIRMADO", response.getBody().getStatus());
    }

    @Test
    @DisplayName("Deve retornar HTTP 201 ao criar agendamento com sucesso")
    void deveRetornarHttp201AoCriarAgendamento() {
        // Arrange
        when(agendamentoRepository.save(any(Agendamento.class))).thenReturn(agendamento);

        // Act
        ResponseEntity<Agendamento> response = agendamentoController.criarAgendamento(agendamento);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody().getId());
    }

    @Test
    @DisplayName("Deve chamar o repository uma vez ao salvar agendamento")
    void deveChamarRepositoryUmaVez() {
        // Arrange
        when(agendamentoRepository.save(any(Agendamento.class))).thenReturn(agendamento);

        // Act
        agendamentoController.criarAgendamento(agendamento);

        // Assert
        verify(agendamentoRepository, times(1)).save(any(Agendamento.class));
    }
}