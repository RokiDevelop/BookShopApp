package com.example.bookshopapp.system.exception;

public class ObjectNotFoundException extends RuntimeException {

    public ObjectNotFoundException(String objectName, Integer id) {
        super("Could noy find " + objectName + " with id " + id + ".");
    }

    public ObjectNotFoundException(String objectName, String id) {
        super("Could noy find " + objectName + " with id " + id + ".");
    }
}
