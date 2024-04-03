package com.example.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName("t_air_conditioner")
public class AirConditionerDO {
    /*
    空调编号
     */
    private Long airConditionerNumber;

    /*
    空调模式：0表示制冷、1制热
     */
    private Integer mode;

    /*
    空调可以工作或停机检修：0表示可以工作、1停机检修
    */
    private Integer workable;

    /*
    空调状态【用户是否按下开关键】：0表示开启、1关闭、2没按开关键
    */
    private Integer status;

    /*
    空调状态【出风口是否出风】：0表示与中央空调建立连接、1关闭与中央空调的连接
    */
    private Integer realStatus;

    /*
    空调风速
    */
    private Integer windSpeed;

    /*
    空调目标温度
    */
    private Integer targetTemperature;

    /*
    调温上限
    */
    private Integer upperBound;

    /*
    调温上限
     */
    private Integer lowerBound;
    /*
    上一次操作时间
     */
    private Date lastOperationTime;

    /*
    当前用户产生的费用
     */
    private BigDecimal currFee;

    /*
    空调总费用
     */
    private BigDecimal totalFee;
}
