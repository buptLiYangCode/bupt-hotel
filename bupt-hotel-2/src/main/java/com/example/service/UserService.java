package com.example.service;

public interface UserService {
    void openOrClose(String acNumber);

    void updateTemperature(String acNumber, Integer targetTemperature);

    void updateWindSpeed(String acNumber, Integer windSpeed);
}
