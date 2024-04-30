package com.example.service.impl;

import com.example.common.SystemParam;
import com.example.dao.entity.AirConditionerDO;
import com.example.dao.entity.DetailedFeesDO;
import com.example.dao.entity.RunningQueueDO;
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
                DetailedFeesDO newDetailedFeesDO = getDetailedFeesDO(airConditionerDO);
                detailedFeesMapper.insert(newDetailedFeesDO);
                // 释放资源，更新空调信息，系统当前连接数 -1
                newAirConditionerDO.setConnecting(false);
                systemParam.setCurrConnectionCount(systemParam.getCurrConnectionCount() - 1);
                newAirConditionerDO.setCurrFee(airConditionerDO.getCurrFee()+newDetailedFeesDO.getFee());
                runningQueueMapper.delete(acNumber);
            } else {
                waitingQueueMapper.delete(acNumber);
            }
        }
        // 如果空调关着：需要打开，请求连接，更新空调信息
        if (!isOpening) {
            WaitingQueueDO waitingQueueDO = WaitingQueueDO.builder()
                    .acNumber(acNumber)
                    .temperature(systemParam.getDefaultTemperature())
                    .windSpeed(systemParam.getDefaultWindSpeed())
                    .requestTime(System.currentTimeMillis())
                    .build();
            waitingQueueMapper.insert(waitingQueueDO);
            log.info("插入" + waitingQueueDO);
        }
        airConditionerMapper.update(newAirConditionerDO);
    }

    /**
     * 更新温度。
     * @param acNumber 空调编号
     * @param targetTemperature 目标温度
     */
    @Override
    public void updateTemperature(String acNumber, Integer targetTemperature) {
        AirConditionerDO airConditionerDO = airConditionerMapper.get(acNumber);
        // 如果空调opening字段 = false
        if (!airConditionerDO.getOpening()) {
            return ;
        }
        long currentTime = System.currentTimeMillis();
        AirConditionerDO newAirConditionerDO = AirConditionerDO.builder()
                .acNumber(acNumber)
                .temperature(targetTemperature)
                // 调温调风时，也需要更新
                .connectionTime(currentTime)
                .build();
        if (airConditionerDO.getConnecting()) {
            DetailedFeesDO newDetailedFeesDO = getDetailedFeesDO(airConditionerDO);
            detailedFeesMapper.insert(newDetailedFeesDO);
            newAirConditionerDO.setCurrFee(airConditionerDO.getCurrFee()+newDetailedFeesDO.getFee());
            airConditionerMapper.update(newAirConditionerDO);
            // 更新运行队列
            RunningQueueDO runningQueueDO = new RunningQueueDO(acNumber, currentTime);
            runningQueueMapper.update(runningQueueDO);
        } else {
            waitingQueueMapper.update(newAirConditionerDO);
        }
    }

    @Override
    public void updateWindSpeed(String acNumber, Integer targetWindSpeed) {
        AirConditionerDO airConditionerDO = airConditionerMapper.get(acNumber);
        // 如果空调opening字段 = false
        if (!airConditionerDO.getOpening()) {
            return ;
        }
        long currentTime = System.currentTimeMillis();
        AirConditionerDO newAirConditionerDO = AirConditionerDO.builder()
                .acNumber(acNumber)
                .windSpeed(targetWindSpeed)
                // 调温调风时，也需要更新
                .connectionTime(currentTime)
                .build();
        if (!airConditionerDO.getOpening())
            return;
        if (airConditionerDO.getConnecting()) {
            DetailedFeesDO newDetailedFeesDO = getDetailedFeesDO(airConditionerDO);
            detailedFeesMapper.insert(newDetailedFeesDO);
            newAirConditionerDO.setCurrFee(airConditionerDO.getCurrFee()+newDetailedFeesDO.getFee());
            airConditionerMapper.update(newAirConditionerDO);
            // 更新运行队列
            RunningQueueDO runningQueueDO = new RunningQueueDO(acNumber, currentTime);
            runningQueueMapper.update(runningQueueDO);
        } else {
            waitingQueueMapper.update(newAirConditionerDO);
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
