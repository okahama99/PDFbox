package example.pdfbox.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RecipientInformation {
    private Long recipientId;
    private String recipientName;
    private String signatureImage;
}
