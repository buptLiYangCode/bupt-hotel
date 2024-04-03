package com.example.dto.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FrontendSettleBillReqDTO {
    /*
    旅客姓名
     */
    private String username;

    /*
    身份证号
     */
    private String idCardNumber;

    /*
    房间号
     */
    private String roomNumber;

    /*
    房间密码
     */
    private String roomPassword;
}
