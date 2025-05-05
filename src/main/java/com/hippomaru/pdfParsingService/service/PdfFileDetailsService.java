package com.hippomaru.pdfParsingService.service;

import com.hippomaru.pdfParsingService.entity.PdfFileDetails;

import java.util.List;

public interface PdfFileDetailsService {
    void saveFile(PdfFileDetails file);
    List<PdfFileDetails> searchFilesByDocumentName(String title);
    PdfFileDetails searchFilesById(int id);
    PdfFileDetails deleteFilesById(int id);
    PdfFileDetails updateFile(int id, PdfFileDetails newFileDetails);
}
