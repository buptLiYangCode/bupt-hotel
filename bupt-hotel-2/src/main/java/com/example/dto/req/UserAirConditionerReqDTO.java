package com.example.dto.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAirConditionerReqDTO {
    /*
    空调编号
     */
    private Long airConditionerNumber;

    /*
    空调状态：0表示开启、1关闭
    */
    private Integer status;

    /*
    空调风速
    */
    private Integer windSpeed;

    /*
    空调温度
    */
    private Integer temperature;
}
