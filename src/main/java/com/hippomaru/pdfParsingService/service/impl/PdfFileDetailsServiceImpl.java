package com.hippomaru.pdfParsingService.service.impl;

import com.hippomaru.pdfParsingService.dao.PdfFileDetailsDao;
import com.hippomaru.pdfParsingService.entity.PdfFileDetails;
import com.hippomaru.pdfParsingService.service.PdfFileDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class PdfFileDetailsServiceImpl implements PdfFileDetailsService {

    private final PdfFileDetailsDao pdfFileDetailsDao;

    @Autowired
    public PdfFileDetailsServiceImpl(PdfFileDetailsDao pdfFileDetailsDao) {
        this.pdfFileDetailsDao = pdfFileDetailsDao;
    }

    @Override
    public void saveFile(PdfFileDetails file) {
        file.setCreationDate(new Date());
        pdfFileDetailsDao.save(file);
    }

    @Override
    public List<PdfFileDetails> searchFilesByTitle(String title) {
        return pdfFileDetailsDao.searchByTitle(title);
    }
}