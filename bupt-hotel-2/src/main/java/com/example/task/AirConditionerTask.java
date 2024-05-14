package com.example.task;

import com.example.common.SystemParam;
import com.example.dao.entity.RoomDO;
import com.example.dao.mapper.AirConditionerMapper;
import com.example.dao.mapper.RoomMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

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
                .filter(roomDO -> airConditionerMapper.select(roomDO.getAcNumber()).isConnecting())
                .toList();
        for (RoomDO roomDO : connectionList) {
            roomDO.setCurrTemperature(roomDO.getCurrTemperature() - SystemParam.TEM_CHANGE_PER_SECOND.get(airConditionerMapper.select(roomDO.getAcNumber()).getWindSpeed()));
        }
    }

}
