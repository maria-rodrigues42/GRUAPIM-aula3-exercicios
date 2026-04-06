package br.ifsp.contacts.dto;
import br.ifsp.contacts.model.Contact;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"id", "nome", "telefone", "email"})
public class ContactResponseDTO {
    private Long id;
    private String nome;
    private String telefone;
    private String email;

    public ContactResponseDTO(Long id, String nome, String telefone, String email) {
        this.id = id;
        this.nome = nome;
        this.telefone = telefone;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getEmail() {
        return email;
    }
}
