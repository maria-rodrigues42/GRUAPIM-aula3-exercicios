package br.ifsp.contacts.controller;

import br.ifsp.contacts.exception.ResourceNotFoundException;
import br.ifsp.contacts.model.Address;
import br.ifsp.contacts.model.Contact;
import br.ifsp.contacts.repository.AddressRepository;
import br.ifsp.contacts.repository.ContactRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/contacts")
public class ContactController {

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private AddressRepository addressRepository;

    @GetMapping
    public List<Contact> getAllContacts() {
        return contactRepository.findAll();
    }

    @GetMapping("/{id}")
    public Contact getContactId(@PathVariable Long id) {
        return contactRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contato não encontrado"));
    }

    @PostMapping
    public Contact createContact(@Valid @RequestBody Contact contact) {
        return contactRepository.save(contact);
    }

    @PutMapping("/{id}")
    public Contact updateContact(@PathVariable Long id, @Valid @RequestBody Contact updateContact) {
        Contact existingContact = contactRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contato não encontrado"));

        existingContact.setNome(updateContact.getNome());
        existingContact.setTelefone(updateContact.getTelefone());
        existingContact.setEmail(updateContact.getEmail());

        return contactRepository.save(existingContact);
    }

    @DeleteMapping("/{id}")
    public void deleleContact(@PathVariable Long id) {
        if (!contactRepository.existsById(id)) {
            throw new ResourceNotFoundException("Contato não encontrado");
        }
        contactRepository.deleteById(id);
    }

    @GetMapping("/search")
    public List<Contact> getContactByName(@RequestParam("name") String nome) {
        return contactRepository.findByNomeContainingIgnoreCase(nome);
    }

    @PatchMapping("/{id}")
    public Contact pathUpdateContact(@PathVariable Long id, @RequestBody Contact parcialContact) {
        Contact existingContact = contactRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contato não encontrado"));

        if (parcialContact.getNome() != null && !parcialContact.getNome().isBlank()) {
            existingContact.setNome(parcialContact.getNome());
        }
        if (parcialContact.getTelefone() != null && !parcialContact.getTelefone().isBlank()) {
            existingContact.setTelefone(parcialContact.getTelefone());
        }
        if (parcialContact.getEmail() != null && !parcialContact.getEmail().isBlank()) {
            existingContact.setEmail(parcialContact.getEmail());
        }

        return contactRepository.save(existingContact);
    }

    @GetMapping("/{id}/addresses")
    public List<Address> getAddressesByContact(@PathVariable Long id) {
        if (!contactRepository.existsById(id)) {
            throw new ResourceNotFoundException("Contato não encontrado");
        }
        return addressRepository.findByContact_Id(id);
    }
}
