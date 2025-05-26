package com.hippomaru.pdfParsingService.dao;

import com.hippomaru.pdfParsingService.entity.PdfFileDetails;

import java.util.List;

public interface PdfFileDetailsDao {
    void save(PdfFileDetails file);
    List<PdfFileDetails> searchByDocumentName(String title);
    PdfFileDetails searchById(int id);
    PdfFileDetails  deleteById(int id);
}