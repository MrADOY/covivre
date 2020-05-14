package fr.istv.covivre.covivre.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

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
}
