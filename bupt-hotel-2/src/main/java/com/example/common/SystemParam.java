package com.example.common;

import lombok.Data;

import java.util.HashMap;
import java.util.LinkedList;

/**
 * 空调价表实体类(单例模式)
 */
@Data
public class SystemParam {

    /*
    中央空调最大连接计数
     */
    private Integer maxConnectionCount;
    /*
    中央空调当前连接计数
    */
    private Integer currConnectionCount;
    /*
    每次连接的最大时间ms
     */
    private Long timeSplice;

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
    /*
    默认温度
     */
    private Integer defaultTemperature;
    /*
    默认风速
     */
    private Integer defaultWindSpeed;

    /*
    计价规则：每档风速: 每度温度：分钟价格
     */
    private LinkedList<HashMap<Integer, Double>> priceTable;

    private static SystemParam instance;

    private SystemParam() {
        maxConnectionCount = 3;
        currConnectionCount = 0;
        timeSplice = 5000L;
        mode = 0;
        temperatureUpperBound = 30;
        temperatureLowerBound = 20;
        defaultTemperature = 26;
        windSpeedUpperBound = 3;
        windSpeedLowerBound = 1;
        defaultWindSpeed = 1;

        priceTable = new LinkedList<>();
        priceTable.add(new HashMap<>());
        HashMap<Integer, Double> hashMap1 = new HashMap<>();
        hashMap1.put(21, 0.21);
        hashMap1.put(22, 0.22);
        hashMap1.put(23, 0.23);
        hashMap1.put(24, 0.24);
        hashMap1.put(25, 0.25);
        hashMap1.put(26, 0.26);
        hashMap1.put(27, 0.27);
        hashMap1.put(28, 0.28);
        hashMap1.put(29, 0.29);
        hashMap1.put(30, 0.30);
        priceTable.add(hashMap1);
        HashMap<Integer, Double> hashMap2 = new HashMap<>();
        hashMap2.put(21, 0.21 * 2);
        hashMap2.put(22, 0.22 * 2);
        hashMap2.put(24, 0.24 * 2);
        hashMap2.put(25, 0.25 * 2);
        hashMap2.put(23, 0.23 * 2);
        hashMap2.put(26, 0.26 * 2);
        hashMap2.put(27, 0.27 * 2);
        hashMap2.put(28, 0.28 * 2);
        hashMap2.put(29, 0.29 * 2);
        hashMap2.put(30, 0.30 * 2);
        priceTable.add(hashMap2);
        HashMap<Integer, Double> hashMap3 = new HashMap<>();
        hashMap3.put(21, 0.21 * 3);
        hashMap3.put(22, 0.22 * 3);
        hashMap3.put(23, 0.23 * 3);
        hashMap3.put(24, 0.24 * 3);
        hashMap3.put(25, 0.25 * 3);
        hashMap3.put(26, 0.26 * 3);
        hashMap3.put(27, 0.27 * 3);
        hashMap3.put(28, 0.28 * 3);
        hashMap3.put(29, 0.29 * 3);
        hashMap3.put(30, 0.30 * 3);
        priceTable.add(hashMap3);
        
    }

    public static synchronized SystemParam getInstance() {
        if (instance == null) {
            instance = new SystemParam();
        }
        return instance;
    }
}