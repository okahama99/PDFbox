package example.pdfbox.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
public class Report {
    private Long reportId;
    private Instant reportDate;

    private ShippingInformation shippingInfo;
    private DeliveryInformation deliveryInfo;
    private RecipientInformation recipientInfo;
    private Corporation corporation;
}
