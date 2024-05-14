package com.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FrontendSettleBillDTO {
    /*
    旅客姓名
     */
    private String username;

    /*
    身份证号
     */
    private String idCard;

    /*
    房间号
     */
    private String roomNumber;

    /*
    房间密码
     */
    private String roomPassword;
}
