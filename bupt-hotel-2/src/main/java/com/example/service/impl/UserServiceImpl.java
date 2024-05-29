package com.example.service.impl;

import com.example.common.SystemParam;
import com.example.dao.entity.AirConditionerDO;
import com.example.dao.entity.RoomDO;
import com.example.dao.entity.RunningQueueDO;
import com.example.dao.entity.WaitingQueueDO;
import com.example.dao.mapper.*;
import com.example.dto.UserUpdateDTO;
import com.example.service.UserService;
import com.example.vo.UserQueryVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final AirConditionerMapper airConditionerMapper;
    private final WaitingQueueMapper waitingQueueMapper;
    private final DetailedFeesMapper detailedFeesMapper;
    private final RunningQueueMapper runningQueueMapper;
    private final RoomMapper roomMapper;

    @Override
    public void openOrClose(String acNumber) {
        if (acNumber.charAt(0) != 'A') acNumber = "AC#" + acNumber;
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
                // 释放资源，更新空调信息，系统当前连接数 -1
                SystemParam.CURR_CONNECTION_COUNT -= 1;
                runningQueueMapper.delete(acNumber);

                airConditionerDO.setConnecting(false);
            } else {
                log.info("关闭空调{}，从等待队列中移出", acNumber);
                waitingQueueMapper.delete(acNumber);
            }
        }
        // 如果空调关着：需要打开，请求连接，更新空调信息
        if (!isOpening) {
            log.info("打开空调");
            // 当前温度 - 目标温度 >= 2 的空调不分配资源
            if (roomMapper.select(airConditionerDO.getRoomNumber()).getCurrTemperature() - airConditionerMapper.select(acNumber).getTemperature() > 1.99)
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
     *
     * @param userUpdateDTO 用户发送的更新请求类
     */
    @Override
    public void update(UserUpdateDTO userUpdateDTO) {
        if (userUpdateDTO.getAcNumber().charAt(0) != 'A')
            userUpdateDTO.setAcNumber("AC#" + userUpdateDTO.getAcNumber());
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
            // 更新空调信息
            airConditionerDO.setTemperature(userUpdateDTO.getTargetTemperature());
            airConditionerDO.setWindSpeed(userUpdateDTO.getTargetWindSpeed());
            airConditionerMapper.update(airConditionerDO);
        } else {
            log.info("空调{}没有出风，更新等待队列中的记录", userUpdateDTO.getAcNumber());
            WaitingQueueDO waitingQueueDO = waitingQueueMapper.select(userUpdateDTO.getAcNumber());
            if (waitingQueueDO != null)
                waitingQueueMapper.delete(userUpdateDTO.getAcNumber());
            else
                waitingQueueDO = WaitingQueueDO.builder()
                        .acNumber(userUpdateDTO.getAcNumber())
                        .build();
            waitingQueueDO.setTemperature(userUpdateDTO.getTargetTemperature());
            waitingQueueDO.setWindSpeed(userUpdateDTO.getTargetWindSpeed());
            waitingQueueDO.setRequestTime(currentTime);
            waitingQueueMapper.insert(waitingQueueDO);
        }
    }

    @Override
    public UserQueryVO query(String acNumber) {
        AirConditionerDO airConditionerDO = airConditionerMapper.select("AC#" + acNumber);
        RoomDO roomDO = roomMapper.select(airConditionerDO.getRoomNumber());
        int flag = 0;
        List<String> waitingList = waitingQueueMapper.getAll().stream()
                .map(WaitingQueueDO::getAcNumber)
                .toList();
        List<String> runningList = runningQueueMapper.getAll().stream()
                .map(RunningQueueDO::getAcNumber)
                .toList();
        if (runningList.contains(acNumber))
            flag = 2;
        if (waitingList.contains(acNumber))
            flag = 1;
        return new UserQueryVO(airConditionerDO.getTemperature(), roomDO.getCurrTemperature(), airConditionerDO.getWindSpeed(), airConditionerDO.getCurrFee(), flag);
    }

}
