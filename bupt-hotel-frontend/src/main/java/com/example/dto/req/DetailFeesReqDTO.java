package com.example.dto.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetailFeesReqDTO {
    /*
    房间号
     */
    private String roomNumber;

    /*
    入住时间
     */
    private Date checkInTime;

    /*
    离开时间
     */
    private Date CheckOutTime;
}
