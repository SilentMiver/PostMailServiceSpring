package com.example.demo.exceptions;

public class ParcelNotFoundException extends RuntimeException {
    public ParcelNotFoundException(Long id) {
        super("Could not find Parcel with id " + id);
    }
}
