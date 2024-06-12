package com.akash.CareSync.security;

import com.akash.CareSync.practicemember.entity.PracticeMember;
import com.akash.CareSync.practicemember.repository.PracticeMemberRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;


@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final PracticeMemberRepository practiceMemberRepository;

    public CustomUserDetailsService(PracticeMemberRepository practiceMemberRepository) {
        this.practiceMemberRepository = practiceMemberRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String user_name) throws UsernameNotFoundException {
        PracticeMember member = this.practiceMemberRepository.findByUserName(user_name).orElseThrow(null);
        return new User(member.getUsername(), member.getPassword(), member.getAuthorities());
    }
}
