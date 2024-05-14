package com.example.utils;

import com.example.common.SystemParam;
import com.example.dao.entity.AirConditionerDO;
import com.example.dao.entity.DetailedFeesDO;

public class CommonTool {

    public static DetailedFeesDO getDetailedFeesDO(AirConditionerDO airConditionerDO) {
        long currTime = System.currentTimeMillis();
        long connectionTime = airConditionerDO.getConnectionTime();
        long seconds = ((currTime - connectionTime) / 1000);
        int windSpeed = airConditionerDO.getWindSpeed();
        double temChangePerSecond = SystemParam.TEM_CHANGE_PER_SECOND.get(windSpeed);
        double fee = seconds * temChangePerSecond * SystemParam.PRICE;

        return DetailedFeesDO.builder()
                .acNumber(airConditionerDO.getAcNumber())
                .windSpeed(windSpeed)
                .startTime(connectionTime)
                .endTime(currTime)
                .seconds(seconds)
                .fee(fee)
                .build();
    }
}
