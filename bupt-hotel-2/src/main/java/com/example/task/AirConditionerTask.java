package com.example.task;

import com.example.dao.entity.AirConditionerDO;
import com.example.dao.entity.DetailedFeesDO;
import com.example.dao.entity.RoomDO;
import com.example.dao.mapper.AirConditionerMapper;
import com.example.dao.mapper.DetailedFeesMapper;
import com.example.dao.mapper.RoomMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

import static com.example.common.SystemParam.PRICE_PER_TEMP;
import static com.example.common.SystemParam.TEMP_CHANGE_PER_SECOND;
import static com.example.common.constant.SystemConstant.SECONDS_PER_MINUTE;

/**
 * 空调降温，空调打开且分配了资源
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class AirConditionerTask {
    private final AirConditionerMapper airConditionerMapper;
    private final RoomMapper roomMapper;
    private final DetailedFeesMapper detailedFeesMapper;

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
            Double tempChanged = TEMP_CHANGE_PER_SECOND.get(airConditionerMapper.select(roomDO.getAcNumber()).getWindSpeed());
            roomDO.setCurrTemperature(roomDO.getCurrTemperature() - tempChanged);
            roomMapper.update(roomDO);
            System.out.printf("房间%s温度下降至%.2f%n", roomDO.getRoomNumber(), roomDO.getCurrTemperature());
            // 更新费用
            AirConditionerDO airConditionerDO = airConditionerMapper.select(roomDO.getAcNumber());
            double fee = PRICE_PER_TEMP * tempChanged;
            airConditionerDO.setCurrFee(airConditionerDO.getCurrFee() + fee);
            airConditionerMapper.update(airConditionerDO);
            // 插入详单
            long endTime = System.currentTimeMillis();
            long startTime = endTime - 1000;
            detailedFeesMapper.insert(DetailedFeesDO.builder()
                    .acNumber(airConditionerDO.getAcNumber())
                    .windSpeed(airConditionerDO.getWindSpeed())
                    .startTime(startTime)
                    .endTime(endTime)
                    .minutes(1.00 / SECONDS_PER_MINUTE)
                    .fee(fee)
                    .feeRate(fee * SECONDS_PER_MINUTE)
                    .build());
        }
    }

}
