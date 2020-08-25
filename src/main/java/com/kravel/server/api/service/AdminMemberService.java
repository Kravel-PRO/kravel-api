package com.kravel.server.api.service;

import com.kravel.server.api.dto.MemberDTO;
import com.kravel.server.api.dto.MembersDTO;
import com.kravel.server.api.mapper.AdminMemberMapper;
import com.kravel.server.auth.model.Member;
import com.kravel.server.common.util.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class AdminMemberService {

    private final AdminMemberMapper adminMemberMapper;

    public MembersDTO findAllMembers(Map<String, Object> param) throws Exception {

        MembersDTO membersDTO = new MembersDTO();

        List<MemberDTO> memberDTOs = adminMemberMapper.findAllMembers(param);
        if (memberDTOs.isEmpty()) {
            throw new NotFoundException("Is not exist members");
        }

        membersDTO.setMembers(memberDTOs);
        membersDTO.setTotalCount(adminMemberMapper.findAllMembersCount(param));

        return membersDTO;
    }

    public Member findMemberById(Map<String, Object> param) throws Exception {
        return adminMemberMapper.findMemberById(param);
    }
}
