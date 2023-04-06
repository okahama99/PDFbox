package example.pdfbox.services.serviceImpl;

import example.pdfbox.entities.*;
import example.pdfbox.services.ExportToPDFService;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.encryption.AccessPermission;
import org.apache.pdfbox.pdmodel.encryption.StandardProtectionPolicy;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

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

    private void drawTableForTwoDimensionArray(PDPage page, PDPageContentStream contentStream,
                                               int y,int margin,
                                               String[][] content) throws IOException{
        int rows = content.length;
        int cols = content[0].length;
        float rowHeight = 20.0f;
        float tableWidth = page.getMediaBox().getWidth() - 2.0f * margin;
        float tableHeight = rowHeight * (float) rows;
        float colWidth = tableWidth / (float) cols;

        //draw the rows
        float nexty = y ;
        for (int i = 0; i <= rows; i++) {
            contentStream.moveTo(margin, nexty);
            contentStream.lineTo(margin + tableWidth, nexty);
            contentStream.stroke();
            nexty-= rowHeight;
        }

        //draw the columns
        float nextx = margin;
        for (int i = 0; i <= cols; i++) {
            contentStream.moveTo(nextx, y);
            contentStream.lineTo(nextx, y - tableHeight);
            contentStream.stroke();
            nextx += colWidth;
        }

        //now add the text
        contentStream.setFont(PDType1Font.HELVETICA, 12.0f);

        final float cellMargin = 5.0f;
        float textx = margin + cellMargin;
        float texty = y - 15.0f;
        for (final String[] aContent : content) {
            for (String text : aContent) {
                contentStream.beginText();
                contentStream.newLineAtOffset(textx, texty);
                contentStream.showText(text);
                contentStream.endText();
                textx += colWidth;
            }
            texty -= rowHeight;
            textx = margin + cellMargin;
        }
    }

    @Override
    public void finalPDF() throws IOException {
//        File file = new File("pdfBoxHelloWorld.pdf");
//        PDDocument document = PDDocument.load(file);
        PDDocument document = new PDDocument();
        PDPage page = new PDPage();
        document.addPage(page);

        PDPageContentStream contentStream =
                new PDPageContentStream(document, page);

        User recipient = new User();
        recipient.setAddress("QUEBECOISE");
        recipient.setName("MARIE-CHRISTINE");
        recipient.setRole("Recipient");
        recipient.setUserId(1L);

        User shipper = new User();
        shipper.setAddress("CANADA");
        shipper.setName("RECEPTION");
        shipper.setRole("Shipper");
        shipper.setUserId(2L);

        ShippingInformation shippingInfo = new ShippingInformation();
        shippingInfo.setShippingId(1L);
        shippingInfo.setShipper(shipper);
        shippingInfo.setRecipient(recipient);

        DeliveryInformation deliveryInfo = new DeliveryInformation();
        deliveryInfo.setDeliveryId(1L);
        deliveryInfo.setActivity("Delivery");
        deliveryInfo.setDateTime(Instant.now());
        deliveryInfo.setLocation("YUTA");
        deliveryInfo.setFedExEmp("Automated");
        deliveryInfo.setFedExEmployee("Felipe, L");

        RecipientInformation recipientInfo = new RecipientInformation();
        recipientInfo.setRecipientId(1L);
        recipientInfo.setSignatureImage("image/FedEx.jpg");
        recipientInfo.setRecipientName("M.NATALIE");

        Corporation corporation = new Corporation();
        corporation.setCorporationId(1L);
        corporation.setName("Federal Express Canada Ltd.");
        corporation.setAddress("5985 Explorer Drive");
        corporation.setHeadquarter("Mississauga");

        Report report = new Report();
        report.setReportDate(Instant.now());
        report.setReportId(1L);
        report.setShippingInfo(shippingInfo);
        report.setRecipientInfo(recipientInfo);
        report.setCorporation(corporation);
        report.setDeliveryInfo(deliveryInfo);


        String[][] reportContent = {
                {"ID","Report Date"},
                {report.getReportId().toString(), report.getReportDate().toString()}
        };

        String[][] shipperContent = {
                {"Shipping ID", "Name", "Address"},
                {shippingInfo.getShippingId().toString(), shipper.getName(), shipper.getAddress()}
        };

        String[][] recipientContent = {
                {"Shipping ID", "Name", "Address"},
                {shippingInfo.getShippingId().toString(), recipient.getName(), recipient.getAddress()}
        };

        String[][] deliveryContent = {
                {"ID", "Activity", "Location", "FedEx Emp#", "FedEx Employee"},
                {deliveryInfo.getDeliveryId().toString(), deliveryInfo.getActivity(),
                        deliveryInfo.getLocation(), deliveryInfo.getFedExEmp(), deliveryInfo.getFedExEmployee()}
        };

        // FedEx logo
        Path path = Paths.get("image/FedEx.jpg");
        PDImageXObject image
                = PDImageXObject.createFromFile(path.toAbsolutePath().toString(), document);
        contentStream.drawXObject(image, 10, 650, 200, 150);

        // Corporation top right information
        contentStream.setFont(PDType1Font.HELVETICA_BOLD_OBLIQUE, 15);
        contentStream.beginText();
        contentStream.setLeading(14.5f);
        contentStream.newLineAtOffset(350,750);
        contentStream.showText(corporation.getName());
        contentStream.newLine();
        contentStream.showText(corporation.getAddress());
        contentStream.newLine();
        contentStream.showText(corporation.getHeadquarter());
        contentStream.endText();

        // Report information
        contentStream.setFont(PDType1Font.TIMES_BOLD, 24);
        contentStream.beginText();
        contentStream.setLeading(14.5f);
        contentStream.newLineAtOffset(200,620);
        contentStream.showText("Report information:");
        contentStream.endText();

        drawTableForTwoDimensionArray(page, contentStream,600,50, reportContent);

        // Shipper information
        contentStream.setFont(PDType1Font.TIMES_BOLD, 24);
        contentStream.beginText();
        contentStream.setLeading(14.5f);
        contentStream.newLineAtOffset(200,520);
        contentStream.showText("Shipper information:");
        contentStream.endText();

        drawTableForTwoDimensionArray(page, contentStream,500,50, shipperContent);

        contentStream.setFont(PDType1Font.TIMES_BOLD, 24);
        contentStream.beginText();
        contentStream.setLeading(14.5f);
        contentStream.newLineAtOffset(200,420);
        contentStream.showText("Recipient information:");
        contentStream.endText();

        drawTableForTwoDimensionArray(page, contentStream,400,50, recipientContent);

        contentStream.setFont(PDType1Font.TIMES_BOLD, 24);
        contentStream.beginText();
        contentStream.setLeading(14.5f);
        contentStream.newLineAtOffset(200,320);
        contentStream.showText("Delivery information:");
        contentStream.endText();

        drawTableForTwoDimensionArray(page, contentStream,300,50, deliveryContent);

        // Draw a line
        contentStream.setLineWidth(2.5f);
        contentStream.setStrokingColor(Color.GRAY);
        contentStream.moveTo(10, 100);
        contentStream.lineTo(900, 100);
        contentStream.stroke();

        contentStream.setFont(PDType1Font.TIMES_BOLD, 12);
        contentStream.beginText();
        contentStream.setLeading(14.5f);
        contentStream.newLineAtOffset(30,85);
        contentStream.showText("Thank you for choosing FedEx Express");
        contentStream.newLine();
        contentStream.showText("TFederal Express Canada Ltd.");
        contentStream.showText("1.800.GoFedEx 1.800.463.3339");
        contentStream.newLine();
        contentStream.showText("Federal Express Canada Ltd. Proprietary and Confidential. The above information may not be disclosed to any");
        contentStream.newLine();
        contentStream.showText("person or party outside of DEPT NATIONAL DEFENCE without the express written consent of Federal Express");
        contentStream.newLine();
        contentStream.showText("Canada Ltd");
        contentStream.endText();

        contentStream.close();
        document.save("pdfBoxHelloWorld.pdf");
        document.close();
    }

    @Override
    public void createPDFWithInputtedPage(int numberOfPage) throws IOException {
        PDDocument document = new PDDocument();

        User recipient = new User();
        recipient.setAddress("QUEBECOISE");
        recipient.setName("MARIE-CHRISTINE");
        recipient.setRole("Recipient");
        recipient.setUserId(1L);

        User shipper = new User();
        shipper.setAddress("CANADA");
        shipper.setName("RECEPTION");
        shipper.setRole("Shipper");
        shipper.setUserId(2L);

        ShippingInformation shippingInfo = new ShippingInformation();
        shippingInfo.setShippingId(1L);
        shippingInfo.setShipper(shipper);
        shippingInfo.setRecipient(recipient);

        DeliveryInformation deliveryInfo = new DeliveryInformation();
        deliveryInfo.setDeliveryId(1L);
        deliveryInfo.setActivity("Delivery");
        deliveryInfo.setDateTime(Instant.now());
        deliveryInfo.setLocation("YUTA");
        deliveryInfo.setFedExEmp("Automated");
        deliveryInfo.setFedExEmployee("Felipe, L");

        RecipientInformation recipientInfo = new RecipientInformation();
        recipientInfo.setRecipientId(1L);
        recipientInfo.setSignatureImage("image/FedEx.jpg");
        recipientInfo.setRecipientName("M.NATALIE");

        Corporation corporation = new Corporation();
        corporation.setCorporationId(1L);
        corporation.setName("Federal Express Canada Ltd.");
        corporation.setAddress("5985 Explorer Drive");
        corporation.setHeadquarter("Mississauga");

        Report report = new Report();
        report.setReportDate(Instant.now());
        report.setReportId(1L);
        report.setShippingInfo(shippingInfo);
        report.setRecipientInfo(recipientInfo);
        report.setCorporation(corporation);
        report.setDeliveryInfo(deliveryInfo);


        String[][] reportContent = {
                {"ID","Report Date"},
                {report.getReportId().toString(), report.getReportDate().toString()}
        };

        String[][] shipperContent = {
                {"Shipping ID", "Name", "Address"},
                {shippingInfo.getShippingId().toString(), shipper.getName(), shipper.getAddress()}
        };

        String[][] recipientContent = {
                {"Shipping ID", "Name", "Address"},
                {shippingInfo.getShippingId().toString(), recipient.getName(), recipient.getAddress()}
        };

        String[][] deliveryContent = {
                {"ID", "Activity", "Location", "FedEx Emp#", "FedEx Employee"},
                {deliveryInfo.getDeliveryId().toString(), deliveryInfo.getActivity(),
                        deliveryInfo.getLocation(), deliveryInfo.getFedExEmp(), deliveryInfo.getFedExEmployee()}
        };

        for (int i = 0; i < numberOfPage; i++) {
            PDPage page = new PDPage();
            document.addPage(page);

            PDPageContentStream contentStream =
                    new PDPageContentStream(document, page);

            // FedEx logo
            Path path = Paths.get("image/FedEx.jpg");
            PDImageXObject image
                    = PDImageXObject.createFromFile(path.toAbsolutePath().toString(), document);
            contentStream.drawXObject(image, 10, 650, 200, 150);

            // Corporation top right information
            contentStream.setFont(PDType1Font.HELVETICA_BOLD_OBLIQUE, 15);
            contentStream.beginText();
            contentStream.setLeading(14.5f);
            contentStream.newLineAtOffset(350,750);
            contentStream.showText(corporation.getName());
            contentStream.newLine();
            contentStream.showText(corporation.getAddress());
            contentStream.newLine();
            contentStream.showText(corporation.getHeadquarter());
            contentStream.endText();

            // Report information
            contentStream.setFont(PDType1Font.TIMES_BOLD, 24);
            contentStream.beginText();
            contentStream.setLeading(14.5f);
            contentStream.newLineAtOffset(200,620);
            contentStream.showText("Report information:");
            contentStream.endText();

            drawTableForTwoDimensionArray(page, contentStream,600,50, reportContent);

            // Shipper information
            contentStream.setFont(PDType1Font.TIMES_BOLD, 24);
            contentStream.beginText();
            contentStream.setLeading(14.5f);
            contentStream.newLineAtOffset(200,520);
            contentStream.showText("Shipper information:");
            contentStream.endText();

            drawTableForTwoDimensionArray(page, contentStream,500,50, shipperContent);

            contentStream.setFont(PDType1Font.TIMES_BOLD, 24);
            contentStream.beginText();
            contentStream.setLeading(14.5f);
            contentStream.newLineAtOffset(200,420);
            contentStream.showText("Recipient information:");
            contentStream.endText();

            drawTableForTwoDimensionArray(page, contentStream,400,50, recipientContent);

            contentStream.setFont(PDType1Font.TIMES_BOLD, 24);
            contentStream.beginText();
            contentStream.setLeading(14.5f);
            contentStream.newLineAtOffset(200,320);
            contentStream.showText("Delivery information:");
            contentStream.endText();

            drawTableForTwoDimensionArray(page, contentStream,300,50, deliveryContent);

            // Draw a line
            contentStream.setLineWidth(2.5f);
            contentStream.setStrokingColor(Color.GRAY);
            contentStream.moveTo(10, 100);
            contentStream.lineTo(900, 100);
            contentStream.stroke();

            contentStream.setFont(PDType1Font.TIMES_BOLD, 12);
            contentStream.beginText();
            contentStream.setLeading(14.5f);
            contentStream.newLineAtOffset(30,85);
            contentStream.showText("Thank you for choosing FedEx Express");
            contentStream.newLine();
            contentStream.showText("TFederal Express Canada Ltd.");
            contentStream.showText("1.800.GoFedEx 1.800.463.3339");
            contentStream.newLine();
            contentStream.showText("Federal Express Canada Ltd. Proprietary and Confidential. The above information may not be disclosed to any");
            contentStream.newLine();
            contentStream.showText("person or party outside of DEPT NATIONAL DEFENCE without the express written consent of Federal Express");
            contentStream.newLine();
            contentStream.showText("Canada Ltd");
            contentStream.endText();

            contentStream.close();
            document.save("multiplePagePDF.pdf");
        }
        document.close();
    }
}
