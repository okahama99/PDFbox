import example.pdfbox.PdFboxApplication;
import example.pdfbox.services.ExportToPDFService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.net.URISyntaxException;

@SpringBootTest
@AutoConfigureMockMvc
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
