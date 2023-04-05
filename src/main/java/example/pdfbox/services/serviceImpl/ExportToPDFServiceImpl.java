package example.pdfbox.services.serviceImpl;

import example.pdfbox.entities.Report;
import example.pdfbox.services.ExportToPDFService;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.encryption.AccessPermission;
import org.apache.pdfbox.pdmodel.encryption.StandardProtectionPolicy;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.springframework.stereotype.Service;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ExportToPDFServiceImpl implements ExportToPDFService {

    @Override
    public void createTable() throws IOException {
        PDDocument doc = new PDDocument();
        PDPage page = new PDPage();
        doc.addPage( page );

        PDPageContentStream contentStream =
                new PDPageContentStream(doc, page);

        List<List<Report>> reportList = new ArrayList<>();

        Report report = new Report();
        report.setReportId(1L);
        report.setReportDate(Instant.now());
        Report report1 = new Report();
        report1.setReportId(1L);
        report1.setReportDate(Instant.now());
        List<Report> reports = new ArrayList<>();
        reports.add(report);
        reports.add(report1);
        List<Report> reports2 = new ArrayList<>();
        reports2.add(report);
        reports2.add(report1);
        reportList.add(reports);
        reportList.add(reports2);

//        String[][] content = {{"a","b", "1"},
//                {"c","d", "2"},
//                {"e","f", "3"},
//                {"g","h", "4"},
//                {"i","j", "5"}} ;

        drawTable(page, contentStream, 700, 100, reportList);
        contentStream.close();
        doc.save("pdfBoxHelloWorld.pdf" );
    }

    private void drawTable(PDPage page, PDPageContentStream contentStream,
                                 float y, float margin,
                                 List<List<Report>> content) throws IOException {
        final int rows = content.size();
        final int cols = content.size();
        final float rowHeight = 20f;
        final float tableWidth = page.getMediaBox().getWidth() - margin - margin;
        final float tableHeight = rowHeight * rows;
        final float colWidth = tableWidth / (float) cols;
        final float cellMargin = 5f;

        //draw the rows
        float nexty = y;
        for (int i = 0; i <= rows; i++) {
            contentStream.drawLine(margin, nexty, margin + tableWidth, nexty);
            nexty -= rowHeight;
        }

        //draw the columns
        float nextx = margin;
        for (int i = 0; i <= cols; i++) {
            contentStream.drawLine(nextx, y, nextx, y - tableHeight);
            nextx += colWidth;
        }

        //now add the text
        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
        contentStream.beginText();
//        contentStream.setLeading(14.5f);
        contentStream.newLineAtOffset(250,725);
        contentStream.showText("Test text in table page");
        contentStream.endText();

        float textx = margin + cellMargin;
        float texty = y - 15;
        for (int i = 0; i < content.size(); i++) {
            for (int j = 0; j < content.get(i).size(); j++) {
                contentStream.beginText();
                contentStream.moveTextPositionByAmount(textx, texty);
                contentStream.drawString(content.get(i).get(j).getReportId().toString());
                contentStream.newLine();
                contentStream.drawString(content.get(i).get(j).getReportDate().toString());
                contentStream.endText();
                textx += colWidth;
            }
            texty -= rowHeight;
            textx = margin + cellMargin;
        }
    }

    @Override
    public void insertText(List<String> list) throws IOException {
        File file = new File("pdfBoxHelloWorld.pdf");
        PDDocument document = PDDocument.load(file);
        PDPage page = new PDPage();
        document.addPage(page);

        PDPageContentStream contentStream = new PDPageContentStream(document, page);

        contentStream.setFont(PDType1Font.TIMES_ROMAN, 24);
        contentStream.beginText();
        contentStream.setLeading(14.5f);
        contentStream.newLineAtOffset(25,725);
        for (int i = 0; i< list.size(); i++){
            contentStream.showText(list.get(i));
            contentStream.newLine();
            contentStream.newLine();
        }
        contentStream.endText();
        contentStream.close();

        document.save("pdfBoxHelloWorld.pdf");
        document.close();
    }

    @Override
    public void insertImage() throws IOException, URISyntaxException, ClassNotFoundException {
        File file = new File("pdfBoxHelloWorld.pdf");
        PDDocument document = PDDocument.load(file);
        PDPage page = new PDPage();
        document.addPage(page);

//        Class implement = Class.forName("example.pdfbox.services.serviceImpl.ExportToPDFServiceImpl");
//
//        ClassLoader cLoader = implement.getClassLoader();

        Path path = Paths.get("image/FedEx.jpg");
        PDPageContentStream contentStream = new PDPageContentStream(document, page);
        PDImageXObject image
                = PDImageXObject.createFromFile(path.toAbsolutePath().toString(), document);
        contentStream.drawXObject(image, 10, 700, 100, 100);
        contentStream.close();

        document.save("pdfBoxHelloWorld.pdf");
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
