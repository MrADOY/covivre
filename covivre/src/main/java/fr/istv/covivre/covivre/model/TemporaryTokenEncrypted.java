package fr.istv.covivre.covivre.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TemporaryTokenEncrypted {
    private String token;
}
