package fr.istv.covivre.covivre.repository;

import fr.istv.covivre.covivre.model.NumberIdentifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NumberIdentifierRepository extends JpaRepository<NumberIdentifier, String> {

}

