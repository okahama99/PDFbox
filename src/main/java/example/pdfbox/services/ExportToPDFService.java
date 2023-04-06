package example.pdfbox.services;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public interface ExportToPDFService {
    void insertText(List<String> list) throws IOException;

    void insertImage() throws IOException, URISyntaxException, ClassNotFoundException;

    void fileEncryption() throws IOException;

    void createTable() throws IOException;

    void finalPDF() throws IOException;

    void createPDFWithInputtedPage(int numberOfPage) throws IOException;

}
