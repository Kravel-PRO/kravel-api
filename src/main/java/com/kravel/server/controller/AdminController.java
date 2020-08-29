//package com.kravel.server.controller;
//
//import com.kravel.server.dto.MembersDTO;
//import com.kravel.server.dto.NoticeDTO;
//import com.kravel.server.service.AdminMemberService;
//import com.kravel.server.service.NoticeService;
//import com.kravel.server.common.util.message.ResponseMessage;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.security.core.Authentication;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//@RestController
//@RequiredArgsConstructor
//public class AdminController {
//
//    private final AdminMemberService adminMemberService;
//    private final NoticeService noticeService;
//
//
//    @GetMapping("/api/admin/members")
//    @ResponseStatus(HttpStatus.OK)
//    @PreAuthorize("hasRole('ROLE_USER')")
//    public ResponseMessage findAllMembers(@RequestParam(value = "offset", defaultValue = "0") int offset,
//                                          @RequestParam(value = "size", defaultValue = "10") int size,
//                                          @RequestParam(value = "login-email", defaultValue = "") String loginEmail,
//                                          @RequestParam(value = "gender", defaultValue = "") String gender,
//                                          @RequestParam(value = "role-type", defaultValue = "") String roleType,
//                                          @RequestParam(value = "speech", defaultValue = "") String speech,
//                                          @RequestParam(value = "use-at", defaultValue = "") String useAt,
//                                          Authentication authentication) throws Exception {
//
//        Map<String, Object> param = new HashMap<>();
//        param.put("offset", offset);
//        param.put("size", size);
//        param.put("loginEmail", loginEmail);
//        param.put("gender", gender);
//        param.put("roleType", roleType);
//        param.put("speech", speech);
//        param.put("useAt", useAt);
//
//        MembersDTO membersDTO = adminMemberService.findAllMembers(param);
//
//        return new ResponseMessage(HttpStatus.OK, membersDTO);
//    }
//
//    @GetMapping("/api/admin/members/{memberId}")
//    @ResponseStatus(HttpStatus.OK)
//    @PreAuthorize("hasRole('ROLE_USER')")
//    public ResponseMessage findMemberInfoById(@PathVariable("memberId") long memberId,
//                                              Authentication authentication) throws Exception {
//
//        Map<String, Object> param = new HashMap<>();
//        param.put("memberId", memberId);
//
//        Member member = adminMemberService.findMemberById(param);
//        return new ResponseMessage(HttpStatus.OK, member);
//    }
//
//
//    @GetMapping("/api/admin/notices")
//    @ResponseStatus(HttpStatus.OK)
//    @PreAuthorize("hasRole('ROLE_USER')")
//    public ResponseMessage findAllNotice(@RequestParam(value = "offset", defaultValue = "0") int offset,
//                                         @RequestParam(value = "size", defaultValue = "6") int size,
//                                         @RequestParam(value = "category", defaultValue = "NOTICE") String category,
//                                         Authentication authentication) throws Exception {
//
//        Map<String, Object> param = new HashMap<>();
//        param.put("offset", offset);
//        param.put("size", size);
//        param.put("category", category);
//
//        List<NoticeDTO> noticeDTOs = noticeService.findAllNotices(param);
//
//        return new ResponseMessage(HttpStatus.OK, noticeDTOs);
//    }
//}
