package fr.istv.covivre.covivre.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OneSignalData {
    private String app_id;
    private Contents contents;
    private Contents headings;
    private String[] include_external_user_ids;
}
