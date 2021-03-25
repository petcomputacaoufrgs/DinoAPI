package br.ufrgs.inf.pet.dinoapi.service.contact.async;

import br.ufrgs.inf.pet.dinoapi.entity.auth.Auth;
import br.ufrgs.inf.pet.dinoapi.entity.user.User;
import br.ufrgs.inf.pet.dinoapi.model.contacts.GoogleContactDataModel;
import br.ufrgs.inf.pet.dinoapi.model.google.people.GooglePeopleModel;
import br.ufrgs.inf.pet.dinoapi.service.clock.ClockServiceImpl;
import br.ufrgs.inf.pet.dinoapi.service.log_error.LogAPIErrorServiceImpl;
import br.ufrgs.inf.pet.dinoapi.service.log_error.LogUtilsBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import java.util.function.BiFunction;

@Service
public class AsyncSaveGoogleContactImpl extends LogUtilsBase implements AsyncSaveGoogleContact {
    private final ClockServiceImpl clockService;

    @Autowired
    public AsyncSaveGoogleContactImpl(ClockServiceImpl clockService,
                                      LogAPIErrorServiceImpl logAPIErrorService) {
        super(logAPIErrorService);
        this.clockService = clockService;
    }

    @Override
    @Async("defaultThreadPoolTaskExecutor")
    public void saveGoogleContact(User user, Long id, Long contactId, GooglePeopleModel googlePeopleModel, BiFunction<GoogleContactDataModel, Auth, Void> save) {
       try {
           final Auth fakeAuth = new Auth();
           fakeAuth.setUser(user);

           final GoogleContactDataModel googleContactDataModel = new GoogleContactDataModel();
           googleContactDataModel.setContactId(contactId);
           googleContactDataModel.setLastUpdate(clockService.getUTCZonedDateTime());
           googleContactDataModel.setId(id);

           if (googlePeopleModel != null) {
               googleContactDataModel.setResourceName(googlePeopleModel.getResourceName());
               googleContactDataModel.setSavedOnGoogleAPI(true);
           } else {
               googleContactDataModel.setSavedOnGoogleAPI(false);
           }

           save.apply(googleContactDataModel, fakeAuth);
       } catch (Exception e) {
           this.logAPIError(e);
       }
    }
}