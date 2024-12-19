package com.akash.CareSync.chartnotes.repository;

import com.akash.CareSync.chartnotes.entity.Encounter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EncounterRepository extends JpaRepository<Encounter, Long> {

    @Query(
            value = "SELECT * FROM encounter e " +
                    "WHERE (:providerId IS NULL OR e.provider_id = :providerId) " +
                    "AND (:patientId IS NULL OR e.patient_id = :patientId) " +
                    "AND (:status IS NULL OR e.status = :status)",
            nativeQuery = true
    )
    List<Encounter> findByProviderIdAndPatientIdAndStatus(Long providerId, Long patientId, String status);
}