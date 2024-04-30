package com.example.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private Boolean workable;

    /*
    空调打开或者关闭
    */
    private Boolean opening;

    /*
    该空调是否与中央空调建立连接
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
    这个字段在 1.和中央空调建立连接 2.调温调风 两种情况下都会更新
     */
    private Long connectionTime;

    /*
    当前用户产生的费用
     */
    private Double currFee;
}
