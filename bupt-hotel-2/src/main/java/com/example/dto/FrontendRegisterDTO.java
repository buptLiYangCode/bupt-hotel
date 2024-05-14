package com.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FrontendRegisterDTO {
    /*
    旅客姓名
     */
    private String name;
    /*
    旅客年龄
     */
    private int age;
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
    private int sex;
    /*
    房间类型
    */
    private int roomType;
    /*
    房间号
     */
    private String roomNumber;
    /*
    入住时间
     */
    private long checkInTime;
    /*
    退房时间
     */
    private long checkOutTime;
}
