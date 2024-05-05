package com.example.dto.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FrontendFreeRoomReqDTO {
    /*
    房间类型：0-标准间，1-大床房
    */
    private Integer roomType;

    /*
    入住时间
     */
    private Long checkInTime;

    /*
    离开时间
     */
    private Long checkOutTime;
}
