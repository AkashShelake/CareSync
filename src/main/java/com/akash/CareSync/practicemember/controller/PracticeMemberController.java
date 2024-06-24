package com.akash.CareSync.practicemember.controller;

import com.akash.CareSync.practicemember.entity.PracticeMember;
import com.akash.CareSync.practicemember.service.PracticeMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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

    @GetMapping("{id}")
    public PracticeMember getMember(@PathVariable Long id) {
        return practiceMemberService.getById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User Not Found!"));
    }

    @PostMapping
    public PracticeMember addMember(@RequestBody PracticeMember member, @RequestParam Set<String> roles) {
        member.setPassword(passwordEncoder.encode(member.getPassword()));
        return practiceMemberService.addMemberWithRoles(member, roles);
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
