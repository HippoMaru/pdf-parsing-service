package com.hippomaru.pdfParsingService.dao.impl;

import com.hippomaru.pdfParsingService.dao.PdfFileDetailsDao;
import com.hippomaru.pdfParsingService.entity.PdfFileDetails;
import com.hippomaru.pdfParsingService.exception.NoSuchPdfFileException;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PdfFileDetailsDaoImpl implements PdfFileDetailsDao {

    private final SessionFactory sessionFactory;

    @Autowired
    public PdfFileDetailsDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void save(PdfFileDetails file) {
        sessionFactory.getCurrentSession().save(file);
    }

    @Override
    public List<PdfFileDetails> searchByDocumentName(String documentName) {
        Query<PdfFileDetails> query = sessionFactory.getCurrentSession()
                .createQuery("FROM PdfFileDetails WHERE documentName = :documentName", PdfFileDetails.class);
        query.setParameter("documentName", documentName);
        return query.getResultList();
    }

    @Override
    public PdfFileDetails searchById(int id){
        Query<PdfFileDetails> query = sessionFactory.getCurrentSession()
                .createQuery("FROM PdfFileDetails WHERE documentId = :id", PdfFileDetails.class);
        query.setParameter("id", id);
        return query.getResultList()
                .stream()
                .findFirst()
                .orElseThrow(() -> new NoSuchPdfFileException(
                        "PDF file not found with id: " + id
                ));
    }

    @Override
    public PdfFileDetails  deleteById(int id){
        PdfFileDetails result = searchById(id);
        sessionFactory.getCurrentSession()
                .createQuery("DELETE FROM PdfFileDetails WHERE documentId = :id")
                .setParameter("id", id)
                .executeUpdate();
        return result;
    }
}