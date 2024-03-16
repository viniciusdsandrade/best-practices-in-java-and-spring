package br.com.alura.adopet.api.controller;

import br.com.alura.adopet.api.service.TutorService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Testes para TutorController")
class TutorControllerTest {

    @MockBean
    private TutorService service;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Deveria devolver codigo 200 para requisicao de listar todos tutores")
    void deveriaDevolverCodigo200ParaRequisicaoDeCadastrarTutor() throws Exception {
        // ARRANGE
        String json = """
                {
                    "nome": "Vinícius dos Santos Andrade",
                    "telefone": "(19)97413-3884",
                    "email": "vinicius_andrade2010@hotmail.com.br"
                }
                """;
        // ACT
        MockHttpServletResponse response = mockMvc.perform(
                post("/api/v1/tutores")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        // ASSERT
        assertEquals(200, response.getStatus());
    }

    @Test
    @DisplayName("Deveria devolver codigo 400 para requisicao de listar todos tutores")
    void deveriaDevolverCodigo400ParaRequisicaoCadastrarTutorDadosInvalidos() throws Exception {
        // ARRANGE
        String json = """
                {
                     "nome": "Vinícius dos Santos Andrade",
                    "telefone": "(19)97413-3884",
                    "email": "vinicius_andrade2010@hotmail.com.br"
                }
                """;

        // ACT
        MockHttpServletResponse response = mockMvc.perform(
                post("/api/v1/tutores")
                        .contentType(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        // ASSERT
        assertEquals(400, response.getStatus());
    }

    @Test
    @DisplayName("Deveria devolver codigo 200 para requisicao de atualizar tutor")
    void deveriaDevolverCodigo200ParaRequisicaoDeAtualizarTutor() throws Exception {
        // ARRANGE
        String json = """
                {
                    "id" : "1",
                     "nome": "Vinícius dos Santos Andrade",
                    "telefone": "(19)97413-3884",
                    "email": "vinicius_andrade2010@hotmail.com.br"
                }
                """;

        // ACT
        MockHttpServletResponse response = mockMvc.perform(
                put("/api/v1/tutores")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        // ASSERT
        assertEquals(200, response.getStatus());
    }

    @Test
    @DisplayName("Deveria devolver codigo 400 para requisicao de atualizar tutor")
    void deveriaDevolverCodigo400ParaRequisicaoDeAtualizarTutor() throws Exception {
        // ARRANGE
        String json = """
                {
                    "id" : "2",
                     "nome": "Vinícius dos Santos Andrade",
                    "telefone": "(19)97413-3884",
                    "email": "vinicius_andrade2010@hotmail.com.br"
                }
                """;

        // ACT
        MockHttpServletResponse response = mockMvc.perform(
                put("/api/v1/tutores")
                        .contentType(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        // ASSERT
        assertEquals(400, response.getStatus());
    }
}
