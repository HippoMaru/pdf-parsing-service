package com.hippomaru.pdfParsingService.controller;
import com.hippomaru.pdfParsingService.entity.PdfFileDetails;
import com.hippomaru.pdfParsingService.exception.InvalidPdfFileProvidedException;
import com.hippomaru.pdfParsingService.service.PdfFileDetailsService;
import com.hippomaru.pdfParsingService.service.PdfFileParsingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/pdfFiles")
public class PdfFileController {

    private final PdfFileDetailsService fileService;
    private final PdfFileParsingService parsingService;

    @Autowired
    public PdfFileController(PdfFileDetailsService fileService, PdfFileParsingService parsingService) {
        this.fileService = fileService;
        this.parsingService = parsingService;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void uploadFile(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            throw new InvalidPdfFileProvidedException("File is empty");
        }

        if (!"application/pdf".equals(file.getContentType())) {
            throw new InvalidPdfFileProvidedException("File is not PDF");
        }

        PdfFileDetails parsedFileDetails = parsingService.parsePdfFile(file);
        fileService.saveFile(parsedFileDetails);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<PdfFileDetails> searchFilesByDocumentName(@RequestParam String documentName) {
        return fileService.searchFilesByDocumentName(documentName);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping("/{id}")
    public PdfFileDetails searchFilesById(@PathVariable Integer id) {
        return fileService.searchFilesById(id);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping("/{id}")
    public PdfFileDetails deleteFilesById(@PathVariable Integer id) {
        return fileService.deleteFilesById(id);
    }

    @PatchMapping
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping("/{id}")
    public PdfFileDetails updateFilesById(@PathVariable Integer id, @RequestBody PdfFileDetails fileDetails) {
        return fileService.updateFile(id, fileDetails);
    }
}