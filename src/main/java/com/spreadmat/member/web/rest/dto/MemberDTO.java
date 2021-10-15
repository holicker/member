package com.spreadmat.member.web.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberDTO {

    private String token;
    private String email;
    private String memberName;
    private String password;
    private String id;

    public MemberDTO token(String token){
        this.token = token;
        return this;
    }

    public MemberDTO passwordEncrypt(PasswordEncoder passwordEncoder){
        this.password = passwordEncoder.encode(this.password);
        return this;
    }
}
