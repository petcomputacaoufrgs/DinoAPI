package br.ufrgs.inf.pet.dinoapi.controller.contacts;

import br.ufrgs.inf.pet.dinoapi.model.contacts.*;
import br.ufrgs.inf.pet.dinoapi.service.contact.ContactServiceImpl;
import br.ufrgs.inf.pet.dinoapi.service.contact.PhoneServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
    @RequestMapping("/contacts/")
    public class ContactsControllerImpl implements ContactsController {

        @Autowired
        ContactServiceImpl contactServiceImpl;
        @Autowired
        PhoneServiceImpl phoneServiceImpl;

        @Override
        @GetMapping
        public ResponseEntity<List<ContactModel>> getAllContacts() {
            return contactServiceImpl.getAllContacts();
        }


        @PostMapping
        public ResponseEntity<ContactModel> saveContact(@RequestBody ContactSaveModel model) {
            return contactServiceImpl.saveContact(model);
        }

        @PostMapping("/all")
        public ResponseEntity<List<ContactModel>> saveContacts(@RequestBody List<ContactSaveModel> models) {
            return contactServiceImpl.saveContacts(models);
        }

        @PutMapping("/all")
        public ResponseEntity<?> editContacts(@RequestBody List<ContactModel> models) {
            return contactServiceImpl.editContacts(models);
        }

        @DeleteMapping
        public ResponseEntity<?> deleteContact(@RequestBody ContactModel model) {
            return contactServiceImpl.deleteContact(model);
        }

        @DeleteMapping("/all")
        public ResponseEntity<?> deleteContacts(@RequestBody List<ContactModel> models) {
            return contactServiceImpl.deleteContacts(models);
        }

        @DeleteMapping("/phone")
        public ResponseEntity<?> deletePhone(@RequestBody PhoneModel model) {
            return phoneServiceImpl.deletePhone(model);
        }

        @DeleteMapping("/phone/all")
        public ResponseEntity<?> deletePhones(@RequestBody List<PhoneModel> models) {
            return phoneServiceImpl.deletePhones(models);
        }

}