package com.example.demo.exception;

public class SensorStationNotFoundException extends RuntimeException {
    public SensorStationNotFoundException(Long id) {
        super("SensorStation not found with id: " + id);
    }
}
