package com.akash.CareSync.chartnotes.service;

import com.akash.CareSync.chartnotes.entity.Encounter;
import com.akash.CareSync.chartnotes.repository.EncounterRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EncounterService {
    private final EncounterRepository encounterRepository;

    EncounterService(EncounterRepository encounterRepository) {
        this.encounterRepository = encounterRepository;
    }

    // 1. Create Encounter linked to an appointment
    public Encounter createEncounterWithAppointment(Long appointmentId, Encounter encounter) {
        encounter.setAppointment_id(appointmentId);
        encounter.setDate(new java.util.Date());
        encounter.setStatus("pending");
        return encounterRepository.save(encounter);
    }

    // 2. Create Encounter without an appointment
    public Encounter createDirectEncounter(Encounter encounter) {
        encounter.setAppointment_id(null);
        encounter.setDate(new java.util.Date());
        encounter.setStatus("pending");
        return encounterRepository.save(encounter);
    }

    // 3. Update an Encounter
    public Optional<Encounter> updateEncounter(Long id, Encounter updatedEncounter) {
        return encounterRepository.findById(id).map(existingEncounter -> {
            existingEncounter.setTemperature(updatedEncounter.getTemperature());
            existingEncounter.setBlood_pressure(updatedEncounter.getBlood_pressure());
            existingEncounter.setHeart_rate(updatedEncounter.getHeart_rate());
            existingEncounter.setSymptoms(updatedEncounter.getSymptoms());
            existingEncounter.setPrescriptions(updatedEncounter.getPrescriptions());
            existingEncounter.setTreatment_notes(updatedEncounter.getTreatment_notes());
            return encounterRepository.save(existingEncounter);
        });
    }

    // 4. Get an Encounter by ID
    public Optional<Encounter> getEncounterById(Long id) {
        return encounterRepository.findById(id);
    }

    // 5. List all Encounters with filters
    public List<Encounter> getEncountersWithFilters(Long provider_id, Long patient_id, String status) {
        if (provider_id == null && patient_id == null && status == null) {
            return encounterRepository.findAll();
        }
        return encounterRepository.findByProviderIdAndPatientIdAndStatus(provider_id, patient_id, status);
    }

    // 6. Soft-delete an Encounter
    public boolean softDeleteEncounter(Long id) {
        return encounterRepository.findById(id).map(encounter -> {
            encounter.setStatus("deleted");
            encounterRepository.save(encounter);
            return true;
        }).orElse(false);
    }

    // 7. Update status of an Encounter
    public Optional<Encounter> updateStatus(Long id, String status) {
        return encounterRepository.findById(id).map(encounter -> {
            encounter.setStatus(status);
            return encounterRepository.save(encounter);
        });
    }
}
