package com.example.dao.mapper;


import com.example.dao.entity.RoomDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.LinkedList;

@Mapper
public interface RoomMapper {

    @Select("select * from t_room where room_number = #{roomNumber} ")
    RoomDO select(String roomNumber);

    @Select("select * from t_room")
    LinkedList<RoomDO> getAll();

    @Update("update t_room set curr_temperature = #{currTemperature} where room_number = #{roomNumber}")
    void update(RoomDO roomDO);

    @Update("update t_room set emptyy = #{emptyy} where room_number = #{roomNumber}")
    void updateEmptyy(@Param("roomNumber") String roomNumber, @Param("emptyy")boolean emptty);
}

