package com.clevershuttle.interviewchallenge.exception;

import static org.springframework.http.HttpStatus.NOT_FOUND;

public class EntityNotFoundException extends BaseException
{
    public EntityNotFoundException(String message)
    {
        super(message, NOT_FOUND);
    }


    @Override
    public String getErrorId()
    {
        return "entity-not-found";
    }
}
