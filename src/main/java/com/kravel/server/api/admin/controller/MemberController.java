package com.kravel.server.api.admin.controller;

import com.kravel.server.common.util.message.ResponseMessage;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/member")
public class MemberController {

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseMessage findAllMembers(@RequestParam("offset") int offset,
                                          @RequestParam("max") int max,
                                          @RequestParam("sort") String sort,
                                          @RequestParam("order") String order,
                                          Authentication authentication) throws Exception {

        Map<String, Object> param = new HashMap<>();
        param.put("offset", offset);
        param.put("max", max);
        param.put("sort", sort);
        param.put("orser", order);

        return new ResponseMessage(HttpStatus.OK, null);
    }
}
