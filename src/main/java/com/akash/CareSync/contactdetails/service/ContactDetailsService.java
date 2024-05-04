package com.akash.CareSync.contactdetails.service;

import com.akash.CareSync.contactdetails.entity.ContactDetails;
import com.akash.CareSync.contactdetails.repository.ContactDetailsRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ContactDetailsService{
    ContactDetailsRepository contactDetailsRepository;
    public ContactDetailsService(ContactDetailsRepository contactDetailsRepository) {
        this.contactDetailsRepository = contactDetailsRepository;
    }

    public Optional<ContactDetails> getById(Long id) {
        return contactDetailsRepository.findById(id);
    }

    public ContactDetails addContactDetails(ContactDetails practicePatient) {
        return contactDetailsRepository.save(practicePatient);
    }

    public ContactDetails updateContactDetails(ContactDetails contactDetails) {
        Optional<ContactDetails> optionalContactDetails = getById(contactDetails.getId());
        optionalContactDetails.ifPresent(
                contact -> {
                    contact.setEmail(contactDetails.getEmail());
                    contact.setPhone(contactDetails.getPhone());
                }
        );
        return optionalContactDetails.orElse(null);
    }
}
