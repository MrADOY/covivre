package fr.istv.covivre.covivre.controller;

import fr.istv.covivre.covivre.controller.dto.AlertUserInputDto;
import fr.istv.covivre.covivre.model.Contents;
import fr.istv.covivre.covivre.model.NumberIdentifier;
import fr.istv.covivre.covivre.model.OneSignalData;
import fr.istv.covivre.covivre.repository.NumberIdentifierRepository;
import fr.istv.covivre.covivre.service.RsaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("users")
public class UserController {

    @Autowired
    private RsaService rsaService;

    private String dateFormat = "yyyy.MM.dd HH:mm:ss";

    @Value("${onesignal.app_id}")
    private String oneSignalAppId;

    @Value("${onesignal.authorization}")
    private String authorization;


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
                .map(decryptedToken -> decryptedToken.split(";")[0])
                .distinct().collect(Collectors.toList());

        final String uri = "https://onesignal.com/api/v1/notifications";

        OneSignalData oneSignalData = OneSignalData.builder()
                .app_id(oneSignalAppId)
                .contents(Contents.builder().en("Vous avez rencontré recemment une personne testée positive").build())
                .headings(Contents.builder().en("Covivre").build())
                .include_external_user_ids(tokensToNotify.toArray(new String[tokensToNotify.size()]))
                .build();

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", authorization);
        headers.set("Content-Type", "application/json");

        HttpEntity<OneSignalData> request = new HttpEntity<>(oneSignalData, headers);
        restTemplate.postForEntity(uri, request,  OneSignalData.class);


        return tokensToNotify;
    }


}
