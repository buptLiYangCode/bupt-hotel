package com.example.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdminQueryVO {
    /*
    空调编号
     */
    private String acNumber;

    private double currTemperature;

    private double targetTemperature;
    /*
    空调风速
    */
    private int windSpeed;

    /*
    当前旅客产生的费用
     */
    private double currFee;

    /*
    空调状态：0表示关闭，1表示打开未出风，2表示打开且出风
     */
    private int flag;
}
