package com.example.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserQueryVO {
    private double targetTemperature;
    private double currTemperature;
    private int windSpeed;
    private double fee;
    // 为0表示空调关机，不显示前四个信息。1表示空调等待中，2表示运行中
    private int flag;
}
