package com.example.shop.exception;

public class NotFoundLoginIdException extends RuntimeException {
    public NotFoundLoginIdException(String s) {
        super(s);
    }
}
