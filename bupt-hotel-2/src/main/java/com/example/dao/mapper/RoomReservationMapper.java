package com.example.dao.mapper;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface RoomReservationMapper {

    @Select("""
            SELECT DISTINCT room_number
            FROM t_room_reservation
            WHERE (check_in_time < #{checkOutTime} AND check_out_time > #{checkInTime})
            """)
    List<String> selectReservedRooms(@Param("checkInTime") Long checkInTime, @Param("checkOutTime") Long checkOutTime);
}
