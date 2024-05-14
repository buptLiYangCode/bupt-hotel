package com.example.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FrontendFreeRoomVO {
    /** 是否还有房间 */
    private boolean hasRoomsAvailable;
}
