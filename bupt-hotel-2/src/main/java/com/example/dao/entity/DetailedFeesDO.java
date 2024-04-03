package com.example.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 空调费用详表，用户使用空调，发送一个请求【开关机、调温、调风速】，就结算上一次的请求。一条记录以空调编号、开始时间作为唯一标识。
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("t_detailed_fees")
public class DetailedFeesDO {
    /*
    空调编号
    */
    private Long airConditionerNumber;

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
