package com.example.fileuploaddownload.controller;

import com.example.fileuploaddownload.payload.ApiResponse;
import com.example.fileuploaddownload.service.AttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@RestController
@RequestMapping("/api/attachment")
public class AttachmentController {

    @Autowired
    private AttachmentService attachmentService;

    @PostMapping("/dbSave")
    public HttpEntity<?> dbSave(MultipartHttpServletRequest request){
        ApiResponse apiResponse = attachmentService.dbSave(request);
        return ResponseEntity.ok().body(apiResponse);
    }
    @PostMapping("/serverSave")
    public HttpEntity<?> serverSave(MultipartHttpServletRequest request){
        ApiResponse apiResponse = attachmentService.serverSave(request);
        return ResponseEntity.ok().body(apiResponse);
    }

    @GetMapping("/fronDb/{id}")
    public HttpEntity<?> fromDb(@PathVariable Integer id){
        ResponseEntity<?> fromDb = attachmentService.getFromDb(id);
        return ResponseEntity.ok().body(fromDb);
    }
    @GetMapping("/fronServer/{id}")
    public HttpEntity<?> fromServer(@PathVariable Integer id){
        ResponseEntity<?> fromDb = attachmentService.fromServer(id);
        return ResponseEntity.ok().body(fromDb);
    }

}
