package com.akash.CareSync.practicepatient.service;

import com.akash.CareSync.contactdetails.entity.ContactDetails;
import com.akash.CareSync.contactdetails.repository.ContactDetailsRepository;
import com.akash.CareSync.practicepatient.entity.PracticePatient;
import com.akash.CareSync.practicepatient.repository.PracticePatientRepository;
import io.micrometer.common.util.StringUtils;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class PracticePatientService {

    private final PracticePatientRepository practicePatientRepository;
    private final ContactDetailsRepository contactDetailsRepository;

    public PracticePatientService(PracticePatientRepository practicePatientRepository, ContactDetailsRepository contactDetailsRepository) {
        this.practicePatientRepository = practicePatientRepository;
        this.contactDetailsRepository = contactDetailsRepository;
    }

    public List<PracticePatient> getAllPracticePatients() {
        return (List<PracticePatient>) practicePatientRepository.findAll();
    }

    public Optional<PracticePatient> getById(Long id) {
        return practicePatientRepository.findById(id);
    }

    public PracticePatient addPatient(PracticePatient practicePatient) {
        practicePatient.setStatus("Enabled");
        return practicePatientRepository.save(practicePatient);
    }

    public PracticePatient updatePatient(PracticePatient practicePatient) {
        Optional<PracticePatient> optionalPracticePatient = getById(practicePatient.getId());
        if (optionalPracticePatient.isPresent()) {
            PracticePatient patient = optionalPracticePatient.get();

            // Update fields if they are not empty
            if (practicePatient.getFirst_name() != null && !practicePatient.getFirst_name().isEmpty()) {
                patient.setFirst_name(practicePatient.getFirst_name());
            }
            if (practicePatient.getLast_name() != null && !practicePatient.getLast_name().isEmpty()) {
                patient.setLast_name(practicePatient.getLast_name());
            }
            if (practicePatient.getGender() != null && !practicePatient.getGender().isEmpty()) {
                patient.setGender(practicePatient.getGender());
            }
            if (practicePatient.getBlood_group() != null && !practicePatient.getBlood_group().isEmpty()) {
                patient.setBlood_group(practicePatient.getBlood_group());
            }
            if (practicePatient.getDate_of_birth() != null && !practicePatient.getDate_of_birth().isEmpty()) {
                patient.setDate_of_birth(practicePatient.getDate_of_birth());
            }

            // Handle ContactDetails
            ContactDetails updatedContactDetails = practicePatient.getContactDetails();
            if (updatedContactDetails != null) {
                String email = updatedContactDetails.getEmail();
                if (email != null) {
                    Optional<ContactDetails> existingContact = contactDetailsRepository.findByEmail(email);
                    if (existingContact.isPresent()) {
                        // Update existing ContactDetails
                        ContactDetails existingDetails = existingContact.get();
                        if (updatedContactDetails.getPhone() != null) {
                            existingDetails.setPhone(updatedContactDetails.getPhone());
                        }
                        updatedContactDetails = existingDetails;
                    } else {
                        // Create new ContactDetails
                        updatedContactDetails = contactDetailsRepository.save(updatedContactDetails);
                    }
                }
                patient.setContactDetails(updatedContactDetails);
            }

            // Update timestamp
            patient.setUpdatedAt(Instant.now());

            return practicePatientRepository.save(patient);
        }
        return null;
    }

    public List<PracticePatient> searchPatients(String searchString) {
        searchString = StringUtils.isEmpty(searchString) ? null : searchString;
        return practicePatientRepository.searchByFirstNameLastNameOrEmail(searchString);
    }

    public PracticePatient makePatientInactive(Long id) {
        Optional<PracticePatient> optionalPracticePatient = practicePatientRepository.findById(id);
        if (optionalPracticePatient.isPresent()) {
            PracticePatient patient = optionalPracticePatient.get();
            if ("Enabled".equals(patient.getStatus())) {
                patient.setStatus("Disabled");
                patient.setUpdatedAt(Instant.now());
                return practicePatientRepository.save(patient);
            }
        }
        return null;
    }

    public void deletePatient(Long id) {
        Optional<PracticePatient> practicePatient = getById(id);
        practicePatient.ifPresent(practicePatientRepository::delete);
    }
}
