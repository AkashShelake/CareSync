package com.akash.CareSync.practicepatient.repository;

import com.akash.CareSync.practicepatient.entity.PracticePatient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PracticePatientRepository extends JpaRepository<PracticePatient, Long> {

    @Query("SELECT p FROM PracticePatient p " +
            "LEFT JOIN p.contactDetails c " +
            "WHERE (:searchString IS NULL OR :searchString = '' " +
            "OR LOWER(p.first_name) LIKE LOWER(CONCAT('%', :searchString, '%')) " +
            "OR LOWER(p.last_name) LIKE LOWER(CONCAT('%', :searchString, '%')) " +
            "OR LOWER(c.email) LIKE LOWER(CONCAT('%', :searchString, '%'))) " +
            "AND p.status = 'Enabled'")
    List<PracticePatient> searchByFirstNameLastNameOrEmail(@Param("searchString") String searchString);
}
