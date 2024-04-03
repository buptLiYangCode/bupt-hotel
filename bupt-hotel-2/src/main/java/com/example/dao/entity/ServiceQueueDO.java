package com.example.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 服务队列，调度员可以查看
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("t_service_queue")
public class ServiceQueueDO {
    /*
    空调编号
     */
    private Long airConditionerNumber;

    /*
    空调获取中央空调连接的开始时间
     */
    private Date requestTime;
}
