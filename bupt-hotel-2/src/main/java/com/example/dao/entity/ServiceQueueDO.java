package com.example.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 服务队列，正在运行空调的状态信息，调度员可以查看
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("t_service_queue")
public class ServiceQueueDO {
    /*
    空调编号
     */
    private Long acNumber;

    /*
    空调获取中央空调连接的开始时间
     */
    private Long requestTime;
}
