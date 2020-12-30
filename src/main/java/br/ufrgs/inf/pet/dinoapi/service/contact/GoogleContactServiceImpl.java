package br.ufrgs.inf.pet.dinoapi.service.contact;

import br.ufrgs.inf.pet.dinoapi.constants.GoogleContactConstants;
import br.ufrgs.inf.pet.dinoapi.entity.auth.Auth;
import br.ufrgs.inf.pet.dinoapi.entity.contacts.Contact;
import br.ufrgs.inf.pet.dinoapi.entity.contacts.GoogleContact;
import br.ufrgs.inf.pet.dinoapi.entity.user.User;
import br.ufrgs.inf.pet.dinoapi.exception.synchronizable.AuthNullException;
import br.ufrgs.inf.pet.dinoapi.exception.synchronizable.ConvertModelToEntityException;
import br.ufrgs.inf.pet.dinoapi.model.contacts.GoogleContactDataModel;
import br.ufrgs.inf.pet.dinoapi.repository.contact.GoogleContactRepository;
import br.ufrgs.inf.pet.dinoapi.service.auth.AuthServiceImpl;
import br.ufrgs.inf.pet.dinoapi.service.clock.ClockServiceImpl;
import br.ufrgs.inf.pet.dinoapi.service.log_error.LogAPIErrorServiceImpl;
import br.ufrgs.inf.pet.dinoapi.service.synchronizable.SynchronizableServiceImpl;
import br.ufrgs.inf.pet.dinoapi.websocket.enumerable.WebSocketDestinationsEnum;
import br.ufrgs.inf.pet.dinoapi.websocket.service.queue.SynchronizableQueueMessageServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class GoogleContactServiceImpl extends SynchronizableServiceImpl<GoogleContact, Long, Integer, GoogleContactDataModel, GoogleContactRepository> {

    private final ContactServiceImpl contactService;

    @Autowired
    public GoogleContactServiceImpl(GoogleContactRepository repository, AuthServiceImpl authService, ContactServiceImpl contactService,
                                    SynchronizableQueueMessageServiceImpl<Long, Integer, GoogleContactDataModel> synchronizableQueueMessageService,
                                    ClockServiceImpl clockService, LogAPIErrorServiceImpl logAPIErrorService) {
        super(repository, authService, clockService, synchronizableQueueMessageService, logAPIErrorService);
        this.contactService = contactService;
    }

    @Override
    public GoogleContactDataModel convertEntityToModel(GoogleContact entity) {
        final GoogleContactDataModel model = new GoogleContactDataModel();
        model.setResourceName(entity.getResourceName());
        model.setContactId(entity.getContact().getId());
        return model;
    }

    @Override
    public GoogleContact convertModelToEntity(GoogleContactDataModel model, Auth auth) throws ConvertModelToEntityException, AuthNullException {
        if (auth != null) {
            final User user = auth.getUser();
            final Optional<Contact> googleContactSearch = contactService.findContactByIdAndUser(model.getContactId(), user);

            if (googleContactSearch.isPresent()) {
                GoogleContact googleContact = new GoogleContact();
                googleContact.setResourceName(model.getResourceName());
                googleContact.setContact(googleContactSearch.get());
                googleContact.setUser(user);
                return googleContact;
            }

            throw new ConvertModelToEntityException(GoogleContactConstants.INVALID_CONTACT_ERROR);
        }

        throw new AuthNullException();
    }

    @Override
    public void updateEntity(GoogleContact entity, GoogleContactDataModel model, Auth auth) throws ConvertModelToEntityException {
        entity.setResourceName(model.getResourceName());
    }

    @Override
    public Optional<GoogleContact> getEntityByIdAndUserAuth(Long id, Auth auth) throws AuthNullException {
        if (auth == null) {
            throw new AuthNullException();
        }
        return this.repository.findByIdAndUserId(id, auth.getUser().getId());
    }

    @Override
    public List<GoogleContact> getEntitiesByUserAuth(Auth auth) throws AuthNullException {
        if (auth == null) {
            throw new AuthNullException();
        }
        return this.repository.findAllByUserId(auth.getUser().getId());
    }

    @Override
    public List<GoogleContact> getEntitiesByIdsAndUserAuth(List<Long> ids, Auth auth) throws AuthNullException {
        if (auth == null) {
            throw new AuthNullException();
        }
        return this.repository.findAllByIdAndUserId(ids, auth.getUser().getId());
    }

    @Override
    public List<GoogleContact> getEntitiesByUserAuthExceptIds(Auth auth, List<Long> ids) throws AuthNullException {
        if (auth == null) {
            throw new AuthNullException();
        }
        return this.repository.findAllByUserIdExceptIds(auth.getUser().getId(), ids);
    }

    @Override
    public WebSocketDestinationsEnum getUpdateWebSocketDestination() {
        return WebSocketDestinationsEnum.GOOGLE_CONTACT_UPDATE;
    }

    @Override
    public WebSocketDestinationsEnum getDeleteWebSocketDestination() {
        return WebSocketDestinationsEnum.GOOGLE_CONTACT_DELETE;
    }
}