package com.example.dao.mapper;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface RoomMapper {
    @Select("""
            SELECT room_number
            FROM t_room
            WHERE room_type = #{roomType}
            """)
    List<String> selectAllRooms(Integer roomType);
}

