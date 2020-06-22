package com.kravel.server.api.admin.controller;

import com.kravel.server.api.admin.dto.MemberDTO;
import com.kravel.server.api.admin.service.AdminMemberService;
import com.kravel.server.common.util.message.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/members")
public class AdminMemberController {

    @Autowired
    private AdminMemberService adminMemberService;

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseMessage findAllMembers(@RequestParam(value = "offset", defaultValue = "0") int offset,
                                          @RequestParam(value = "max", defaultValue = "10") int max,
                                          @RequestParam(value = "login-email", defaultValue = "") String loginEmail,
                                          @RequestParam(value = "gender", defaultValue = "") String gender,
                                          @RequestParam(value = "role", defaultValue = "") String role,
                                          @RequestParam(value = "langu", defaultValue = "") String langu,
                                          @RequestParam(value = "use-at", defaultValue = "") String useAt,
                                          Authentication authentication) throws Exception {

        Map<String, Object> param = new HashMap<>();
        param.put("offset", offset);
        param.put("max", max);

        param.put("loginEmail", loginEmail);
        param.put("gender", gender);
        param.put("role", role);
        param.put("langu", langu);
        param.put("useAt", useAt);

        List<MemberDTO> memberDTOs = adminMemberService.findAllMembers(param);

        return new ResponseMessage(HttpStatus.OK, memberDTOs);
    }
}
