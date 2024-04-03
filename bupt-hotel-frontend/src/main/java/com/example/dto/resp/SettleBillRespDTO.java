package com.example.dto.resp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SettleBillRespDTO {

    /*
    房间单价
     */
    private BigDecimal roomPrice;
    /*
    住宿天数
     */
    private BigDecimal days;
    /*
    房费
     */
    private BigDecimal roomFee;
    /*
    空调费
     */
    private BigDecimal airConditionerFee;
    /*
    总费用
     */
    private BigDecimal totalFee;
}
