package com.akash.CareSync.practicemember.controller;

import com.akash.CareSync.practicemember.entity.PracticeMember;
import com.akash.CareSync.practicemember.service.PracticeMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

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
    public List<PracticeMember> getMembersList(@RequestParam(required = false) String role,
                                               @RequestParam(required = false) String status,
                                               @RequestParam(required = false) String search) {
        return practiceMemberService.getAllPracticeMembers(role, status, search);
    }

    @GetMapping("{id}")
    public PracticeMember getMember(@PathVariable Long id) {
        return practiceMemberService.getById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User Not Found!"));
    }

    @PostMapping
    public PracticeMember addMember(@RequestBody PracticeMember member) {
        member.setPassword(passwordEncoder.encode(member.getPassword()));
        return practiceMemberService.addMemberWithRoles(member);
    }

    @PutMapping
    public ResponseEntity<PracticeMember> updateMember(@RequestBody PracticeMember member) {
        PracticeMember updatedMember = practiceMemberService.updateMember(member);
        if (updatedMember != null) {
            return ResponseEntity.ok(updatedMember);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("{id}/status")
    public ResponseEntity<PracticeMember> setMemberStatus(@PathVariable Long id, @RequestParam String status) {
        if (!status.equals("Enabled") && !status.equals("Disabled")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid status value");
        }
        PracticeMember updatedMember = practiceMemberService.setMemberStatus(id, status);
        return ResponseEntity.ok(updatedMember);
    }

    @DeleteMapping("{id}")
    public void deleteMember(@PathVariable Long id) {
        practiceMemberService.deleteMember(id);
    }
}
