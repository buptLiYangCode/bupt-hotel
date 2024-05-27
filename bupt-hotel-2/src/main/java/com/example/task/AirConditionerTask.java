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

/**
 * 空调降温，空调打开且分配了资源
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class AirConditionerTask {
    private final AirConditionerMapper airConditionerMapper;
    private final RoomMapper roomMapper;


    @Scheduled(fixedRate = 1000) // 每隔1000ms执行一次
    public void adjustTemperature() {

        LinkedList<RoomDO> roomDOs = roomMapper.getAll();
        List<RoomDO> connectionList = roomDOs.stream()
                .filter(roomDO -> {
                    AirConditionerDO airConditionerDO = airConditionerMapper.select(roomDO.getAcNumber());
                    return airConditionerDO.isOpening() && airConditionerDO.isConnecting();
                })
                .toList();
        for (RoomDO roomDO : connectionList) {
            roomDO.setCurrTemperature(roomDO.getCurrTemperature() - SystemParam.TEMP_CHANGE_PER_SECOND.get(airConditionerMapper.select(roomDO.getAcNumber()).getWindSpeed()));
            roomMapper.update(roomDO);
            System.out.printf("房间%s温度下降至%.2f%n", roomDO.getRoomNumber(), roomDO.getCurrTemperature());
        }
    }

}
