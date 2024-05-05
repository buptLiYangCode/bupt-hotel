package com.example.dao.mapper;


import com.example.dao.entity.RoomDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface RoomMapper {
    @Select("select * from t_room where room_type = #{roomType}")
    List<RoomDO> selectAllRooms(Integer roomType);

    @Select("select * from t_room where room_number = #{roomNumber} ")
    RoomDO select(String roomNumber);
}

