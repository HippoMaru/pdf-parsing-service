package com.hippomaru.pdfParsingService.controller;
import com.hippomaru.pdfParsingService.entity.PdfFileDetails;
import com.hippomaru.pdfParsingService.service.PdfFileDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pdfFiles")
public class PdfFileController {

    private final PdfFileDetailsService fileService;

    @Autowired
    public PdfFileController(PdfFileDetailsService fileService) {
        this.fileService = fileService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void uploadFile(@RequestBody PdfFileDetails fileDetails) {
        fileService.saveFile(fileDetails);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<PdfFileDetails> searchFiles(@RequestParam String title) {
        return fileService.searchFilesByTitle(title);
    }
}