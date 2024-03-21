package com.tdoft.shop.dto.request;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class GoodRequestDto {

    private String title;

    private String size;

    private String composition;

    private BigDecimal price;

}
