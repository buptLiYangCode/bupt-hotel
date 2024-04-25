package com.example.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("t_room_reservation")
public class RoomReservationDO {
    private String roomNumber;
    private Long checkIntegerime;
    private Long checkOutTime;
}