package com.example.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 空调费用详表，旅客使用空调，发送一个请求【开关机、调温、调风速】，就结算上一次的请求。一条记录以空调编号、开始时间作为唯一标识。
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName("t_detailed_fees")
public class DetailedFeesDO {
    /*
    空调编号
    */
    private String acNumber;

    /*
    空调风速
    */
    private int windSpeed;

    /*
    开始时间
     */
    private long startTime;
    /*
    结束时间
     */
    private long endTime;

    /*
    分钟数
     */
    private long minutes;

    /*
    该段时间费用
     */
    private double fee;

    /*
    费率
     */
    private double feeRate;

}
