package com.akash.CareSync.security;

import com.akash.CareSync.config.AppConstants;
import com.akash.CareSync.practicemember.entity.PracticeMember;
import com.akash.CareSync.practicemember.repository.PracticeMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;


@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private AppConstants appConstants;

    private final PracticeMemberRepository practiceMemberRepository;

    public CustomUserDetailsService(PracticeMemberRepository practiceMemberRepository) {
        this.practiceMemberRepository = practiceMemberRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String user_name) throws UsernameNotFoundException {
        Optional<PracticeMember> optionalMember = this.practiceMemberRepository.findByUserName(user_name);
        if(optionalMember.isEmpty()){
            throw new UsernameNotFoundException(appConstants.USER_NOT_FOUND);
        }
        PracticeMember member = optionalMember.get();
        return new User(member.getUsername(), member.getPassword(), member.getAuthorities());
    }
}
