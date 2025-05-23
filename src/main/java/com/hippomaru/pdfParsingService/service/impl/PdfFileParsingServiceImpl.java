package com.hippomaru.pdfParsingService.service.impl;

import com.hippomaru.pdfParsingService.entity.PdfFileDetails;
import com.hippomaru.pdfParsingService.service.PdfFileParsingService;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.io.RandomAccessRead;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

@Service
public class PdfFileParsingServiceImpl implements PdfFileParsingService {

    private static final int MIN_WORD_LENGTH = 5;
    private static final int MAX_KEYWORDS = 7;

    private final Tesseract tesseract;

    public PdfFileParsingServiceImpl() {
        this.tesseract = new Tesseract();

        tesseract.setDatapath("C:\\Program Files\\Tesseract-OCR\\tessdata"); // Тут подставить своё
        tesseract.setLanguage("rus+eng"); // Языки распознавания
        tesseract.setPageSegMode(1); // Автоматическое определение страницы
        tesseract.setOcrEngineMode(3); // Режим движка LSTM
    }

    @Override
    public PdfFileDetails parsePdfFile(MultipartFile file) {
        try (InputStream inputStream = file.getInputStream();
             PDDocument document = Loader.loadPDF(inputStream.readAllBytes())) {

            PdfFileDetails details = new PdfFileDetails();
            setBasicFileInfo(file, details);
            extractMetadata(document, details);
            extractTextWithOCR(document, details);

            return details;

        } catch (IOException | TesseractException e) {
            throw new RuntimeException("Error processing PDF file", e);
        }
    }

    private void setBasicFileInfo(MultipartFile file, PdfFileDetails details) {
        details.setDocumentName(file.getOriginalFilename());
    }

    private void extractMetadata(PDDocument document, PdfFileDetails details) {
        PDDocumentInformation info = document.getDocumentInformation();
        details.setAuthor(info.getAuthor());
    }

    private void extractTextWithOCR(PDDocument document, PdfFileDetails details)
            throws IOException, TesseractException {

        PDFRenderer renderer = new PDFRenderer(document);
        StringBuilder recognizedText = new StringBuilder();

        for (int page = 0; page < document.getNumberOfPages(); page++) {
            BufferedImage image = renderer.renderImageWithDPI(page, 300);
            image = preprocessImage(image);
            String pageText = tesseract.doOCR(image);
            recognizedText.append(pageText).append("\n\n");
        }

        details.setRecognizedText(recognizedText.toString());
    }

    private BufferedImage preprocessImage(BufferedImage image) {

        BufferedImage processedImage = new BufferedImage(
                image.getWidth(),
                image.getHeight(),
                BufferedImage.TYPE_BYTE_GRAY
        );
        processedImage.getGraphics().drawImage(image, 0, 0, null);
        return processedImage;
    }
}