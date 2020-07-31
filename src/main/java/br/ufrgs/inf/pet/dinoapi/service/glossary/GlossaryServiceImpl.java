package br.ufrgs.inf.pet.dinoapi.service.glossary;

import br.ufrgs.inf.pet.dinoapi.entity.GlossaryItem;
import br.ufrgs.inf.pet.dinoapi.model.glossary.GlossaryResponseModel;
import br.ufrgs.inf.pet.dinoapi.model.glossary.GlossaryUpdateModel;
import br.ufrgs.inf.pet.dinoapi.model.glossary.GlossaryItemResponseModel;
import br.ufrgs.inf.pet.dinoapi.model.glossary.GlossaryItemSaveModel;
import br.ufrgs.inf.pet.dinoapi.model.glossary.GlossarySaveModel;
import br.ufrgs.inf.pet.dinoapi.model.glossary.GlossaryItemUpdateModel;
import br.ufrgs.inf.pet.dinoapi.repository.GlossaryItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GlossaryServiceImpl implements GlossaryService {
    @Autowired
    GlossaryItemRepository glossaryItemRepository;

    @Autowired
    GlossaryVersionServiceImpl glossaryVersionService;

    @Override
    public ResponseEntity<GlossaryResponseModel> save(GlossarySaveModel glossarySaveModel) {
        final GlossaryResponseModel response = new GlossaryResponseModel();
        Long glossaryVersion;

        if (glossarySaveModel != null) {
            final List<GlossaryItemSaveModel> newItemsList = glossarySaveModel.getItemList();
            Optional<GlossaryItem> glossaryItemSearchResult;
            GlossaryItem glossaryItem;
            GlossaryItemResponseModel responseItem;

            if (newItemsList != null) {
                for (GlossaryItemSaveModel newItem : newItemsList) {
                    if (newItem.isValid()) {
                        glossaryItemSearchResult = glossaryItemRepository.findByTitle(newItem.getTitle());

                        if (!glossaryItemSearchResult.isPresent()) {
                            glossaryItem = new GlossaryItem();
                            glossaryItem.setByGlossarySaveModel(newItem);

                            glossaryItem = glossaryItemRepository.save(glossaryItem);

                            responseItem = new GlossaryItemResponseModel();

                            responseItem.setByGlossaryItem(glossaryItem);

                            response.addItem(responseItem);
                        }
                    }
                }
            }
        }

        if (response.getSize() > 0) {
            glossaryVersion = glossaryVersionService.updateGlossaryVersion();
        } else {
            glossaryVersion = glossaryVersionService.getGlossaryVersionNumber();
        }

        response.setVersion(glossaryVersion);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> update(GlossaryUpdateModel glossaryUpdateModel) {
        final GlossaryResponseModel response = new GlossaryResponseModel();
        Long glossaryVersion = glossaryVersionService.getGlossaryVersionNumber();

        if (glossaryUpdateModel.getVersion() != glossaryVersion) {
            return new ResponseEntity<>("Versão do glossário inválida.", HttpStatus.BAD_REQUEST);
        }

        if (glossaryUpdateModel != null) {
            final List<GlossaryItemUpdateModel> updatedItemsList = glossaryUpdateModel.getItemList();

            if (updatedItemsList != null) {
                Optional<GlossaryItem> glossaryItemSearchResult;
                GlossaryItem dbGlossaryItem;
                Boolean updated;
                GlossaryItemResponseModel responseItem;

                for (GlossaryItemUpdateModel updatedItem : updatedItemsList) {
                    if (updatedItem.isValid()) {
                        glossaryItemSearchResult = glossaryItemRepository.findById(updatedItem.getId());

                        if (glossaryItemSearchResult.isPresent()) {
                            dbGlossaryItem = glossaryItemSearchResult.get();

                            updated = dbGlossaryItem.update(updatedItem);

                            if (updated) {
                                dbGlossaryItem = glossaryItemRepository.save(dbGlossaryItem);

                                responseItem = new GlossaryItemResponseModel();

                                responseItem.setByGlossaryItem(dbGlossaryItem);

                                response.addItem(responseItem);
                            }
                        }

                    }
                }
            }
        }

        if (response.getSize() > 0) {
            glossaryVersion = glossaryVersionService.updateGlossaryVersion();
        }

        response.setVersion(glossaryVersion);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<GlossaryItem>> get() {
        GlossaryItemResponseModel responseItem;

        final List<GlossaryItem> response = glossaryItemRepository.findAllByExistsTrue();

        for (GlossaryItem item : response) {
            responseItem = new GlossaryItemResponseModel();

            responseItem.setByGlossaryItem(item);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
