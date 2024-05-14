package com.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FrontendFreeRoomDTO {
    /*
    房间类型：0-标准间，1-大床房
    */
    private int roomType;

    /*
    入住时间
     */
    private long checkInTime;

    /*
    离开时间
     */
    private long checkOutTime;
}
