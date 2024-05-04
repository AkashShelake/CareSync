package com.akash.CareSync.practicemember.service;

import com.akash.CareSync.practicemember.entity.PracticeMember;
import com.akash.CareSync.practicemember.repository.PracticeMemberRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class PracticeMemberService {

    private final PracticeMemberRepository practiceMemberRepository;

    public PracticeMemberService(PracticeMemberRepository practiceMemberRepository) {
        this.practiceMemberRepository = practiceMemberRepository;
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
                }
        );
        return optionalPracticeMember.orElse(null);
    }

    public void deleteMember(Long id) {
        Optional<PracticeMember> practiceMember = getById(id);
        practiceMember.ifPresent(practiceMemberRepository::delete);
    }
}
