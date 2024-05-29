package com.example.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("t_user")
public class UserDO {

    /** 旅客旅客姓名 */
    private String name;

    /** 旅客年龄 */
    private long age;

    /** 旅客身份证号 */
    private String idCard;

    /** 旅客手机号 */
    private String phone;

    /** 旅客性别 */
    private int sex;

    /** 房间类型 */
    private int roomType;

    /** 入住天数 */
    private int days;

    /** 房间号 */
    private String roomNumber;

    /** 入住时间 */
    private long checkInTime;

    /** 离开时间 */
    private long checkOutTime;
}
