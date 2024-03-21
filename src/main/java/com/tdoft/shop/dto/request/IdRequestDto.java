package com.tdoft.shop.dto.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class IdRequestDto {
    @NotNull
    private Long id;
}
