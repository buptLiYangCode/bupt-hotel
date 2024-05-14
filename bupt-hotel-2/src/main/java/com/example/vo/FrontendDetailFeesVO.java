package com.example.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FrontendDetailFeesVO {
    /*
    空调编号
    */
    private long acNumber;

    /*
    空调风速
    */
    private int windSpeed;

    /*
    空调温度
    */
    private int temperature;

    /*
    开始时间
     */
    private long startTime;

    /*
    分钟数
     */
    private long minutes;

    /*
    该段时间费用
     */
    private double billing;
}
