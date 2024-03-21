package com.tdoft.shop.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;

@Getter
public class JwtAuthenticationException extends AuthenticationException {

    private HttpStatus status;

    public JwtAuthenticationException(String explanation, HttpStatus status) {
        super(explanation);
        this.status = status;
    }

    public JwtAuthenticationException(String explanation) {
        super(explanation);
    }
}
