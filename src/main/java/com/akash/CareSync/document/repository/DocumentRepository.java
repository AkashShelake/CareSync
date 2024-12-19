package com.akash.CareSync.document.repository;

import com.akash.CareSync.document.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {
    List<Document> findByPatientIdAndStatus(Long patientId, String status);
    List<Document> findBySubject(String subject);
}
