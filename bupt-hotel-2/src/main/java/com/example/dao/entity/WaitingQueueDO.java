package com.example.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 等待队列，等待使用空调资源的空调请求放在该队列，调度员可以查看
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName("t_waiting_queue")
public class WaitingQueueDO {
    /*
    空调编号
     */
    private String acNumber;

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
    private Long requestTime;
}
