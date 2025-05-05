package com.hippomaru.pdfParsingService.service;

import com.hippomaru.pdfParsingService.entity.PdfFileDetails;
import org.springframework.web.multipart.MultipartFile;

public interface PdfFileParsingService {
    PdfFileDetails parsePdfFile(MultipartFile file);
}
