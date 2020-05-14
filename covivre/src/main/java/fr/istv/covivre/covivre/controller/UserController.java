package fr.istv.covivre.covivre.controller;

import fr.istv.covivre.covivre.controller.dto.AlertUserInputDto;
import fr.istv.covivre.covivre.model.NumberIdentifier;
import fr.istv.covivre.covivre.repository.NumberIdentifierRepository;
import fr.istv.covivre.covivre.service.RsaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("users")
public class UserController {

    @Autowired
    private RsaService rsaService;

    private String dateFormat = "yyyy.MM.dd HH:mm:ss";

    @PostMapping("alert-users")
    public List<String> alertUsers(@RequestBody AlertUserInputDto input) {
        List<String> tokensToNotify = Arrays.stream(input.getUuids()).map(i -> {
            try {
                return this.rsaService.decrypt(i);
            } catch (Exception e) {
                return null;
            }
        })
        .filter(decryptedToken -> decryptedToken != null)
                .map(decryptedToken -> decryptedToken.split(";")[0]).collect(Collectors.toList());

        return tokensToNotify;
    }


}
