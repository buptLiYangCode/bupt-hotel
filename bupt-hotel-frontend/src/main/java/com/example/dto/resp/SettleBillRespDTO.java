package com.example.dto.resp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.double;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SettleBillRespDTO {

    /*
    房间单价
     */
    private double roomPrice;
    /*
    住宿天数
     */
    private double days;
    /*
    房费
     */
    private double roomFee;
    /*
    空调费
     */
    private double airConditionerFee;
    /*
    总费用
     */
    private double totalFee;
}
