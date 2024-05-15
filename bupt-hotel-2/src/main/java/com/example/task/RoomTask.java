package com.example.task;

import com.example.common.SystemParam;
import com.example.dao.entity.AirConditionerDO;
import com.example.dao.entity.RoomDO;
import com.example.dao.entity.WaitingQueueDO;
import com.example.dao.mapper.AirConditionerMapper;
import com.example.dao.mapper.RoomMapper;
import com.example.dao.mapper.WaitingQueueMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

import static com.example.common.constant.SystemConstant.SECONDS_PER_MINUTE;


/**
 * 1.空调关机且当前温度< 房间初始温度,需要回温
 * 2.降至目标温度后释放连接，当前温度 < 房间初始温度， 需要回温
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class RoomTask {
    private final AirConditionerMapper airConditionerMapper;
    private final RoomMapper roomMapper;
    private final WaitingQueueMapper waitingQueueMapper;

    @Scheduled(fixedRate = 1000) // 每隔1000ms执行一次
    public void restoreTemperature() {
        LinkedList<RoomDO> roomDOs = roomMapper.getAll();
        List<RoomDO> roomDOList = roomDOs.stream()
                .filter(roomDO -> {
                    AirConditionerDO airConditionerDO = airConditionerMapper.select(roomDO.getAcNumber());
                    return !airConditionerDO.isOpening() && roomDO.getCurrTemperature() < SystemParam.INITIAL_TEMP_TABLE.get(roomDO.getRoomNumber()) || airConditionerDO.isOpening() && !airConditionerDO.isConnecting() && roomDO.getCurrTemperature() < SystemParam.INITIAL_TEMP_TABLE.get(roomDO.getRoomNumber());
                })
                .toList();
        for (RoomDO roomDO : roomDOList) {
            roomDO.setCurrTemperature(roomDO.getCurrTemperature() + (0.5 / SECONDS_PER_MINUTE));
            roomMapper.update(roomDO);
            System.out.printf("房间%s温度回升至%.2f%n", roomDO.getRoomNumber(), roomDO.getCurrTemperature());
            // 在第2种情况下，回温超过2.00，在waitingQueue放出风请求
            AirConditionerDO airConditionerDO = airConditionerMapper.select(roomDO.getAcNumber());
            if (airConditionerDO.isOpening() && roomDO.getCurrTemperature() - airConditionerDO.getTemperature() > 2.00) {
                waitingQueueMapper.insert(WaitingQueueDO.builder()
                        .acNumber(airConditionerDO.getAcNumber())
                        .temperature(airConditionerDO.getTemperature())
                        .windSpeed(airConditionerDO.getWindSpeed())
                        .requestTime(System.currentTimeMillis())
                        .build());
            }
        }
    }
}

