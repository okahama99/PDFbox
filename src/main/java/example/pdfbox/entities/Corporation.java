package example.pdfbox.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class Corporation {
    private Long corporationId;
    private String name;
    private String address;
    private String headquarter;
}
