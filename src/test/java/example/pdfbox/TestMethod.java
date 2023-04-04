package example.pdfbox;

import example.pdfbox.services.ExportToPDFService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.net.URISyntaxException;

@SpringBootTest
public class TestMethod {
    @Autowired
    private ExportToPDFService exportToPDFService;

    @Test
    public void createPDF() throws IOException, URISyntaxException {
        exportToPDFService.insertText();
        exportToPDFService.insertImage();
        exportToPDFService.fileEncryption();
    }
}
