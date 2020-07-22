package br.ufrgs.inf.pet.dinoapi.repository.contact;

import br.ufrgs.inf.pet.dinoapi.entity.contacts.Phone;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Repository
public interface PhoneRepository extends CrudRepository<Phone, Long> {

        //@Query("SELECT c FROM Contact c WHERE c.name IN ?1 AND c.user.id = ?2")
        //List<Contact> findAllByNameAndUserId(String contactName, Long userId);

    @Query("SELECT p FROM Phone p WHERE p.id IN ?1 AND p.contact.id = ?2")
    List<Phone> findAllByIdAndContactId(List<Long> phoneIds, Long contactId);

    @Query("SELECT p FROM Phone p WHERE p.id IN ?1 AND p.contact.id = ?2")
    Optional<Phone> findByIdAndContactId(Long phoneId, Long contactId);

    @Transactional
    @Modifying
    @Query("DELETE FROM Phone n WHERE n.id = ?1 AND n.contact.id = ?2")
    int deleteByIdAndContactId(Long id, Long contactId);

    @Transactional
    @Modifying
    @Query("DELETE FROM Phone n WHERE n.id = ?1 AND n.contact.id = ?2")
    int deleteAllByIdAndContactId(List<Long> id, Long contactId);


}