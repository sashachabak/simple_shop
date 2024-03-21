package com.tdoft.shop.dto.response;

import com.tdoft.shop.entity.file.AbstractFile;


public class FileResponseDto {
    private AbstractFile file;
    private String url;

    public FileResponseDto(AbstractFile file, String url) {
        this.file = file;
        this.url = url;
    }

    public String getFileName() {
        return this.file.getFileName();
    }

    public String getContentType() {
        return this.file.getContentType();
    }

    public String getUrl() {
        return this.url;
    }
}
