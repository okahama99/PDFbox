package example.pdfbox.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
public class DeliveryInformation {
    private Long deliveryId;
    private Instant dateTime;
    private String activity;
    private String location;
    private String fedExEmp;
    private String fedExEmployee;
}
