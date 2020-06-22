package com.kravel.server.api.admin.service;

import com.kravel.server.api.admin.dto.MemberDTO;
import com.kravel.server.api.admin.mapper.AdminMemberMapper;
import com.kravel.server.common.util.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class AdminMemberService {

    @Autowired
    private AdminMemberMapper adminMemberMapper;

    public List<MemberDTO> findAllMembers(Map<String, Object> param) throws Exception {

        List<MemberDTO> memberDTOs = adminMemberMapper.findAllMembers(param);
        if (memberDTOs.isEmpty()) {
            throw new NotFoundException("Is not exist members");
        }

        return memberDTOs;
    }
}
