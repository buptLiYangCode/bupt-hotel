package com.example.service.impl;

import com.example.common.SystemConfig;
import com.example.dao.entity.AirConditionerDO;
import com.example.dao.entity.DetailedFeesDO;
import com.example.dao.entity.WaitQueueDO;
import com.example.dao.mapper.AirConditionerMapper;
import com.example.dao.mapper.DetailedFeesMapper;
import com.example.dao.mapper.RunningQueueMapper;
import com.example.dao.mapper.WaitingQueueMapper;
import com.example.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedList;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final AirConditionerMapper airConditionerMapper;
    private final WaitingQueueMapper waitingQueueMapper;
    private final DetailedFeesMapper detailedFeesMapper;
    private final RunningQueueMapper runningQueueMapper;
    private final SystemConfig systemConfig = SystemConfig.getInstance();

    @Override
    public void openOrClose(String acNumber) {
        AirConditionerDO airConditionerDO = airConditionerMapper.get(acNumber);
        boolean isOpening = airConditionerDO.getOpening();
        boolean isConnecting = airConditionerDO.getConnecting();

        AirConditionerDO newAirConditionerDO = AirConditionerDO.builder()
                .opening(!isOpening)
                .temperature(systemConfig.getDefaultTemperature())
                .windSpeed(systemConfig.getDefaultWindSpeed())
                .build();
        // 如果空调开着：需要关闭，计算费用，更新空调信息，释放资源
        if (isOpening) {
            if (isConnecting) {
                DetailedFeesDO newDetailedFeesDO = getDetailedFeesDO(airConditionerDO);
                detailedFeesMapper.insert(newDetailedFeesDO);
                // 释放资源，更新空调信息
                newAirConditionerDO.setConnecting(false);
                newAirConditionerDO.setCurrFee(airConditionerDO.getCurrFee().add(newDetailedFeesDO.getFee()));
                runningQueueMapper.delete(acNumber);
            } else {
                waitingQueueMapper.delete(acNumber);
            }
        }
        // 如果空调关着：需要打开，请求连接，更新空调信息
        if (!isOpening) {
            WaitQueueDO waitQueueDO = WaitQueueDO.builder()
                    .acNumber(acNumber)
                    .temperature(systemConfig.getDefaultTemperature())
                    .windSpeed(systemConfig.getDefaultWindSpeed())
                    .requestTime(System.currentTimeMillis())
                    .build();
            waitingQueueMapper.insertRequest(waitQueueDO);
        }
        airConditionerMapper.update(newAirConditionerDO);
    }


    @Override
    public void updateTemperature(String acNumber, Integer targetTemperature) {
        AirConditionerDO airConditionerDO = airConditionerMapper.get(acNumber);
        AirConditionerDO newAirConditionerDO = AirConditionerDO.builder()
                .acNumber(acNumber)
                .temperature(targetTemperature)
                .build();
        if (!airConditionerDO.getOpening())
            return;
        if (airConditionerDO.getConnecting()) {
            DetailedFeesDO newDetailedFeesDO = getDetailedFeesDO(airConditionerDO);
            detailedFeesMapper.insert(newDetailedFeesDO);
            newAirConditionerDO.setCurrFee(airConditionerDO.getCurrFee().add(newDetailedFeesDO.getFee()));
            airConditionerMapper.update(newAirConditionerDO);
            runningQueueMapper.update(newAirConditionerDO);
        } else {
            waitingQueueMapper.update(newAirConditionerDO);
        }
    }

    @Override
    public void updateWindSpeed(String acNumber, Integer windSpeed) {
        AirConditionerDO airConditionerDO = airConditionerMapper.get(acNumber);
        AirConditionerDO newAirConditionerDO = AirConditionerDO.builder()
                .acNumber(acNumber)
                .windSpeed(windSpeed)
                .build();
        if (!airConditionerDO.getOpening())
            return;
        if (airConditionerDO.getConnecting()) {
            DetailedFeesDO newDetailedFeesDO = getDetailedFeesDO(airConditionerDO);
            detailedFeesMapper.insert(newDetailedFeesDO);
            newAirConditionerDO.setCurrFee(airConditionerDO.getCurrFee().add(newDetailedFeesDO.getFee()));
            airConditionerMapper.update(newAirConditionerDO);
            runningQueueMapper.update(newAirConditionerDO);
        } else {
            waitingQueueMapper.update(newAirConditionerDO);
        }
    }


    private DetailedFeesDO getDetailedFeesDO(AirConditionerDO airConditionerDO) {
        long currTime = System.currentTimeMillis();
        long lastConnectionTime = airConditionerDO.getConnectionTime();
        long minutes = (lastConnectionTime + currTime) / (1000 * 60);
        int lastTemperature = airConditionerDO.getTemperature();
        int lastWindSpeed = airConditionerDO.getWindSpeed();
        // 计算费用
        LinkedList<HashMap<Integer, BigDecimal>> priceTable = systemConfig.getPriceTable();
        BigDecimal pricePerMinute = priceTable.get(lastWindSpeed).get(lastTemperature);
        BigDecimal fee = pricePerMinute.multiply(BigDecimal.valueOf(minutes));
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
