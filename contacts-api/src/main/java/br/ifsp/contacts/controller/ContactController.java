package br.ifsp.contacts.controller;

import br.ifsp.contacts.dto.ContactResponseDTO;
import br.ifsp.contacts.model.Contact;
import br.ifsp.contacts.repository.ContactRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import br.ifsp.contacts.exception.ResourceNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

/*
* Classe responsavel por mapear as rotas ou endpoints dos contatos
* */

@RestController //indica que é um controller de requisições REST
@RequestMapping("/api/contacts")// indica o caminhos dos endpoints

public class ContactController {

    @Autowired //injeta automaticamente uma instancia
    private ContactRepository contactRepository; //classe repository

    @GetMapping//indica que o endpoint chaqmado é metodo GET
    public List<ContactResponseDTO> getAllContacts(){
        return contactRepository.findAll().stream()
                .map(ContactResponseDTO::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")//indica metodo GET
    public ContactResponseDTO getContactId(@PathVariable Long id){ //@PathVariable "amarra" a variável {id} da URL
        // findById retorna um Optional, então usamos orElseThrow
        Contact contact = contactRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contato com ID:" + id+ " não encontrado"));
        return new ContactResponseDTO(contact);
    }

    @PostMapping//indica metodo POST (create)
    public ContactResponseDTO createContact(@Valid @RequestBody Contact contact){ //@RequestBody indica que o objeto Contact será preenchido
        Contact savedContact = contactRepository.save(contact);
        return  new ContactResponseDTO(savedContact);
    }

    @PutMapping("/{id}") //metodo PUT de update pelo id do contato
    public ContactResponseDTO updateContact(@PathVariable Long id, @Valid @RequestBody Contact updateContact){
        Contact existingContact = contactRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Contato com ID:" + id+ " não encontrado")); //caso não encontrado

        existingContact.setNome(updateContact.getNome()); //atualiza nome
        existingContact.setTelefone(updateContact.getTelefone()); //atualiza telefone
        existingContact.setEmail(updateContact.getEmail()); //atualiza email

        Contact contactSalvo =  contactRepository.save(existingContact);
        return new ContactResponseDTO(contactSalvo);//salva contato atualizado
    }

    @DeleteMapping("/{id}") //metodo DELETE contato pelo id
    public void deleleContact(@PathVariable Long id){
        Contact existingContact = contactRepository.findById(id)
            .orElseThrow(()-> new ResourceNotFoundException("Não foi possível deletar contato ID: " +id+ ". Contato não encontrado"));

        contactRepository.delete(existingContact);
    }

    @GetMapping("/search") //metodo GET para buscar contato pelo nome
    public List<ContactResponseDTO> getContactByName(@RequestParam("name") String nome){
        // 1. Guarda o resultado da busca numa lista
        List<Contact> contatosEncontrados = contactRepository.findByNomeContainingIgnoreCase(nome);

        // 2. Verifica se a lista está vazia
        if (contatosEncontrados.isEmpty()) {
            throw new ResourceNotFoundException("Nenhum contato encontrado com o nome: " + nome);
        }

        // 3. Se não estiver vazia, converte para DTO e retorna
        return contatosEncontrados.stream()
                .map(ContactResponseDTO::new)
                .collect(Collectors.toList());
    }

    @PatchMapping("/{id}")// metodo PATH para atualizar apenas um ou mais campos
    public ContactResponseDTO pathUpdateContact (@PathVariable Long id, @RequestBody Contact parcialContact){
        Contact existingContact = contactRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Contato com o ID " + id + " não encontrado!")); //busca se o contato existe pelo id

        if (parcialContact.getNome() != null && !parcialContact.getNome().isBlank()){ //nn atualizar dado nulo
            existingContact.setNome(parcialContact.getNome()); //update nome
        }
        if (parcialContact.getTelefone() != null && !parcialContact.getTelefone().isBlank()){ //nn atualizar dado nulo
            existingContact.setTelefone(parcialContact.getTelefone()); //update telefone
        }
        if (parcialContact.getEmail() != null && !parcialContact.getEmail().isBlank()){ //nn atualizar dado nulo
            existingContact.setEmail(parcialContact.getEmail()); //update email
        }

        Contact contactSalvo = contactRepository.save(existingContact);
        return new ContactResponseDTO(contactSalvo);//retorna com o campo atualizado
    }

}
