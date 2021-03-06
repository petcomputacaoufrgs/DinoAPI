package br.ufrgs.inf.pet.dinoapi.repository.glossary;

import br.ufrgs.inf.pet.dinoapi.entity.glossary.GlossaryItem;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface GlossaryItemRepository extends CrudRepository<GlossaryItem, Long> {
    @Query("SELECT gi FROM GlossaryItem gi WHERE gi.id = :id ")
    Optional<GlossaryItem> findById(@Param("id") Long id);

    @Query("SELECT gi FROM GlossaryItem gi")
    List<GlossaryItem> findAll();

    @Query("SELECT gi FROM GlossaryItem gi WHERE gi.id IN :ids")
    List<GlossaryItem> findByIds(@Param("ids") List<Long> ids);

    @Query("SELECT gi FROM GlossaryItem gi WHERE gi.id NOT IN :ids")
    List<GlossaryItem> findAllExcludingIds(@Param("ids") List<Long> ids);

    @Query("SELECT gi FROM GlossaryItem gi WHERE gi.title = :title")
    Optional<GlossaryItem> findByTitle(@Param("title") String title);

    @Query("SELECT gi FROM GlossaryItem gi WHERE gi.title IN :titles ORDER BY gi.title")
    List<GlossaryItem> findAllByTitlesOrderedByTitle(@Param("titles") List<String> titles);
}
