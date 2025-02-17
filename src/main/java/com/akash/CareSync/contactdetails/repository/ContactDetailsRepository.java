package com.akash.CareSync.contactdetails.repository;

import com.akash.CareSync.contactdetails.entity.ContactDetails;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ContactDetailsRepository extends CrudRepository<ContactDetails, Long> {
    Optional<ContactDetails> findByEmail(String email);
}
