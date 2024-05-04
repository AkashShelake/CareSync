package com.akash.CareSync.practicemember.controller;

import com.akash.CareSync.practicemember.entity.PracticeMember;
import com.akash.CareSync.practicemember.service.PracticeMemberService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rest/v1/practice-members")
public class PracticeMemberController {

    private final PracticeMemberService practiceMemberService;

    PracticeMemberController(PracticeMemberService practiceMemberService) {
        this.practiceMemberService = practiceMemberService;
    }

    @GetMapping
    public List<PracticeMember> getMembersList() {
        return practiceMemberService.getAllPracticeMembers();
    }

    @PostMapping
    public PracticeMember addMember(@RequestBody PracticeMember member) {
        return practiceMemberService.addMember(member);
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
