package fr.istv.covivre.covivre.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateTemporaryTokenDto implements Serializable {
    @JsonProperty("uuid")
        private String uuid;
}
