package br.ufrgs.inf.pet.dinoapi.controller.contacts;

import br.ufrgs.inf.pet.dinoapi.controller.synchronizable.SynchronizableControllerImpl;
import br.ufrgs.inf.pet.dinoapi.entity.contacts.GoogleContact;
import br.ufrgs.inf.pet.dinoapi.model.contacts.GoogleContactModel;
import br.ufrgs.inf.pet.dinoapi.repository.contact.GoogleContactRepository;
import br.ufrgs.inf.pet.dinoapi.service.contact.GoogleContactServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/google_contact/")
public class GoogleContactControllerImpl extends SynchronizableControllerImpl<
        GoogleContact, Long, GoogleContactModel, GoogleContactRepository, GoogleContactServiceImpl> {

    @Autowired
    public GoogleContactControllerImpl(GoogleContactServiceImpl service) {
        super(service);
    }
}
