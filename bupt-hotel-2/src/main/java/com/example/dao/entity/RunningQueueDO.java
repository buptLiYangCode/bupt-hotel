package com.example.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 服务队列，正在运行空调的状态信息，调度员可以查看
 * 服务队列，中的connectionTime字段 记录的是空调放进服务队列的时间
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("t_running_queue")
@EnableScheduling
public class RunningQueueDO {
    /*
    空调编号
     */
    private String acNumber;

    /*
    空调获取中央空调连接的开始时间
     */
    private Long connectionTime;
}
