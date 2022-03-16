package com.example.fileuploaddownload.service;

import com.example.fileuploaddownload.entity.Attachment;
import com.example.fileuploaddownload.entity.AttachmentContent;
import com.example.fileuploaddownload.payload.ApiResponse;
import com.example.fileuploaddownload.repository.AttachmentContentRepository;
import com.example.fileuploaddownload.repository.AttachmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
public class AttachmentService implements AttachmentServiceInterface {
    @Autowired
    private AttachmentRepository attachmentRepository;
    @Autowired
    private AttachmentContentRepository attachmentContentRepository;

    @Override
    public ApiResponse dbSave(MultipartHttpServletRequest request) {

        try {
            Iterator<String> fileNames = request.getFileNames();
            List<Integer> integerList = new ArrayList<>();
            while (fileNames.hasNext()) {
                MultipartFile file = request.getFile(fileNames.next());
                assert file != null;
                Attachment attachment = new Attachment(
                        file.getOriginalFilename(),
                        file.getContentType() != null ? file.getContentType() : "unknown",
                        (int) file.getSize()
                );
                Attachment saveAttachment = attachmentRepository.save(attachment);

                AttachmentContent attachmentContent = new AttachmentContent(
                        file.getBytes(),
                        saveAttachment
                );
                attachmentContentRepository.save(attachmentContent);
                integerList.add(saveAttachment.getId());
            }

            return new ApiResponse("save", true, integerList);
        } catch (Exception e) {
            return new ApiResponse("Error", false);
        }
    }

    @Override
    public ResponseEntity<?> getFromDb(Integer id) {
        Attachment attachment = attachmentRepository.findById(id).
                orElseThrow(() -> new IllegalStateException("getAttachment"));
        AttachmentContent byAttachmentId = attachmentContentRepository.findByAttachmentId(id);
        return ResponseEntity.ok().contentType(MediaType.valueOf(attachment.getContentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; fileName =/" + attachment.getName())
                .body(byAttachmentId.getBytes());
    }

    @Override
    public ApiResponse serverSave(MultipartHttpServletRequest request) {
        try {
            Iterator<String> fileNames = request.getFileNames();
            List<Integer> integerList = new ArrayList<>();
            while (fileNames.hasNext()) {
                MultipartFile file = request.getFile(fileNames.next());
                assert file != null;

                FileOutputStream fileOutputStream = new FileOutputStream("src/main/resources/appFiles" + file.getOriginalFilename());

                byte[] bytes = file.getBytes();
                fileOutputStream.write(bytes);
                fileOutputStream.close();
                Attachment attachment = new Attachment(
                        file.getOriginalFilename(),
                        file.getContentType() != null ? file.getContentType() : "unknown",
                        (int) file.getSize(),
                        "src/main/resources/appFiles" + file.getOriginalFilename()
                );
                Attachment saved = attachmentRepository.save(attachment);
                integerList.add(saved.getId());
            }
            return new ApiResponse("save", true, integerList);
        } catch (Exception e) {
            return new ApiResponse("Error", false);
        }
    }

    @Override
    public ResponseEntity fromServer(Integer id) {
        Attachment attachment = attachmentRepository.findById(id).
                orElseThrow(() -> new IllegalStateException("getAttachment"));

        try {
            File file = ResourceUtils.getFile("classpath:static/appFile" + attachment.getName());
            InputStream inputStream = new FileInputStream(file);
            byte[] bytes = FileCopyUtils.copyToByteArray(inputStream);
            return ResponseEntity.ok().contentType(MediaType.valueOf(attachment.getContentType()))
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            "attachment; fileName =/" + attachment.getName())
                    .body(bytes);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}
