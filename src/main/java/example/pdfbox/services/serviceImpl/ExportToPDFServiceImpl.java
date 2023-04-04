package example.pdfbox.services.serviceImpl;

import example.pdfbox.services.ExportToPDFService;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.encryption.AccessPermission;
import org.apache.pdfbox.pdmodel.encryption.StandardProtectionPolicy;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class ExportToPDFServiceImpl implements ExportToPDFService {

    @Override
    public void insertText() throws IOException {
        PDDocument document = new PDDocument();
        PDPage page = new PDPage();
        document.addPage(page);

        PDPageContentStream contentStream = new PDPageContentStream(document, page);

        contentStream.setFont(PDType1Font.COURIER, 12);
        contentStream.beginText();
        contentStream.showText("Hello World");
        contentStream.endText();
        contentStream.close();

        document.save("pdfBoxHelloWorld.pdf");
        document.close();
    }

    @Override
    public void insertImage() throws IOException, URISyntaxException {
        PDDocument document = new PDDocument();
        PDPage page = new PDPage();
        document.addPage(page);

        Path path = Paths.get(ClassLoader.getSystemResource("Java_logo.png").toURI());
        PDPageContentStream contentStream = new PDPageContentStream(document, page);
        PDImageXObject image
                = PDImageXObject.createFromFile(path.toAbsolutePath().toString(), document);
        contentStream.drawImage(image, 0, 0);
        contentStream.close();

        document.save("pdfBoxImage.pdf");
        document.close();
    }

    @Override
    public void fileEncryption() throws IOException {
        PDDocument document = new PDDocument();
        PDPage page = new PDPage();
        document.addPage(page);

        AccessPermission accessPermission = new AccessPermission();
        accessPermission.setCanPrint(false);
        accessPermission.setCanModify(false);

        StandardProtectionPolicy standardProtectionPolicy
                = new StandardProtectionPolicy("ownerpass", "userpass", accessPermission);
        document.protect(standardProtectionPolicy);
        document.save("pdfBoxEncryption.pdf");
        document.close();
    }
}
