package fr.istv.covivre.covivre.controller;

import fr.istv.covivre.covivre.controller.dto.CreateNumberIdentifierDto;
import fr.istv.covivre.covivre.controller.dto.CreateTemporaryTokenDto;
import fr.istv.covivre.covivre.model.NumberIdentifier;
import fr.istv.covivre.covivre.model.TemporaryToken;
import fr.istv.covivre.covivre.repository.NumberIdentifierRepository;
import fr.istv.covivre.covivre.service.RsaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

@RestController
@RequestMapping("uuid")
public class UUIDController {

    @Autowired
    private NumberIdentifierRepository repository;

    @Autowired
    private RsaService rsaService;

    private String dateFormat = "yyyy.MM.dd HH:mm:ss";

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

    /**
     * @param input Take the uuid of the smartphone
     * @return TemporaryToken
     * Temporary token is sent to people met and store in their smartphone
     * Temporary token is crypted using RSA.
     * @throws Exception
     */
    @PostMapping("temporary-token")
    public TemporaryToken getTemporaryToken(@RequestBody CreateTemporaryTokenDto input) throws Exception {
        final int ONE_MINUTE_IN_MILLIS = 60000;
        final int NUMBER_OF_MINUTES = 20;

        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        Calendar calendar = Calendar.getInstance();

        String toEncrypt =
                input.getUuid() + // uuid of phone
                sdf.format(calendar.getTime()) + ";" + // date of the day
                sdf.format(new Date(calendar.getTimeInMillis() + (NUMBER_OF_MINUTES * ONE_MINUTE_IN_MILLIS)))  + ";" + // date of the day + 20 minutes
                rsaService.getIV(); // initialisation vector

        return TemporaryToken.builder().token(this.rsaService.encrypt(toEncrypt)).build();
    }

}
