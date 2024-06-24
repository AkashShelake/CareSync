package com.akash.CareSync.authentication.controller;

import com.akash.CareSync.authentication.Entity.JwtAuthRequest;
import com.akash.CareSync.authentication.Entity.JwtAuthResponse;
import com.akash.CareSync.config.AppConstants;
import com.akash.CareSync.practicemember.entity.PracticeMember;
import com.akash.CareSync.practicemember.repository.PracticeMemberRepository;
import com.akash.CareSync.practicemember.service.PracticeMemberService;
import com.akash.CareSync.security.JwtTokenHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private AppConstants appConstants;

    @Autowired
    private JwtTokenHelper jwtTokenHelper;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private final PracticeMemberService practiceMemberService;

    AuthController(PracticeMemberService practiceMemberService) {
        this.practiceMemberService = practiceMemberService;
    }

    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> createToken(@RequestBody JwtAuthRequest request) throws Exception{
        this.authenticate(request.getUsername(), request.getPassword());
        UserDetails user = this.userDetailsService.loadUserByUsername(request.getUsername());
        String token = this.jwtTokenHelper.generateToken(user);
        JwtAuthResponse response = new JwtAuthResponse();
        response.setToken(token);
        PracticeMember member = this.practiceMemberService.getByUserName(user.getUsername()).orElseThrow(() -> new UsernameNotFoundException(appConstants.USER_NOT_FOUND));
        response.setPracticeMember(member);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<PracticeMember> createPracticeAdmin(@RequestBody PracticeMember member) {
        member.setPassword(passwordEncoder.encode(member.getPassword()));
        return new ResponseEntity<>(practiceMemberService.addMember(member), HttpStatus.CREATED);
    }

    private void authenticate(String username, String password) throws Exception {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        try {
            this.authenticationManager.authenticate(authenticationToken);
        }catch (BadCredentialsException e){
            System.err.println("Authentication failed: " + e.getMessage());
            throw new BadCredentialsException(appConstants.AUTHENTICATION_ERROR);
        }
    }


}
