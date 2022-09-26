package com.clevershuttle.interviewchallenge.exception;


import static org.springframework.http.HttpStatus.BAD_REQUEST;

public class LicensePlateExistException extends BaseException
{
    public LicensePlateExistException(String message)
    {
        super(message, BAD_REQUEST);
    }


    @Override
    public String getErrorId()
    {
        return "license-id-already-taken";
    }
}
