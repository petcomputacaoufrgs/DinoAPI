package br.ufrgs.inf.pet.dinoapi.service.log_error;

import br.ufrgs.inf.pet.dinoapi.entity.log_error.LogAppError;
import br.ufrgs.inf.pet.dinoapi.model.log_app_error.LogAppErroListRequestModel;
import br.ufrgs.inf.pet.dinoapi.model.log_app_error.LogAppErrorRequestModel;
import br.ufrgs.inf.pet.dinoapi.repository.log_error.LogAppErrorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LogAppErrorServiceImpl implements LogAppErrorService {

    private final LogAppErrorRepository logAppErrorRepository;

    @Autowired
    public LogAppErrorServiceImpl(LogAppErrorRepository logAppErrorRepository) {
        this.logAppErrorRepository = logAppErrorRepository;
    }

    @Override
    public ResponseEntity<Void> save(LogAppErrorRequestModel model, HttpServletRequest httpServletRequest) {
        final String userAgent = httpServletRequest.getHeader("User-Agent");

        LogAppError error = this.generateError(model, userAgent);

        logAppErrorRepository.save(error);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> saveAll(LogAppErroListRequestModel model, HttpServletRequest httpServletRequest) {
        final String userAgent = httpServletRequest.getHeader("User-Agent");

        List<LogAppError> items = model.getItems().stream().map(item ->
                this.generateError(item, userAgent)).collect(Collectors.toList());

        logAppErrorRepository.saveAll(items);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public void deleteOldItems(LocalDateTime deadline) {
        logAppErrorRepository.deleteAllByDate(deadline);
    }

    private LogAppError generateError(LogAppErrorRequestModel model, String userAgent) {
        LogAppError error = new LogAppError();
        error.setError(model.getError());
        error.setFile(model.getFile());
        error.setTitle(model.getTitle());
        error.setDate(model.getDate().toLocalDateTime());
        error.setUserAgent(userAgent);
        return error;
    }
}
