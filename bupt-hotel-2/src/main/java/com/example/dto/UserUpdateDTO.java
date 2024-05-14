package com.example.dto;

import lombok.Data;

@Data
public class UserUpdateDTO {
    private String acNumber;

    private double targetTemperature;

    private int targetWindSpeed;
}
