package fr.istv.covivre.covivre.controller;

import fr.istv.covivre.covivre.controller.dto.CreateNumberIdentifierDto;
import fr.istv.covivre.covivre.model.NumberIdentifier;
import fr.istv.covivre.covivre.repository.NumberIdentifierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("uuid")
public class UUIDController {

    @Autowired
    NumberIdentifierRepository repository;

    /**
     *
     * @param input containing phone number
     * @return NumberIdentifier
     * NumberIdentifier contains unique 'uuid' to identify phone number, it will be using later
     * for temporary token.
     */
    @PostMapping
    public NumberIdentifier getNumberIdentifier(@RequestBody CreateNumberIdentifierDto input) {
        NumberIdentifier toSave = NumberIdentifier.builder()
                .uuid(UUID.randomUUID().toString())
                .phoneNumber(input.getPhoneNumber()).build();
        repository.save(toSave);
        return toSave;
    }

}
