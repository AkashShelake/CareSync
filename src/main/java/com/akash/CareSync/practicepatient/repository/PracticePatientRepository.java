package com.akash.CareSync.practicepatient.repository;

import com.akash.CareSync.practicepatient.entity.PracticePatient;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PracticePatientRepository extends CrudRepository<PracticePatient, Long> {

}
