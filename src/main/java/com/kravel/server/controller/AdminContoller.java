package com.kravel.server.controller;

import com.kravel.server.common.LogHandler;
import com.kravel.server.common.S3Uploader;
import com.kravel.server.common.util.message.Message;
import com.kravel.server.dto.update.media.MediaUpdateDTO;
import com.kravel.server.dto.update.place.PlaceUpdateDTO;
import com.kravel.server.service.AdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AdminContoller {

    private final AdminService adminService;
    private final S3Uploader s3Uploader;

    @PostMapping("/admin/places")
    public ResponseEntity<Message> savePlace(@ModelAttribute PlaceUpdateDTO placeUpdateDTO,
                                             HttpServletRequest request) throws Exception {

        LogHandler.getClientIP(request);
        LogHandler.getRequestUrl(request);

        long placeId = adminService.savePlace(placeUpdateDTO);
        return ResponseEntity.ok(new Message(placeId));
    }

    @DeleteMapping("/admin/places/{placeId}")
    public ResponseEntity<Message> deletePlace(@PathVariable long placeId,
                                               HttpServletRequest request) throws Exception {
        LogHandler.getClientIP(request);
        LogHandler.getRequestUrl(request);

        adminService.deletePlace(placeId);
        return ResponseEntity.ok(new Message("remove succeed"));
    }

    @PostMapping("/admin/medias")
    public ResponseEntity<Message> saveMedia(@ModelAttribute MediaUpdateDTO mediaUpdateDTO) throws Exception {
        long placeId = adminService.saveMedia(mediaUpdateDTO);
        return ResponseEntity.ok(new Message(placeId));
    }

    @DeleteMapping("/admin/medias/{mediaId}")
    public ResponseEntity<Message> deleteMedia(@PathVariable long mediaId) throws Exception {
        adminService.deleteMedia(mediaId);
        return ResponseEntity.ok(new Message("remove succeed"));
    }
}
