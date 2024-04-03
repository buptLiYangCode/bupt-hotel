package com.example.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 等待队列
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("t_wait_queue")
public class WaitQueueDO {
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
    空调资源申请时间
     */
    private Date requestTime;
}
