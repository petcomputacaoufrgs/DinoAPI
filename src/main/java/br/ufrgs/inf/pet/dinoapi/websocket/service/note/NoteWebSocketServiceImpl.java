package br.ufrgs.inf.pet.dinoapi.websocket.service.note;

import br.ufrgs.inf.pet.dinoapi.service.auth.dino.AuthServiceImpl;
import br.ufrgs.inf.pet.dinoapi.websocket.constants.WebSocketDestinations;
import br.ufrgs.inf.pet.dinoapi.websocket.model.note.NoteWebSocketAlertUpdateModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class NoteWebSocketServiceImpl implements NoteWebSocketService {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private AuthServiceImpl authService;

    @Override
    public void sendNoteUpdateMessage(Long newVersion) {
        final NoteWebSocketAlertUpdateModel model = new NoteWebSocketAlertUpdateModel();
        model.setNewVersion(newVersion);
        final UserDetails principal = authService.getPrincipal();
        simpMessagingTemplate.convertAndSendToUser(principal.getUsername(), WebSocketDestinations.ALERT_NOTE_UPDATE, model);
    }
}