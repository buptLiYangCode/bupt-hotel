package com.example.dto.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FreeRoomReqDTO {
    /** 请求的房间类型。 */
    private Integer roomType;

    /**
     * 请求的住宿时间段。
     * 此数组应包含两个 Date 对象，表示住宿期间的开始和结束日期。
     */
    private Date[] stayDuration;
}
