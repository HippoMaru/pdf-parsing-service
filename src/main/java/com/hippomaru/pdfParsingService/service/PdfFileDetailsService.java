package com.hippomaru.pdfParsingService.service;

import com.hippomaru.pdfParsingService.entity.PdfFileDetails;

import java.util.List;

public interface PdfFileDetailsService {
    void saveFile(PdfFileDetails file);
    List<PdfFileDetails> searchFilesByTitle(String title);
}
