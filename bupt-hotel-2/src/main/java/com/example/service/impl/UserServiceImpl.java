package com.example.service.impl;

import com.example.common.SystemParam;
import com.example.dao.entity.AirConditionerDO;
import com.example.dao.entity.DetailedFeesDO;
import com.example.dao.entity.WaitingQueueDO;
import com.example.dao.mapper.AirConditionerMapper;
import com.example.dao.mapper.DetailedFeesMapper;
import com.example.dao.mapper.RunningQueueMapper;
import com.example.dao.mapper.WaitingQueueMapper;
import com.example.dto.UserUpdateDTO;
import com.example.service.UserService;
import com.example.utils.CommonTool;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final AirConditionerMapper airConditionerMapper;
    private final WaitingQueueMapper waitingQueueMapper;
    private final DetailedFeesMapper detailedFeesMapper;
    private final RunningQueueMapper runningQueueMapper;

    @Override
    public void openOrClose(String acNumber) {
        AirConditionerDO airConditionerDO = airConditionerMapper.select(acNumber);
        boolean workable = airConditionerDO.isWorkable();
        boolean isOpening = airConditionerDO.isOpening();
        boolean isConnecting = airConditionerDO.isConnecting();

        if (!workable)
            throw new RuntimeException();
        // 如果空调开着：需要关闭，计算费用，更新空调信息，释放资源
        if (isOpening) {
            if (isConnecting) {
                log.info("关闭空调{}，释放空调连接", acNumber);
                DetailedFeesDO detailedFeesDO = CommonTool.getDetailedFeesDO(airConditionerDO);
                detailedFeesMapper.insert(detailedFeesDO);
                // 释放资源，更新空调信息，系统当前连接数 -1
                SystemParam.CURR_CONNECTION_COUNT -= 1;
                runningQueueMapper.delete(acNumber);

                airConditionerDO.setConnecting(false);
                airConditionerDO.setCurrFee(airConditionerDO.getCurrFee() + detailedFeesDO.getFee());
            } else {
                log.info("关闭空调{}，从等待队列中移出", acNumber);
                waitingQueueMapper.delete(acNumber);
            }
        }
        // 如果空调关着：需要打开，请求连接，更新空调信息
        if (!isOpening) {
            log.info("打开空调");
            waitingQueueMapper.insert(WaitingQueueDO.builder()
                    .acNumber(acNumber)
                    .temperature(SystemParam.DEFAULT_TEMPERATURE)
                    .windSpeed(SystemParam.DEFAULT_WIND_SPEED)
                    .requestTime(System.currentTimeMillis())
                    .build());
        }

        airConditionerDO.setOpening(!airConditionerDO.isOpening());
        airConditionerDO.setTemperature(SystemParam.DEFAULT_TEMPERATURE);
        airConditionerDO.setWindSpeed(SystemParam.DEFAULT_WIND_SPEED);
        airConditionerMapper.update(airConditionerDO);
    }

    /**
     * 用户调温调风
     * @param userUpdateDTO 用户发送的更新请求类
     */
    @Override
    public void update(UserUpdateDTO userUpdateDTO) {
        AirConditionerDO airConditionerDO = airConditionerMapper.select(userUpdateDTO.getAcNumber());
        boolean workable = airConditionerDO.isWorkable();
        boolean isOpening = airConditionerDO.isOpening();
        boolean isConnecting = airConditionerDO.isConnecting();

        if (!workable)
            throw new RuntimeException();
        // 如果空调是关闭的，提示用户先打开空调
        if (!isOpening) {
            throw new RuntimeException();
        }
        long currentTime = System.currentTimeMillis();
        if (isConnecting) {
            log.info("空调{}正在出风，更新温度为{}，风速为{}", userUpdateDTO.getAcNumber(), userUpdateDTO.getTargetTemperature(), userUpdateDTO.getTargetWindSpeed());
            DetailedFeesDO detailedFeesDO = CommonTool.getDetailedFeesDO(airConditionerDO);
            detailedFeesMapper.insert(detailedFeesDO);
            // 更新空调信息
            airConditionerDO.setTemperature(userUpdateDTO.getTargetTemperature());
            airConditionerDO.setWindSpeed(userUpdateDTO.getTargetWindSpeed());
            airConditionerDO.setCurrFee(airConditionerDO.getCurrFee() + detailedFeesDO.getFee());
        } else {
            log.info("空调{}没有出风，更新等待队列中的记录", userUpdateDTO.getAcNumber());
            WaitingQueueDO waitingQueueDO = waitingQueueMapper.select(userUpdateDTO.getAcNumber());
            waitingQueueMapper.delete(userUpdateDTO.getAcNumber());

            waitingQueueDO.setTemperature(userUpdateDTO.getTargetTemperature());
            waitingQueueDO.setWindSpeed(userUpdateDTO.getTargetWindSpeed());
            waitingQueueDO.setRequestTime(currentTime);
            waitingQueueMapper.insert(waitingQueueDO);
        }
    }

}
