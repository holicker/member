package com.spreadmat.member.service.impl;

import com.spreadmat.member.domain.Member;
import com.spreadmat.member.repository.MemberRepository;
import com.spreadmat.member.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class MemberServiceImpl implements MemberService {

    @Autowired private MemberRepository memberRepository;

    @Override
    public Member save(Member member) {
        return memberRepository.save(member);
    }

    @Override
    public Page<Member> findAll(Pageable pageable) {
        return memberRepository.findAll(pageable);
    }

    @Override
    public Optional<Member> findOneById(String id) {
        return memberRepository.findById(id);
    }

    @Override
    public Optional<Member> findOneByEmail(String email) {
        return memberRepository.findByEmail(email);
    }

    @Override
    public void delete(String id) {
        memberRepository.deleteById(id);
    }

    // 최초 멤버 생성
    @Override
    public Member createMember(Member member) {
        if(member == null || member.getEmail() == null) throw new RuntimeException("Invalid Arguments! 멤버가 비어있습니다.");
        final String email = member.getEmail();
        if(memberRepository.existsByEmail(email)) throw new RuntimeException("Email already exist! 이메일이 이미 존재합니다!");
        return memberRepository.save(member);
    }

    @Override
    public Member getByCredentials(final Member member, final PasswordEncoder encoder) {
        final Member originalUser = memberRepository.findByEmail(member.getEmail()).orElseGet(Member::new);
        return (encoder.matches(member.getPassword(), originalUser.getPassword())) ? originalUser : null;
    }
}
