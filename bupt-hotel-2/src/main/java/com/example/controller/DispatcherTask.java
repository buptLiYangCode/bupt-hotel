package com.example.controller;

import com.example.common.SystemParam;
import com.example.dao.entity.AirConditionerDO;
import com.example.dao.entity.DetailedFeesDO;
import com.example.dao.entity.RunningQueueDO;
import com.example.dao.entity.WaitingQueueDO;
import com.example.dao.mapper.AirConditionerMapper;
import com.example.dao.mapper.DetailedFeesMapper;
import com.example.dao.mapper.RunningQueueMapper;
import com.example.dao.mapper.WaitingQueueMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;

/**
 * 调度任务，定时更新等待队列、服务队列
 */

@Component
@Slf4j
@RequiredArgsConstructor
public class DispatcherTask {

    private final RunningQueueMapper runningQueueMapper;
    private final WaitingQueueMapper waitingQueueMapper;
    private final AirConditionerMapper airConditionerMapper;
    private final DetailedFeesMapper detailedFeesMapper;

    private final SystemParam systemParam = SystemParam.getInstance();

    @Scheduled(fixedRate = 1000000) // 每隔1000ms执行一次
    public void dispatchConnections() {
        long currentTime = System.currentTimeMillis();
        log.info("定时检查捏");

        List<RunningQueueDO> runningQueue = runningQueueMapper.getAll();
        if (!runningQueue.isEmpty()) {
            for (RunningQueueDO runningQueueDO : runningQueue) {
                String acNumber = runningQueueDO.getAcNumber();
                Long connectionTime = runningQueueDO.getConnectionTime();

                AirConditionerDO airConditionerDO = airConditionerMapper.get(acNumber);
                // 如果时间片耗尽，更新空调状态，结算空调费用
                if ((currentTime - connectionTime) > systemParam.getTimeSplice()) {
                    // 断开连接，中央空调当前连接数 - 1
                    systemParam.setCurrConnectionCount(systemParam.getCurrConnectionCount() - 1);
                    // 移出运行表，移进等待表
                    runningQueueMapper.delete(acNumber);
                    waitingQueueMapper.insert(WaitingQueueDO.builder()
                            .acNumber(acNumber)
                            .windSpeed(airConditionerDO.getWindSpeed())
                            .temperature(airConditionerDO.getTemperature())
                            .requestTime(currentTime)
                            .build());
                    // TODO 按秒计算费用
                    double price = systemParam.getPriceTable().get(airConditionerDO.getWindSpeed()).get(airConditionerDO.getTemperature());
                    long minutes = systemParam.getTimeSplice() / 1000;
                    double fee = price * minutes;
                    // 插入记录到 详细费用表
                    detailedFeesMapper.insert(DetailedFeesDO.builder()
                                    .acNumber(acNumber)
                                    .windSpeed(airConditionerDO.getWindSpeed())
                                    .temperature(airConditionerDO.getTemperature())
                                    .startTime(airConditionerDO.getConnectionTime())
                                    .minutes(minutes)
                                    .fee(fee)
                            .build());
                    // 修改空调状态信息
                    airConditionerDO.setCurrFee(airConditionerDO.getCurrFee() + fee);
                    airConditionerDO.setConnecting(false);
                    airConditionerDO.setConnectionTime(currentTime);
                    airConditionerMapper.update(airConditionerDO);
                }
            }
        }

        List<WaitingQueueDO> waitQueue = waitingQueueMapper.getAll();
        // k 表示空闲资源
        int k = systemParam.getMaxConnectionCount() - systemParam.getCurrConnectionCount();
        if (!waitQueue.isEmpty() && k > 0) {
            // 使用 Stream API 对 waitQueue 进行排序
            List<WaitingQueueDO> sortedWaitQueue = waitQueue.stream()
                    .sorted(Comparator.comparing(WaitingQueueDO::getWindSpeed, Comparator.reverseOrder()) // 风速大的排前面
                            .thenComparing(WaitingQueueDO::getRequestTime)) // 风速相同则按照请求时间早的排前面
                    .toList(); // 收集结果到新的列表
            // 将前k 个放入运行队列，移出等待队列，连接数 + 1
            for (int i = 0; i < k; i++) {
                // 中央空调当前连接数 + 1
                systemParam.setCurrConnectionCount(systemParam.getCurrConnectionCount() + 1);
                // TODO 什么问题？
                WaitingQueueDO waitingQueueDO = sortedWaitQueue.get(i);
                runningQueueMapper.insert(RunningQueueDO.builder()
                                .acNumber(waitingQueueDO.getAcNumber())
                                .connectionTime(currentTime)
                        .build());
                waitingQueueMapper.delete(waitingQueueDO.getAcNumber());
                // 更新空调 连接时间字段
                airConditionerMapper.update(AirConditionerDO.builder()
                        .acNumber(waitingQueueDO.getAcNumber())
                        .connectionTime(currentTime)
                        .build());
            }
        }
    }
}