package example.pdfbox.services;

import java.io.IOException;
import java.net.URISyntaxException;

public interface ExportToPDFService {
    void insertText() throws IOException;

    void insertImage() throws IOException, URISyntaxException;

    void fileEncryption() throws IOException;

}
