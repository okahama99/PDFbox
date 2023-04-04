package example.pdfbox.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ShippingInformation {
    private Long shippingId;
    private User recipient;
    private User shipper;
}
