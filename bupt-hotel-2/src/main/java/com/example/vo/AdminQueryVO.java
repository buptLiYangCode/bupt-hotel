package com.example.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminQueryVO {
    /*
    空调编号
     */
    private long acNumber;

    /*
    空调模式：0表示制冷、1制热
     */
    private int mode;

    /*
    空调风速
    */
    private int windSpeed;

    /*
    空调温度
    */
    private int temperature;

    /*
    当前旅客产生的费用
     */
    private double currFee;

    /*
    空调总费用
     */
    private double totalFee;
}
