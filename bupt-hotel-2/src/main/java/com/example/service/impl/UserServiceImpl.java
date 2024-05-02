package com.example.service.impl;

import com.example.common.SystemParam;
import com.example.dao.entity.AirConditionerDO;
import com.example.dao.entity.DetailedFeesDO;
import com.example.dao.entity.WaitingQueueDO;
import com.example.dao.mapper.AirConditionerMapper;
import com.example.dao.mapper.DetailedFeesMapper;
import com.example.dao.mapper.RunningQueueMapper;
import com.example.dao.mapper.WaitingQueueMapper;
import com.example.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedList;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final AirConditionerMapper airConditionerMapper;
    private final WaitingQueueMapper waitingQueueMapper;
    private final DetailedFeesMapper detailedFeesMapper;
    private final RunningQueueMapper runningQueueMapper;
    private final SystemParam systemParam = SystemParam.getInstance();

    @Override
    public void openOrClose(String acNumber) {
        System.out.println(systemParam.getPriceTable());


        AirConditionerDO airConditionerDO = airConditionerMapper.get(acNumber);
        boolean isOpening = airConditionerDO.getOpening();
        boolean isConnecting = airConditionerDO.getConnecting();

        AirConditionerDO newAirConditionerDO = AirConditionerDO.builder()
                .acNumber(acNumber)
                .opening(!isOpening)
                .temperature(systemParam.getDefaultTemperature())
                .windSpeed(systemParam.getDefaultWindSpeed())
                .build();
        // 如果空调开着：需要关闭，计算费用，更新空调信息，释放资源
        if (isOpening) {
            if (isConnecting) {
                log.info("关闭空调" + acNumber + "，释放空调连接");
                DetailedFeesDO detailedFeesDO = getDetailedFeesDO(airConditionerDO);
                detailedFeesMapper.insert(detailedFeesDO);
                // 释放资源，更新空调信息，系统当前连接数 -1
                systemParam.setCurrConnectionCount(systemParam.getCurrConnectionCount() - 1);
                runningQueueMapper.delete(acNumber);

                newAirConditionerDO.setConnecting(false);
                newAirConditionerDO.setCurrFee(airConditionerDO.getCurrFee() + detailedFeesDO.getFee());
            } else {
                waitingQueueMapper.delete(acNumber);
                log.info("关闭空调" + acNumber + "，从等待队列中移出");
            }
        }
        // 如果空调关着：需要打开，请求连接，更新空调信息
        if (!isOpening) {
            log.info("打开空调");
            waitingQueueMapper.insert(WaitingQueueDO.builder()
                    .acNumber(acNumber)
                    .temperature(systemParam.getDefaultTemperature())
                    .windSpeed(systemParam.getDefaultWindSpeed())
                    .requestTime(System.currentTimeMillis())
                    .build());
        }
        airConditionerMapper.update(newAirConditionerDO);
    }

    /**
     * 更新温度。
     *
     * @param acNumber          空调编号
     * @param targetTemperature 目标温度
     */
    @Override
    public void updateTemperature(String acNumber, Integer targetTemperature) {
        AirConditionerDO airConditionerDO = airConditionerMapper.get(acNumber);
        // 如果空调opening字段 = false
        if (!airConditionerDO.getOpening()) {
            return;
        }
        long currentTime = System.currentTimeMillis();
        if (airConditionerDO.getConnecting()) {
            DetailedFeesDO detailedFeesDO = getDetailedFeesDO(airConditionerDO);
            detailedFeesMapper.insert(detailedFeesDO);
            airConditionerMapper.update(AirConditionerDO.builder()
                    .acNumber(acNumber)
                    .temperature(targetTemperature)
                    // 调温调风时，也需要更新
                    .connectionTime(currentTime)
                    .currFee(airConditionerDO.getCurrFee() + detailedFeesDO.getFee())
                    .build());
            log.info("空调" + acNumber + "连接中，更新温度" +targetTemperature +"从等待队列中移出");
        } else {
            WaitingQueueDO newWaitingQueueDO = WaitingQueueDO.builder()
                    .acNumber(acNumber)
                    .windSpeed(systemParam.getDefaultWindSpeed())
                    .temperature(targetTemperature)
                    .requestTime(currentTime)
                    .build();
            WaitingQueueDO waitingQueueDO = waitingQueueMapper.select(acNumber);
            if (waitingQueueDO != null) {
                newWaitingQueueDO.setWindSpeed(waitingQueueDO.getWindSpeed());
                newWaitingQueueDO.setRequestTime(waitingQueueDO.getRequestTime());
                waitingQueueMapper.delete(acNumber);
            }
            waitingQueueMapper.insert(newWaitingQueueDO);
            log.info("空调" + acNumber + "在等待队列中，更新温度" +targetTemperature);
        }
    }

    @Override
    public void updateWindSpeed(String acNumber, Integer targetWindSpeed) {
        AirConditionerDO airConditionerDO = airConditionerMapper.get(acNumber);
        // 如果空调opening字段 = false
        if (!airConditionerDO.getOpening()) {
            return;
        }
        long currentTime = System.currentTimeMillis();
        if (airConditionerDO.getConnecting()) {
            DetailedFeesDO detailedFeesDO = getDetailedFeesDO(airConditionerDO);
            detailedFeesMapper.insert(detailedFeesDO);
            airConditionerMapper.update(AirConditionerDO.builder()
                    .acNumber(acNumber)
                    .windSpeed(targetWindSpeed)
                    // 调温调风时，也需要更新
                    .connectionTime(currentTime)
                    .currFee(airConditionerDO.getCurrFee() + detailedFeesDO.getFee())
                    .build());
        } else {
            WaitingQueueDO newWaitingQueueDO = WaitingQueueDO.builder()
                    .acNumber(acNumber)
                    .windSpeed(targetWindSpeed)
                    .temperature(systemParam.getDefaultTemperature())
                    .requestTime(currentTime)
                    .build();
            WaitingQueueDO waitingQueueDO = waitingQueueMapper.select(acNumber);
            if (waitingQueueDO != null) {
                newWaitingQueueDO.setTemperature(waitingQueueDO.getTemperature());
                newWaitingQueueDO.setRequestTime(waitingQueueDO.getRequestTime());
            }
            waitingQueueMapper.insert(waitingQueueDO);
        }
    }


    private DetailedFeesDO getDetailedFeesDO(AirConditionerDO airConditionerDO) {
        long currTime = System.currentTimeMillis();
        long lastConnectionTime = airConditionerDO.getConnectionTime();
        // TODO 这里也是 按秒算钱
        long minutes = (lastConnectionTime + currTime) / (1000);
        int lastTemperature = airConditionerDO.getTemperature();
        int lastWindSpeed = airConditionerDO.getWindSpeed();
        // 计算费用
        LinkedList<HashMap<Integer, Double>> priceTable = systemParam.getPriceTable();
        Double pricePerMinute = priceTable.get(lastWindSpeed).get(lastTemperature);
        Double fee = pricePerMinute * minutes;
        return DetailedFeesDO.builder()
                .acNumber(airConditionerDO.getAcNumber())
                .windSpeed(lastWindSpeed)
                .temperature(lastTemperature)
                .startTime(lastConnectionTime)
                .minutes(minutes)
                .fee(fee)
                .build();
    }
}
