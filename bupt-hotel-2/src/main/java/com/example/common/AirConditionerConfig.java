package com.example.common;

import lombok.Data;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * 空调价表实体类(单例模式)
 */
@Data
public class AirConditionerConfig {
    /*
    空调模式：0表示制冷、1制热
    */
    private Integer mode;

    /*
    调温上限
     */
    private Integer temperatureUpperBound;

    /*
    调温下限
     */
    private Integer temperatureLowerBound;
    /*
    风速上限
     */
    private Integer windSpeedUpperBound;

    /*
    风速下限
     */
    private Integer windSpeedLowerBound;

    private Integer defaultTemperature;

    private Integer defaultWindSpeed;

    /*
    计价规则：每档风速: 每度温度：分钟价格
     */
    private LinkedList<HashMap<Integer, BigDecimal>> priceTable;

    private static AirConditionerConfig instance;

    private AirConditionerConfig() {
        // 初始化默认值
    }

    public static synchronized AirConditionerConfig getInstance() {
        if (instance == null) {
            instance = new AirConditionerConfig();
        }
        return instance;
    }
}