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
    private String userName;

    /** 旅客年龄 */
    private Long age;

    /** 旅客性别 */
    private String gender;

    /** 旅客身份证号 */
    private String idCardNumber;

    /** 房间类型 */
    private String roomType;

    /** 房间号 */
    private String roomNumber;

    /** 入住时间 */
    private Long checkInTime;

    /** 离开时间 */
    private Long checkOutTime;
}
