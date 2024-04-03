package com.example.dto.resp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FrontendFreeRoomRespDTO {
    /** 是否还有房间 */
    private Boolean hasRoomsAvailable;
}
