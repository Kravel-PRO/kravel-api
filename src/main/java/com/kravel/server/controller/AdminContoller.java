package com.kravel.server.controller;

import com.kravel.server.common.LogHandler;
import com.kravel.server.common.S3Uploader;
import com.kravel.server.common.util.message.Message;
import com.kravel.server.dto.update.media.MediaUpdateDTO;
import com.kravel.server.dto.update.place.PlaceUpdateDTO;
import com.kravel.server.dto.update.place.PlaceWebDTO;
import com.kravel.server.service.AdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AdminContoller {

    private final AdminService adminService;
    private final S3Uploader s3Uploader;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/api/places/{placeId}/update")
    public ResponseEntity<Message> findUpdateById(@PathVariable long placeId) throws Exception {
        PlaceWebDTO placeWebDTO = adminService.findUpdateById(placeId);
        return ResponseEntity.ok(new Message(placeWebDTO));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/api/places/{placeId}")
    public ResponseEntity<Message> updatePlace(@ModelAttribute PlaceUpdateDTO placeUpdateDTO,
                                               @PathVariable long placeId) throws Exception {
        adminService.updatePlace(placeUpdateDTO, placeId);
        return ResponseEntity.ok(new Message("succeed"));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/api/places")
    public ResponseEntity<Message> savePlace(@ModelAttribute PlaceUpdateDTO placeUpdateDTO,
                                             HttpServletRequest request) throws Exception {

        LogHandler.getClientIP(request);
        LogHandler.getRequestUrl(request);

        long placeId = adminService.savePlace(placeUpdateDTO);
        return ResponseEntity.ok(new Message(placeId));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/api/places/{placeId}")
    public ResponseEntity<Message> deletePlace(@PathVariable long placeId,
                                               HttpServletRequest request) throws Exception {
        LogHandler.getClientIP(request);
        LogHandler.getRequestUrl(request);

        adminService.deletePlace(placeId);
        return ResponseEntity.ok(new Message("remove succeed"));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/api/medias")
    public ResponseEntity<Message> saveMedia(@ModelAttribute MediaUpdateDTO mediaUpdateDTO) throws Exception {
        long placeId = adminService.saveMedia(mediaUpdateDTO);
        return ResponseEntity.ok(new Message(placeId));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/api/medias/{mediaId}")
    public ResponseEntity<Message> deleteMedia(@PathVariable long mediaId) throws Exception {
        adminService.deleteMedia(mediaId);
        return ResponseEntity.ok(new Message("remove succeed"));
    }
}
