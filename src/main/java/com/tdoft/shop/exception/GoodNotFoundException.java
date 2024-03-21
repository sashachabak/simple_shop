package com.tdoft.shop.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "No good found with such id")
public class GoodNotFoundException extends RuntimeException {
    public GoodNotFoundException(Long id) {
        super(String.format("No good found with id %s", id));
    }
}
