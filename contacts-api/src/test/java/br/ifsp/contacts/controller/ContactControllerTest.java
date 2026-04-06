package br.ifsp.contacts.controller;

import br.ifsp.contacts.model.Contact;
import br.ifsp.contacts.repository.ContactRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ContactController.class)
public class    ContactControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ContactRepository contactRepository; // Simulamos a base de dados para não depender dela

    @Autowired
    private ObjectMapper objectMapper; // Ajuda a converter objetos Java em JSON

    @Test
    public void deveRetornarListaDeContatosComSucesso() throws Exception {
        Contact contato = new Contact("Ana", "11999999999", "ana@email.com");
        contato.setId(1L);
        Mockito.when(contactRepository.findAll()).thenReturn(List.of(contato));

        mockMvc.perform(get("/api/contacts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome").value("Ana"))
                .andExpect(jsonPath("$[0].email").value("ana@email.com"));
    }

    @Test
    public void deveRetornarErro400AoEnviarEmailInvalido() throws Exception {
        Contact contatoInvalido = new Contact("João", "11888888888", "email-errado");
        String jsonRequisicao = objectMapper.writeValueAsString(contatoInvalido);

        mockMvc.perform(post("/api/contacts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequisicao))
                .andExpect(status().isBadRequest()) // Verifica se dá erro 400
                .andExpect(jsonPath("$.Erro").value("Digite um email válido"));
    }
}