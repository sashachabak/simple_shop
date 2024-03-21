package com.tdoft.shop.entity.file;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@Data
@MappedSuperclass
public abstract class AbstractFile {

    @Column
    private String s3BucketFilePath;

    @JsonIgnore
    @Column
    private String fileName;

    @Column
    private String contentType;

}
