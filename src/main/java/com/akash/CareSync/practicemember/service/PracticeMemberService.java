package com.akash.CareSync.practicemember.service;

import com.akash.CareSync.practicemember.entity.PracticeMember;
import com.akash.CareSync.practicemember.repository.PracticeMemberRepository;
import com.akash.CareSync.role.entity.Role;
import com.akash.CareSync.role.repository.RoleRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class PracticeMemberService {

    private final RoleRepository roleRepository;

    private final PracticeMemberRepository practiceMemberRepository;

    public PracticeMemberService(PracticeMemberRepository practiceMemberRepository, RoleRepository roleRepository) {
        this.practiceMemberRepository = practiceMemberRepository;
        this.roleRepository = roleRepository;
    }

    public List<PracticeMember> getAllPracticeMembers() {
        return (List<PracticeMember>) practiceMemberRepository.findAll();
    }

    public Optional<PracticeMember> getById(Long id) {
//        List<String> a = List.of("1","2","3","4","5","6","7","8","9","10");
//        Set<String> b = a.stream().filter(s -> s.contains("2")).collect(Collectors.toSet());
//        IntStream.range(10, 100).forEach(System.out::println);
//        a.forEach(System.out::println);
//        Optional.ofNullable(practiceMemberRepository.findQu())
        return practiceMemberRepository.findById(id);
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
    public PracticeMember addMemberWithRoles(PracticeMember practiceMember, Set<String> userRoles) {
        Set<Role> roles = new HashSet<>();
        for (String roleName : userRoles) {
            Optional<Role> role = roleRepository.findByName(roleName);
            role.ifPresent(roles::add);
        }
        practiceMember.setRoles(roles);
        practiceMember.setStatus("Enabled");
        return practiceMemberRepository.save(practiceMember);
    }

    public PracticeMember updateMember(PracticeMember practiceMember) {
        Optional<PracticeMember> optionalPracticeMember = getById(practiceMember.getId());
        optionalPracticeMember.ifPresent(
                member -> {
                    member.setFirst_name(practiceMember.getFirst_name());
                    member.setLast_name(practiceMember.getLast_name());
                    member.setGender(practiceMember.getGender());
                    member.setBlood_group(practiceMember.getBlood_group());
                    member.setDegree(practiceMember.getDegree());
                    member.setUpdatedAt(Instant.now());
                }
        );
        return optionalPracticeMember.orElse(null);
    }

    public void deleteMember(Long id) {
        Optional<PracticeMember> practiceMember = getById(id);
        practiceMember.ifPresent(practiceMemberRepository::delete);
    }
}
