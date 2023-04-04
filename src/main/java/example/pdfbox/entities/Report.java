package example.pdfbox.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class Report {
    private Long reportId;
    private Date reportDate;

    private ShippingInformation shippingInfo;
    private DeliveryInformation deliveryInfo;
    private RecipientInformation recipientInfo;
    private Corporation corporation;
}
