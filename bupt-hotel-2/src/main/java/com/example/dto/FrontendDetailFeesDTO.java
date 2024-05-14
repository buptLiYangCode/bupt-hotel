package com.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FrontendDetailFeesDTO {
    /*
    房间号
     */
    private String roomNumber;

    /*
    入住时间
     */
    private long checkInTime;

    /*
    离开时间
     */
    private long CheckOutTime;
}
