package com.example.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("t_user")
public class UserDO {

    /** 旅客用户姓名 */
    private String userName;

    /** 用户年龄 */
    private Integer age;

    /** 用户性别 */
    private String gender;

    /** 用户身份证号 */
    private String idCardNumber;

    /** 房间类型 */
    private String roomType;

    /** 房间号 */
    private String roomNumber;

    /** 入住时间 */
    private Date checkInTime;

    /** 离开时间 */
    private Date checkOutTime;
}
