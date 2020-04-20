package br.ufrgs.inf.pet.dinoapi.service.user_app_settings;

import br.ufrgs.inf.pet.dinoapi.model.user_app_settings.UserAppSettingsRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * Service para a tabela: {@link br.ufrgs.inf.pet.dinoapi.entity.User}
 *
 * @author joao.silva
 */
@Service
public interface UserAppSettingsService {

    /**
     * Busca as configurações do usuário logado atual
     *
     * @return Entidade com as configurações do usuário salvas e o status da requisição
     *
     * @author joao.silva
     */
    ResponseEntity<?> getUserAppSettings();

    /**
     * Salva os configurações vindas da Model
     *
     * @param userAppSettingsModel Model com os dados para serem salvos
     * @return Entidade com status de sucesso ou erro
     */
    ResponseEntity<?> saveUserAppSettings(UserAppSettingsRequest userAppSettingsModel);

    /**
     * Retorna a versão da configuração do usuário logado.
     * Versão da configuração ou erro.
     * @return
     */
    ResponseEntity<?> getUserAppSettingsVersion();

}