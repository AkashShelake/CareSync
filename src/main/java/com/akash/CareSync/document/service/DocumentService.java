package com.akash.CareSync.document.service;

import com.akash.CareSync.document.entity.Document;
import com.akash.CareSync.document.repository.DocumentRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class DocumentService {

    private final DocumentRepository documentRepository;

    public DocumentService(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }

    public Document uploadFile(Long patientId, MultipartFile file, String subject) throws IOException {
        Document document = new Document();
        document.setPatientId(patientId);
        document.setFileName(file.getOriginalFilename());
        document.setFileType(file.getContentType());
        document.setFileData(file.getBytes());
        document.setStatus("ACTIVE");
        document.setSubject(subject);
        return documentRepository.save(document);
    }

    public Optional<Document> downloadFile(Long documentId) {
        return documentRepository.findById(documentId);
    }

    public List<Document> getActiveFilesForPatient(Long patientId) {
        return documentRepository.findByPatientIdAndStatus(patientId, "ACTIVE");
    }

    public List<Document> getDocumentsBySubject(String subject) {
        return documentRepository.findBySubject(subject);
    }

    public void deleteFile(Long documentId) {
        Optional<Document> document = documentRepository.findById(documentId);
        document.ifPresent(doc -> {
            doc.setStatus("DELETED");
            documentRepository.save(doc);
        });
    }
}
