package com.akash.CareSync.document.controller;

import com.akash.CareSync.document.entity.Document;
import com.akash.CareSync.document.service.DocumentService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/rest/v1/documents")
public class DocumentController {

    private final DocumentService documentService;

    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @PostMapping("/upload")
    public ResponseEntity<Document> uploadFile(@RequestParam("patientId") Long patientId,
                                               @RequestParam("file") MultipartFile file,
                                               @RequestParam("subject") String subject) {
        try {
            Document document = documentService.uploadFile(patientId, file, subject);
            return new ResponseEntity<>(document, HttpStatus.CREATED);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable Long id) {
            return documentService.downloadFile(id)
                .map(doc -> ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + doc.getFileName() + "\"")
                        .body(doc.getFileData()))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<Document>> getActiveFiles(@PathVariable Long patientId) {
        List<Document> documents = documentService.getActiveFilesForPatient(patientId);
        return new ResponseEntity<>(documents, HttpStatus.OK);
    }

    @GetMapping("/subject")
    public ResponseEntity<List<Document>> getDocumentsBySubject(@RequestParam("subject") String subject) {
        List<Document> documents = documentService.getDocumentsBySubject(subject);
        return new ResponseEntity<>(documents, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteFile(@PathVariable Long id) {
        documentService.deleteFile(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
