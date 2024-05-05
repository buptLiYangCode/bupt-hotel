package com.example.dto.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FrontendDetailFeesReqDTO {
    /*
    房间号
     */
    private String roomNumber;

    /*
    入住时间
     */
    private Long checkInTime;

    /*
    离开时间
     */
    private Long CheckOutTime;
}
