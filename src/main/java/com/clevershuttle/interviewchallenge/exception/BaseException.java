package com.clevershuttle.interviewchallenge.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public abstract class BaseException extends RuntimeException
{
    private final String message;
    private final HttpStatus status;

    public abstract String getErrorId();
}
