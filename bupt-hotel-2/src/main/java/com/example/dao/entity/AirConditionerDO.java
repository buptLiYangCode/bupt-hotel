package com.example.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName("t_air_conditioner")
public class AirConditionerDO {
    /*
    空调编号
     */
    private String acNumber;

    /*
    空调模式：0表示制冷、1制热
     */
    private Integer mode;

    /*
    空调可以工作或停机检修：1表示可以工作、0停机检修
    */
    private Integer working;

    /*
    空调打开或者关闭
    */
    private Boolean opening;

    /*

    */
    private Boolean connecting;

    /*
    空调风速
    */
    private Integer windSpeed;

    /*
    空调目标温度
    */
    private Integer temperature;

    /*
    上一次操作时间
     */
    private Long connectionTime;

    /*
    当前用户产生的费用
     */
    private BigDecimal currFee;
}
