package com.example.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;


/**
 * 房间实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("t_room")
public class RoomDO {
    /*
    房间号
     */
    private String roomNumber;

    /*
    房间号
     */
    private String roomPassword;

    /*
    房间类型：0-标准间，1-大床房
     */
    private Integer roomType;

    /*
    房间预定时间集合，List<Date[2]>，Date[0]为预定开始时间，Date[1]为预定结束时间
     */
    private List<Date[]> reserveTimeList;
    /*
    房间中空调编号
     */
    private String airConditionerNumber;
    /*
    房间中是否使用空调
     */
    private Boolean isAirConditionerUsed;
}
