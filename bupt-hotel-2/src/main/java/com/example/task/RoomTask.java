package com.example.task;

import com.example.common.SystemParam;
import com.example.dao.entity.AirConditionerDO;
import com.example.dao.entity.RoomDO;
import com.example.dao.mapper.AirConditionerMapper;
import com.example.dao.mapper.RoomMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

import static com.example.common.constant.SystemConstant.SECONDS_PER_MINUTE;


/**
 * 1.空调关机且当前温度不等于默认温度的房间需要回温
 * 2.空调打开，当前|温度 - 目标温度| < 2， 需要回温
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class RoomTask {
    private final AirConditionerMapper airConditionerMapper;
    private final RoomMapper roomMapper;

    @Scheduled(fixedRate = 1000) // 每隔1000ms执行一次
    public void restoreTemperature() {
        LinkedList<RoomDO> roomDOs = roomMapper.getAll();
        List<RoomDO> roomDOList = roomDOs.stream()
                .filter(roomDO -> {
                    AirConditionerDO airConditionerDO = airConditionerMapper.select(roomDO.getAcNumber());
                    return !airConditionerDO.isOpening() && Math.abs(roomDO.getCurrTemperature() - SystemParam.DEFAULT_TEMPERATURE) >= 0.01 || airConditionerDO.isOpening() && Math.abs(roomDO.getCurrTemperature() - airConditionerDO.getTemperature()) < 2.00;
                })
                .toList();
        for (RoomDO roomDO : roomDOList) {
            roomDO.setCurrTemperature(roomDO.getCurrTemperature() + (0.5 / SECONDS_PER_MINUTE));
            System.out.printf("房间%s温度回升至%.2f%n", roomDO.getRoomNumber(), roomDO.getCurrTemperature());
        }
    }
}

