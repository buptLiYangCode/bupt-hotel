package com.example.common;

import java.util.HashMap;
import java.util.LinkedList;

import static com.example.common.constant.SystemConstant.SECONDS_PER_MINUTE;

/**
 *
 */
public class SystemParam {
    /*
    中央空调最大连接计数
     */
    public static int MAX_CONNECTION_COUNT = 3;
    /*
    中央空调当前连接计数
    */
    public static int CURR_CONNECTION_COUNT = 0;
    /*
    每次连接的最大时间ms
     */
    public static int TIME_SPLICE = 2 * SECONDS_PER_MINUTE;

    /*
    空调模式：0表示制冷、1制热
    */
    public static int MODE = 0;

    /*
    调温上限
     */
    public static double TEMPERATURE_UPPER_BOUND = 28.00;

    /*
    调温下限
     */
    public static double TEMPERATURE_LOWER_BOUND = 18.00;

    /*
    风速上限
     */
    public static int WIND_SPEED_UPPER_BOUND = 3;

    /*
    风速下限
     */
    public static int WIND_SPEED_LOWER_BOUND = 1;

    /*
    默认温度
     */
    public static double DEFAULT_TEMPERATURE = 25.00;

    /*
    默认风速
     */
    public static int DEFAULT_WIND_SPEED = 2;

    /*

     */
    public static LinkedList<Double> TEMP_CHANGE_PER_SECOND = new LinkedList<>();

    public static HashMap<String, Double> INITIAL_TEMP_TABLE = new HashMap<>();

    public static HashMap<String, Double> ROOM_PRICE_TABLE = new HashMap<>();

    public static Double PRICE_PER_TEMP = 1.00;

    static {
        TEMP_CHANGE_PER_SECOND.add(0.00);
        TEMP_CHANGE_PER_SECOND.add(1.00 / 3 / SECONDS_PER_MINUTE);
        TEMP_CHANGE_PER_SECOND.add(1.00 / 2 / SECONDS_PER_MINUTE);
        TEMP_CHANGE_PER_SECOND.add(1.00 / SECONDS_PER_MINUTE);

        INITIAL_TEMP_TABLE.put("1-01", 32.00);
        INITIAL_TEMP_TABLE.put("1-02", 28.00);
        INITIAL_TEMP_TABLE.put("1-03", 30.00);
        INITIAL_TEMP_TABLE.put("1-04", 29.00);
        INITIAL_TEMP_TABLE.put("1-05", 35.00);

        ROOM_PRICE_TABLE.put("1-01", 100.00);
        ROOM_PRICE_TABLE.put("1-02", 125.00);
        ROOM_PRICE_TABLE.put("1-03", 150.00);
        ROOM_PRICE_TABLE.put("1-04", 200.00);
        ROOM_PRICE_TABLE.put("1-05", 100.00);
    }
}
