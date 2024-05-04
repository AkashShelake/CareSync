package com.akash.CareSync.practicepatient.controller;

import com.akash.CareSync.practicepatient.entity.PracticePatient;
import com.akash.CareSync.practicepatient.service.PracticePatientService;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping
    public PracticePatient add(@RequestBody PracticePatient patient) {
        return practicePatientService.addPatient(patient);
    }

    @PutMapping
    public PracticePatient put(@RequestBody PracticePatient patient) {
        return practicePatientService.updatePatient(patient);
    }

    @DeleteMapping("{id}")
    public void deletePatient(@PathVariable Long id) {
        practicePatientService.deletePatient(id);
    }

}
