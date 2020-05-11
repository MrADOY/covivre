package fr.istv.covivre.covivre.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Pattern;

@Entity
@Data
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NumberIdentifier {
    @Id
    private String uuid;
    @Pattern(regexp="(^$|[0-9]{10})")
    private String phoneNumber;
}
