package com.spreadmat.member.web.rest.mapper;


import com.spreadmat.member.domain.Member;
import com.spreadmat.member.web.rest.dto.MemberDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MemberMapper extends EntityMapper<MemberDTO, Member> {

    Member toEntity(MemberDTO memberDTO);

    @Mapping(target = "token", ignore = true)
    @Mapping(target = "passwordEncrypt", ignore = true)
    MemberDTO toDto(Member member);
}
