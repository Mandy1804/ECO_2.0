package com.ecodescarte.backend;

import com.ecodescarte.backend.controller.UsuarioController;
import com.ecodescarte.backend.model.Usuario;
import com.ecodescarte.backend.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes do UsuarioController")
class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioController usuarioController;

    private Usuario usuario;

    @BeforeEach
    void setUp() {
        usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNome("Carlos Mendes");
        usuario.setEmail("carlos@email.com");
        usuario.setSenha("senha123");
        usuario.setTelefone("44988888888");
        usuario.setEndereco("Rua das Flores, 200, Maringá");
    }

    @Test
    @DisplayName("Deve cadastrar usuário com sucesso quando email não existe")
    void deveCadastrarUsuarioComSucesso() {
        // Arrange
        when(usuarioRepository.existsByEmail(usuario.getEmail())).thenReturn(false);
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);

        // Act
        ResponseEntity<?> response = usuarioController.cadastrarUsuario(usuario);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        verify(usuarioRepository, times(1)).save(any(Usuario.class));
    }

    @Test
    @DisplayName("Deve retornar erro 400 quando email já cadastrado")
    void deveRetornarErroCom400QuandoEmailJaCadastrado() {
        // Arrange
        when(usuarioRepository.existsByEmail(usuario.getEmail())).thenReturn(true);

        // Act
        ResponseEntity<?> response = usuarioController.cadastrarUsuario(usuario);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Erro: Este e-mail já está cadastrado.", response.getBody());
        verify(usuarioRepository, never()).save(any(Usuario.class));
    }

    @Test
    @DisplayName("Deve fazer login com sucesso quando credenciais corretas")
    void deveFazerLoginComSucesso() {
        // Arrange
        when(usuarioRepository.findByEmailAndSenha(usuario.getEmail(), usuario.getSenha()))
                .thenReturn(usuario);

        // Act
        ResponseEntity<?> response = usuarioController.login(usuario);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Login bem-sucedido!", response.getBody());
    }

    @Test
    @DisplayName("Deve retornar 401 quando credenciais inválidas")
    void deveRetornar401QuandoCredenciaisInvalidas() {
        // Arrange
        when(usuarioRepository.findByEmailAndSenha(usuario.getEmail(), usuario.getSenha()))
                .thenReturn(null);

        // Act
        ResponseEntity<?> response = usuarioController.login(usuario);

        // Assert
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("Credenciais inválidas.", response.getBody());
    }

    @Test
    @DisplayName("Não deve salvar usuário quando email duplicado")
    void naoDeveSalvarUsuarioComEmailDuplicado() {
        // Arrange
        when(usuarioRepository.existsByEmail(usuario.getEmail())).thenReturn(true);

        // Act
        usuarioController.cadastrarUsuario(usuario);

        // Assert
        verify(usuarioRepository, never()).save(any(Usuario.class));
    }
}