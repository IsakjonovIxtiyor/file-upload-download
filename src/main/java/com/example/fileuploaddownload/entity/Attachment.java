package com.example.fileuploaddownload.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Attachment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private String contentType;
    private int size;
    private String path;

    public Attachment(String name, String contentType, int size) {
        this.name = name;
        this.contentType = contentType;
        this.size = size;
    }

    public Attachment(String name, String contentType, int size, String path) {
        this.name = name;
        this.contentType = contentType;
        this.size = size;
        this.path = path;
    }
}
