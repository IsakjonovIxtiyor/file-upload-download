package com.example.fileuploaddownload.repository;

import com.example.fileuploaddownload.entity.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttachmentRepository extends JpaRepository<Attachment,Integer> {
}
