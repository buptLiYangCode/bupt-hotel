package com.example.dto.resp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FrontendDetailFeesRespDTO {
    /*
    空调编号
    */
    private Long acNumber;

    /*
    空调风速
    */
    private Integer windSpeed;

    /*
    空调温度
    */
    private Integer temperature;

    /*
    开始时间
     */
    private Long startTime;

    /*
    分钟数
     */
    private Long minutes;

    /*
    该段时间费用
     */
    private Double billing;
}
