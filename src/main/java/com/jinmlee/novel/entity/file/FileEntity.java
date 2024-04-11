package com.jinmlee.novel.entity.file;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FileEntity {

    @Id @GeneratedValue
    @Column(name = "file_id")
    private Long id;

    private String uploadFileName;
    private String storeFileName;

    public FileEntity(String uploadFileName, String storeFileName){
        this.uploadFileName = uploadFileName;
        this.storeFileName = storeFileName;
    }
}
