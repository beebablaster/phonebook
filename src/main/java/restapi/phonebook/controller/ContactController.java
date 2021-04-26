package restapi.phonebook.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import restapi.phonebook.entity.Contact;
import restapi.phonebook.repository.ContactRepository;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class ContactController {
    @Autowired
    private ContactRepository contactRepository;

    @GetMapping("/contacts")
    public List<Contact> getAllContacts(){
        return this.contactRepository.findAll();
    }

    @GetMapping("/contacts/{id}")
    public ResponseEntity<Contact> getContactsById(@PathVariable(value = "id") Long id)
            throws ResourceNotFoundException {
        Contact contact =
                contactRepository
                        .findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("Contact not found on :: " + id));
        return ResponseEntity.ok().body(contact);
    }

    @PostMapping("/contacts")
    public Contact createContact(@Validated @RequestBody Contact contact){
        return contactRepository.save(contact);
    }

    @PutMapping("/contacts/{id}")
    public ResponseEntity<Contact> updateContact(
            @PathVariable(value = "id") Long id, @Validated @RequestBody Contact contactDetails)
            throws ResourceNotFoundException
    {
        Contact contact =
                contactRepository
                        .findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("Contact not found on :: " + id));
        contact.setPhoneNumber(contactDetails.getPhoneNumber());
        contact.setFirstName(contactDetails.getFirstName());
        contact.setMiddleName(contactDetails.getMiddleName());
        contact.setLastName(contactDetails.getLastName());
        final Contact updatedContact = contactRepository.save(contact);
        return ResponseEntity.ok(updatedContact);
    }


    @DeleteMapping("/contacts/{id}")
    public Map<String, Boolean> deleteContact(@PathVariable(value = "id") Long id)
            throws ResourceNotFoundException{
        Contact contact =
                contactRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contact not found on :: " + id));

        contactRepository.delete(contact);
        Map<String,Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;



    }
}
