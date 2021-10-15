package com.spreadmat.member.web.rest;

import com.spreadmat.member.domain.Member;
import com.spreadmat.member.security.TokenProvider;
import com.spreadmat.member.service.MemberService;
import com.spreadmat.member.web.rest.dto.MemberDTO;
import com.spreadmat.member.web.rest.dto.ResponseDTO;
import com.spreadmat.member.web.rest.mapper.MemberMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(path = "member", produces = "application/json")
public class MemberController {

    @Autowired private MemberService memberService;
    @Autowired private MemberMapper memberMapper;
    @Autowired private TokenProvider tokenProvider;
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @PostMapping("/register")
    public ResponseEntity<MemberDTO> registerMember(@RequestBody MemberDTO memberDTO){
        Member member = memberMapper.toEntity(memberDTO.passwordEncrypt(passwordEncoder));
        Member registerdMember = memberService.createMember(member);
        MemberDTO responseDTO = memberMapper.toDto(registerdMember);
        return ResponseEntity.ok(responseDTO);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody MemberDTO memberDTO){
        Member member = memberService.getByCredentials(memberMapper.toEntity(memberDTO), passwordEncoder);
        if(member != null){
            final String token = tokenProvider.create(member);
            final MemberDTO responseDTO = memberMapper.toDto(member).token(token);
            return ResponseEntity.ok(responseDTO);
        } else {
            ResponseDTO responseDTO = ResponseDTO.builder()
                    .error("Login Failed")
                    .build();
            return ResponseEntity
                    .badRequest()
                    .body(responseDTO);
        }
    }
}
