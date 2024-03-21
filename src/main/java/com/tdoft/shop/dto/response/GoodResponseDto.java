package com.tdoft.shop.dto.response;

import com.tdoft.shop.entity.Good;
import lombok.Data;

import java.math.BigDecimal;

public class GoodResponseDto {
    private Good good;
    private FileResponseDto image;

    public GoodResponseDto(Good good, FileResponseDto image) {
        this.good = good;
        this.image = image;
    }

    public Long getId() {
        return this.good.getId();
    }

    public String getTitle() {
        return this.good.getTitle();
    }

    public String getSize() {
        return this.good.getSize();
    }

    public String getComposition() {
        return this.good.getComposition();
    }

    public BigDecimal getPrice() {
        return this.good.getPrice();
    }

    public FileResponseDto getImage() {
        return this.image;
    }

}
