package fr.istv.covivre.covivre.repository;

import fr.istv.covivre.covivre.model.NumberIdentifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NumberIdentifierRepository extends JpaRepository<NumberIdentifier, String> {

    @Query( "select o from NumberIdentifier o where uuid in :uuids" )
    List<NumberIdentifier> findByInventoryUuids(@Param("uuids") List<String> uuids);

}

