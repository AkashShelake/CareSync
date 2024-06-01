package com.akash.CareSync.practicemember.controller;

import com.akash.CareSync.practicemember.entity.PracticeMember;
import com.akash.CareSync.practicemember.service.PracticeMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/rest/v1/practice-members")
public class PracticeMemberController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    private final PracticeMemberService practiceMemberService;

    PracticeMemberController(PracticeMemberService practiceMemberService) {
        this.practiceMemberService = practiceMemberService;
    }

    @GetMapping
    public List<PracticeMember> getMembersList() {
        return practiceMemberService.getAllPracticeMembers();
    }

    @PostMapping
    public PracticeMember addMember(@RequestBody PracticeMember member, @RequestParam Set<Long> roleIds) {
        member.setPassword(passwordEncoder.encode(member.getPassword()));
        if(roleIds.isEmpty()){
            return practiceMemberService.addMember(member);
        }
        return practiceMemberService.addMemberWithRoles(member, roleIds);
    }

    @PutMapping
    public PracticeMember updateMember(@RequestBody PracticeMember member) {
        return practiceMemberService.updateMember(member);
    }

    @DeleteMapping("{id}")
    public void deleteMember(@PathVariable Long id) {
        practiceMemberService.deleteMember(id);
    }

}
