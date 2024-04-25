package com.example.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * 房间实体类，一个房间对应一个空调
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
    房间中空调编号
     */
    private String acNumber;
    /*
    房间中是否使用空调
     */
    private Boolean AirConditionerUsed;
}
