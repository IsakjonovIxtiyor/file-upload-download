package com.example.fileuploaddownload.service;

import com.example.fileuploaddownload.payload.ApiResponse;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartHttpServletRequest;

public interface AttachmentServiceInterface {
    public ApiResponse dbSave(MultipartHttpServletRequest request);
    public HttpEntity<?> getFromDb(Integer id);
    public ApiResponse serverSave(MultipartHttpServletRequest request);
    public ResponseEntity fromServer(Integer id);
}
