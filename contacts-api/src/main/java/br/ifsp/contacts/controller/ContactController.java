package br.ifsp.contacts.controller;

import br.ifsp.contacts.model.Contact;
import br.ifsp.contacts.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
* Classe responsavel por mapear as rotas ou endpoints dos contatos
* */

@RestController //indica que é um controller de requisições REST
@RequestMapping("/api/contacts")// indica o caminhos dos endpoints

public class ContactController {

    @Autowired //injeta automaticamente uma instancia
    private ContactRepository contactRepository; //classe repository

    @GetMapping//indica que o endpoint chaqmado é metodo GET
    public List<Contact> getAllContacts(){
        return contactRepository.findAll();
    }

    @GetMapping("/{id}")//indica metodo GET
    public Contact getContactId(@PathVariable Long id){ //@PathVariable "amarra" a variável {id} da URL
        // findById retorna um Optional, então usamos orElseThrow
        return contactRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Contato não encontrado!"));
    }

    @PostMapping//indica metodo POST (create)
    public Contact createContact(@RequestBody Contact contact){ //@RequestBody indica que o objeto Contact será preenchido
        return contactRepository.save(contact);
    }

    @PutMapping("/{id}") //metodo PUT de update pelo id do contato
    public Contact updateContact(@PathVariable Long id, @RequestBody Contact updateContact){
        Contact existingContact = contactRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Contato não encontrado " + id)); //caso não encontrado

        existingContact.setNome(updateContact.getNome()); //atualiza nome
        existingContact.setTelefone(updateContact.getTelefone()); //atualiza telefone
        existingContact.setEmail(updateContact.getEmail()); //atualiza email

        return contactRepository.save(existingContact); //salva contato atualizado
    }

    @DeleteMapping("/{id}") //metodo DELETE contato pelo id
    public void deleleContact(@PathVariable Long id){
        contactRepository.deleteById(id);
    }

}
