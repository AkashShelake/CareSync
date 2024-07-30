package com.akash.CareSync.practicepatient.controller;

import com.akash.CareSync.practicemember.entity.PracticeMember;
import com.akash.CareSync.practicepatient.entity.PracticePatient;
import com.akash.CareSync.practicepatient.service.PracticePatientService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/rest/v1/practice-patients")
public class PracticePatientController {
    private final PracticePatientService practicePatientService;

    PracticePatientController(PracticePatientService practicePatientService) {
        this.practicePatientService = practicePatientService;
    }

    @GetMapping
    public List<PracticePatient> getPatientsList() {
        return practicePatientService.getAllPracticePatients();
    }

    @GetMapping("{id}")
    public PracticePatient getPatient(@PathVariable Long id) {
        return practicePatientService.getById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User Not Found!"));
    }

    @PostMapping
    public PracticePatient addPatient(@RequestBody PracticePatient patient) {
        return practicePatientService.addPatient(patient);
    }

    @PutMapping
    public PracticePatient updatePatient(@RequestBody PracticePatient patient) {
        return practicePatientService.updatePatient(patient);
    }

    @GetMapping("/list")
    public List<PracticePatient> searchPatients(@RequestParam(required = false) String searchString) {
        return practicePatientService.searchPatients(searchString);
    }

    @PatchMapping("/{id}/disable")
    public PracticePatient disablePatient(@PathVariable Long id) {
        return practicePatientService.makePatientInactive(id);
    }

    @DeleteMapping("{id}")
    public void deletePatient(@PathVariable Long id) {
        practicePatientService.deletePatient(id);
    }
}
