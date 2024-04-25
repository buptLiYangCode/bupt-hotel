package com.example.dto.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterReqDTO {
    /*
    旅客姓名
     */
    private String name;
    /*
    旅客年龄
     */
    private Integer age;
    /*
    旅客身份证号
     */
    private String idCard;
    /*
    旅客电话
     */
    private String phone;
    /*
    旅客性别：0-女，1-男
     */
    private Integer sex;
    /*
    房间类型
    */
    private Integer roomType;
    /*
    房间号
     */
    private String roomNumber;
    /*
    入住时间
     */
    private Date checkIntegerime;
    /*
    退房时间
     */
    private Date checkOutTime;
}
