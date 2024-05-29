package com.example.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FrontendSettleBillVO {

    /*
    房间单价
     */
    private double roomPrice;

    /*
    入住时间
     */
    private long startTime;

    /*
    住宿天数
     */
    private int days;

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
