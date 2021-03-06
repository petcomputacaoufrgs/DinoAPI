package br.ufrgs.inf.pet.dinoapi.communication.google.people;

import br.ufrgs.inf.pet.dinoapi.entity.contacts.GoogleContact;
import br.ufrgs.inf.pet.dinoapi.entity.user.User;
import br.ufrgs.inf.pet.dinoapi.model.google.people.GooglePeopleModel;
import br.ufrgs.inf.pet.dinoapi.service.auth.google.GoogleAuthService;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public interface GooglePeopleCommunication {
    /**
     * Get a contact on Google People API
     * @param user dino user that owns the contact
     * @param resourceName resourceName of saved Google Contact
     * @return GooglePeopleModel or null
     */
    GooglePeopleModel getContact(
            User user, String resourceName
    ) throws IOException, InterruptedException, URISyntaxException;

    /**
     * Save a contact on Google People API
     * @param user dino user that owns the contact
     * @param name contact name
     * @param description contact description
     * @param phoneNumbers list of contact's phone numbers
     * @return saved GooglePeopleModel or null
     */
    GooglePeopleModel createContact(
            User user, String name, String description, List<String> phoneNumbers
    );

    /**
     * Save a contact on Google People API
     * @param user dino user that owns the contact
     * @param name contact name
     * @param description contact description
     * @param phoneNumbers list of contact's phone numbers
     * @param googleContact entity with saved Google People API data
     * @return saved GooglePeopleModel or null
     */
    GooglePeopleModel updateContact(
            User user, String name, String description, List<String> phoneNumbers, GoogleContact googleContact
    );

    /**
     * Delete a contact on Google People API
     * @param user dino user that owns the contact
     * @param resourceName identifier of google contact
     * @return
     */
    boolean deleteContact(User user, String resourceName);
}
