package com.example.dto.resp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.Double;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SettleBillRespDTO {

    /*
    房间单价
     */
    private Double roomPrice;
    /*
    住宿天数
     */
    private Double days;
    /*
    房费
     */
    private Double roomFee;
    /*
    空调费
     */
    private Double airConditionerFee;
    /*
    总费用
     */
    private Double totalFee;
}
