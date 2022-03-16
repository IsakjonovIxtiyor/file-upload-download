package com.example.fileuploaddownload.repository;

import com.example.fileuploaddownload.entity.AttachmentContent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttachmentContentRepository extends JpaRepository<AttachmentContent,Integer> {
    AttachmentContent findByAttachmentId(Integer attachment_id);
}
