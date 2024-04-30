package com.example.dto.resp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminQueryRespDTO {
    /*
    空调编号
     */
    private Long acNumber;

    /*
    空调模式：0表示制冷、1制热
     */
    private Integer mode;

    /*
    空调风速
    */
    private Integer windSpeed;

    /*
    空调温度
    */
    private Integer temperature;

    /*
    当前旅客产生的费用
     */
    private Double currFee;

    /*
    空调总费用
     */
    private Double totalFee;
}
