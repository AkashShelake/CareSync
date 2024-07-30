package com.akash.CareSync.practicemember.service;

import com.akash.CareSync.config.AppConstants;
import com.akash.CareSync.contactdetails.entity.ContactDetails;
import com.akash.CareSync.contactdetails.repository.ContactDetailsRepository;
import com.akash.CareSync.practicemember.entity.PracticeMember;
import com.akash.CareSync.practicemember.repository.PracticeMemberRepository;
import com.akash.CareSync.role.entity.Role;
import com.akash.CareSync.role.repository.RoleRepository;
import io.micrometer.common.util.StringUtils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PracticeMemberService {

    @Autowired
    private AppConstants appConstants;

    private final RoleRepository roleRepository;
    private final PracticeMemberRepository practiceMemberRepository;
    private final ContactDetailsRepository contactDetailsRepository;

    public PracticeMemberService(PracticeMemberRepository practiceMemberRepository, RoleRepository roleRepository, ContactDetailsRepository contactDetailsRepository) {
        this.practiceMemberRepository = practiceMemberRepository;
        this.roleRepository = roleRepository;
        this.contactDetailsRepository = contactDetailsRepository;
    }

    public List<PracticeMember> getAllPracticeMembers(String role, String status, String search) {
        role = StringUtils.isBlank(role) ? null : role;
        status = StringUtils.isBlank(status) ? null : status;
        search = StringUtils.isBlank(search) ? null : search;
        return practiceMemberRepository.findAllByRoleStatusAndSearch(role, status, search);
    }

    public Optional<PracticeMember> getById(Long id) {
        return practiceMemberRepository.findById(id);
    }

    public Optional<PracticeMember> getByUserName(String username) {
        return practiceMemberRepository.findByUserName(username);
    }

    public PracticeMember addMember(PracticeMember practiceMember) {
        Set<Role> roles = new HashSet<>();
        Optional<Role> role = Optional.of(roleRepository.findByName("ROLE_ADMIN").orElseThrow());
        role.ifPresent(roles::add);
        practiceMember.setRoles(roles);
        practiceMember.setStatus("Enabled");
        return practiceMemberRepository.save(practiceMember);
    }

    @Transactional
    public PracticeMember addMemberWithRoles(PracticeMember practiceMember) {
        Set<Role> roles = practiceMember.getRoles().stream()
                .map(role -> roleRepository.findByName(role.getName()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toSet());
        practiceMember.setRoles(roles);
        practiceMember.setUserName(practiceMember.getUserName());
        practiceMember.setStatus("Enabled");
        return practiceMemberRepository.save(practiceMember);
    }

    public PracticeMember updateMember(PracticeMember practiceMember) {
        Optional<PracticeMember> optionalPracticeMember = practiceMemberRepository.findById(practiceMember.getId());
        if (optionalPracticeMember.isPresent()) {
            PracticeMember existingMember = optionalPracticeMember.get();

            // Update fields if provided
            if (practiceMember.getFirst_name() != null) {
                existingMember.setFirst_name(practiceMember.getFirst_name());
            }
            if (practiceMember.getLast_name() != null) {
                existingMember.setLast_name(practiceMember.getLast_name());
            }
            if (practiceMember.getGender() != null) {
                existingMember.setGender(practiceMember.getGender());
            }
            if (practiceMember.getBlood_group() != null) {
                existingMember.setBlood_group(practiceMember.getBlood_group());
            }
            if (practiceMember.getDegree() != null) {
                existingMember.setDegree(practiceMember.getDegree());
            }
            if (practiceMember.getStatus() != null) {
                existingMember.setStatus(practiceMember.getStatus());
            }
            if (practiceMember.getUserName() != null) {
                existingMember.setUserName(practiceMember.getUserName());
            }

            // Handle ContactDetails
            ContactDetails updatedContactDetails = practiceMember.getContactDetails();
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
                existingMember.setContactDetails(updatedContactDetails);
            }

            // Update roles
            Set<Role> roles = practiceMember.getRoles().stream()
                    .map(role -> roleRepository.findByName(role.getName()))
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .collect(Collectors.toSet());
            if (!roles.isEmpty()) {
                existingMember.setRoles(roles);
            }

            // Update photo if provided
            if (practiceMember.getPhoto() != null) {
                existingMember.setPhoto(practiceMember.getPhoto());
            }

            existingMember.setUpdatedAt(Instant.now());
            return practiceMemberRepository.save(existingMember);
        } else {
            throw new UsernameNotFoundException(appConstants.USER_NOT_FOUND);
        }
    }

    public PracticeMember setMemberStatus(Long id, String status) {
        PracticeMember member = practiceMemberRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User Not Found!"));
        member.setStatus(status);
        return practiceMemberRepository.save(member);
    }

    public void deleteMember(Long id) {
        Optional<PracticeMember> practiceMember = getById(id);
        practiceMember.ifPresent(practiceMemberRepository::delete);
    }
}
