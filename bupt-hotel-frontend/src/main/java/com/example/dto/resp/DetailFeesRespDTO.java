package com.example.dto.resp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetailFeesRespDTO {
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
    private Date startTime;

    /*
    分钟数
     */
    private Long minutes;

    /*
    该段时间费用
     */
    private BigDecimal billing;
}
