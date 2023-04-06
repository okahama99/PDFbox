package example.pdfbox;

import example.pdfbox.services.ExportToPDFService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class TestMethod {
    @Autowired
    private ExportToPDFService exportToPDFService;

    @Test
    public void testPDF() throws IOException, URISyntaxException, ClassNotFoundException {
        exportToPDFService.createTable();
        List<String> stringList = new ArrayList<>();
        stringList.add("test1");
        stringList.add("test2");
        exportToPDFService.insertText(stringList);
        exportToPDFService.insertImage();
//        exportToPDFService.fileEncryption();
    }

    @Test
    public void finalPDF() throws IOException {
        exportToPDFService.finalPDF();
    }

    @Test
    public void createMultiplePDF() throws IOException {
        int numberOfPage = 3;
        exportToPDFService.createPDFWithInputtedPage(numberOfPage);
    }
}
