package com.example.task;

import com.example.common.SystemParam;
import com.example.dao.entity.AirConditionerDO;
import com.example.dao.entity.RunningQueueDO;
import com.example.dao.entity.WaitingQueueDO;
import com.example.dao.mapper.*;
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
    private final RoomMapper roomMapper;

    @Scheduled(fixedRate = 1000) // 每隔1000ms执行一次
    public void dispatchConnections() {
        long currentTime = System.currentTimeMillis();

//        List<RunningQueueDO> runningQueue1 = runningQueueMapper.getAll();
//        System.out.println(runningQueue1);
//        List<WaitingQueueDO> waitQueue1 = waitingQueueMapper.getAll();
//        System.out.println(waitQueue1);

        List<RunningQueueDO> runningQueue = runningQueueMapper.getAll();
        for (RunningQueueDO runningQueueDO : runningQueue) {
            String acNumber = runningQueueDO.getAcNumber();
            AirConditionerDO airConditionerDO = airConditionerMapper.select(acNumber);
            // 1.时间片耗尽 2.已经降低到目标温度，更新空调状态，结算空调费用
            long connectionTime = runningQueueDO.getConnectionTime();
            double currTemperature = roomMapper.select(airConditionerDO.getRoomNumber()).getCurrTemperature();
            double targetTemperature = airConditionerDO.getTemperature();
            boolean a = (currentTime - connectionTime) > SystemParam.TIME_SPLICE * 1000L;
            boolean b = currTemperature <= targetTemperature;
            if (a || b) {
                if (a)
                    System.out.println(acNumber + "时间片耗尽，释放资源");
                if (b)
                    System.out.println(acNumber + "降至目标温度，释放资源");
                // 断开连接，中央空调当前连接数 - 1
                SystemParam.CURR_CONNECTION_COUNT -= 1;
                // 时间片耗尽，移出运行表，移进等待表
                runningQueueMapper.delete(acNumber);
                if (!b) {
                    waitingQueueMapper.insert(WaitingQueueDO.builder()
                            .acNumber(acNumber)
                            .windSpeed(airConditionerDO.getWindSpeed())
                            .temperature(airConditionerDO.getTemperature())
                            .requestTime(currentTime)
                            .build());
                }
                // 修改空调状态信息
                airConditionerDO.setConnecting(false);
                airConditionerDO.setConnectionTime(currentTime);
                airConditionerMapper.update(airConditionerDO);
            }
        }


        List<WaitingQueueDO> waitQueue = waitingQueueMapper.getAll();
        // k 表示空闲资源个数
        int k = SystemParam.MAX_CONNECTION_COUNT - SystemParam.CURR_CONNECTION_COUNT;
        if (!waitQueue.isEmpty() && k > 0) {
            // 使用 Stream API 对 waitQueue 进行排序
            List<WaitingQueueDO> sortedWaitQueue = waitQueue.stream()
                    .sorted(Comparator.comparing(WaitingQueueDO::getWindSpeed, Comparator.reverseOrder()) // 风速大的排前面
                            .thenComparing(WaitingQueueDO::getRequestTime)) // 风速相同则按照请求时间早的排前面
                    .toList(); // 收集果结到新的列表
            // 将前k 个放入运行队列，移出等待队列，连接数 + 1
            for (int i = 0; i < k && sortedWaitQueue.size() > i; i++) {
                WaitingQueueDO waitingQueueDO = sortedWaitQueue.get(i);
                String acNumber = waitingQueueDO.getAcNumber();
                AirConditionerDO airConditionerDO = airConditionerMapper.select(acNumber);
                // 中央空调当前连接数 + 1
                SystemParam.CURR_CONNECTION_COUNT += 1;
                // 从等待队列进入服务队列
                waitingQueueMapper.delete(acNumber);
                runningQueueMapper.insert(RunningQueueDO.builder()
                        .acNumber(acNumber)
                        .connectionTime(currentTime)
                        .build());
                // 更新空调 连接时间字段
                airConditionerDO.setConnecting(true);
                airConditionerDO.setConnectionTime(currentTime);
                airConditionerDO.setTemperature(waitingQueueDO.getTemperature());
                airConditionerDO.setWindSpeed(waitingQueueDO.getWindSpeed());
                airConditionerMapper.update(airConditionerDO);
                System.out.println(acNumber + "获得资源");
            }
        }
    }
}