package com.akash.CareSync.practicepatient.service;

import com.akash.CareSync.practicepatient.entity.PracticePatient;
import com.akash.CareSync.practicepatient.repository.PracticePatientRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PracticePatientService {

    private final PracticePatientRepository practicepatientRepository;

    public PracticePatientService(PracticePatientRepository practicepatientRepository) {
        this.practicepatientRepository = practicepatientRepository;
    }
    public List<PracticePatient> getAllPracticePatients() {
        return (List<PracticePatient>) practicepatientRepository.findAll();
    }

    public Optional<PracticePatient> getById(Long id) {
        return practicepatientRepository.findById(id);
    }

    public PracticePatient addPatient(PracticePatient practicePatient) {
        return practicepatientRepository.save(practicePatient);
    }

    public PracticePatient updatePatient(PracticePatient practicePatient) {
        Optional<PracticePatient> optionalPracticePatient = getById(practicePatient.getId());
        optionalPracticePatient.ifPresent(
                patient -> {
                    patient.setFirst_name(practicePatient.getFirst_name());
                    patient.setLast_name(practicePatient.getLast_name());
                    patient.setGender(practicePatient.getGender());
                    patient.setBlood_group(practicePatient.getBlood_group());
                    patient.setDate_of_birth(practicePatient.getDate_of_birth());
                }
        );
        return optionalPracticePatient.orElse(null);
    }

    public void deletePatient(Long id) {
        Optional<PracticePatient> practicePatient = getById(id);
        practicePatient.ifPresent(practicepatientRepository::delete);
    }
}
