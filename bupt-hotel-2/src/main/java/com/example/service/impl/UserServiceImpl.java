package com.example.service.impl;

import com.example.dao.entity.AirConditionerDO;
import com.example.common.AirConditionerConfig;
import com.example.dao.entity.DetailedFeesDO;
import com.example.dao.mapper.*;
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
    private final WaitQueueMapper waitQueueMapper;
    private final DetailedFeesMapper detailedFeesMapper;
    private final ServiceQueueMapper serviceQueueMapper;
    private final AirConditionerConfig airConditionerConfig = AirConditionerConfig.getInstance();


    @Override
    public void openOrClose(String acNumber) {

        AirConditionerDO airConditionerDO = airConditionerMapper.getAirConditioner(acNumber);
        boolean isOpening = airConditionerDO.getOpening();
        boolean isConnecting = airConditionerDO.getConnecting();

        long currOperationTime = System.currentTimeMillis();
        AirConditionerDO newAirConditionerDO = AirConditionerDO.builder()
                .opening(!isOpening)
                .lastOperationTime(currOperationTime)
                .build();
        // 如果空调开着：需要关闭，计算费用，更新空调信息，释放资源
        if (isOpening && isConnecting) {
            long lastOperationTime = airConditionerDO.getLastOperationTime();
            long minutes = (lastOperationTime + currOperationTime) / (1000 * 60);
            int lastTemperature = airConditionerDO.getTemperature();
            int lastWindSpeed = airConditionerDO.getWindSpeed();
            // 计算费用
            LinkedList<HashMap<Integer, BigDecimal>> priceTable = airConditionerConfig.getPriceTable();
            BigDecimal pricePerMinute = priceTable.get(lastWindSpeed).get(lastTemperature);
            BigDecimal fee = pricePerMinute.multiply(BigDecimal.valueOf(minutes));
            DetailedFeesDO detailedFeesDO = DetailedFeesDO.builder()
                    .acNumber(acNumber)
                    .windSpeed(lastWindSpeed)
                    .temperature(lastTemperature)
                    .startTime(lastOperationTime)
                    .minutes(minutes)
                    .billing(fee)
                    .build();
            detailedFeesMapper.insert(detailedFeesDO);
            // 释放资源，更新空调信息
            newAirConditionerDO.setConnecting(false);
            newAirConditionerDO.setTemperature(airConditionerConfig.getDefaultTemperature());
            newAirConditionerDO.setWindSpeed(airConditionerConfig.getDefaultWindSpeed());
            serviceQueueMapper.delete(acNumber);
        }
        // 如果空调关着：需要打开，请求连接，更新空调信息
        if (!isOpening) {

        }
        airConditionerMapper.update(newAirConditionerDO);

    }

    @Override
    public void updateTemperature(String acNumber, Integer targetTemperature) {

    }

    @Override
    public void updateWindSpeed(String acNumber, Integer windSpeed) {

    }
}
