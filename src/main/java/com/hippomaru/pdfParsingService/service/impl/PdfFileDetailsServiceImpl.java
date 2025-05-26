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
        var temp = new Date();
        file.setUploadDate((Date) temp.clone());
        file.setUpdateDate((Date) temp.clone());
        pdfFileDetailsDao.save(file);
    }

    @Override
    public PdfFileDetails updateFile(int id, PdfFileDetails newFileDetails) {
        PdfFileDetails oldFileDetails = searchFilesById(id);
        newFileDetails.setDocumentId(oldFileDetails.getDocumentId());
        newFileDetails.setUploadDate(oldFileDetails.getUploadDate());
        newFileDetails.setUpdateDate(new Date());
        pdfFileDetailsDao.save(newFileDetails);
        return newFileDetails;
    }

    @Override
    public List<PdfFileDetails> searchFilesByDocumentName(String documentName) {
        return pdfFileDetailsDao.searchByDocumentName(documentName);
    }

    @Override
    public PdfFileDetails searchFilesById(int id) { return pdfFileDetailsDao.searchById(id);}

    @Override
    public PdfFileDetails deleteFilesById(int id) { return pdfFileDetailsDao.deleteById(id);}
}