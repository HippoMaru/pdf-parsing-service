package com.hippomaru.pdfParsingService.dao.impl;

import com.hippomaru.pdfParsingService.dao.PdfFileDetailsDao;
import com.hippomaru.pdfParsingService.entity.PdfFileDetails;
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
    public List<PdfFileDetails> searchByTitle(String title) {
        Query<PdfFileDetails> query = sessionFactory.getCurrentSession()
                .createQuery("FROM PdfFileDetails WHERE title LIKE :title", PdfFileDetails.class);
        query.setParameter("title", "%" + title + "%");
        return query.getResultList();
    }
}