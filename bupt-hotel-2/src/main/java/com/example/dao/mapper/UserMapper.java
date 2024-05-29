package com.example.dao.mapper;

import com.example.dao.entity.UserDO;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {

    @Insert(" INSERT INTO t_user (name, age, id_card, phone, sex, room_type, days, room_number, check_in_time, check_out_time)" +
            " VALUES (#{name}, #{age}, #{idCard}, #{phone}, #{sex}, #{roomType}, #{days}, #{roomNumber}, #{checkInTime}, #{checkOutTime})")
    void insert(UserDO userDO);

    @Select("select * from t_user where id_card = #{idCard}")
    UserDO select(String idCard);

    @Update("update t_user set check_out_time = #{checkOutTime} where id_card = #{idCard}")
    void update(@Param("idCard")String idCard, @Param("checkOutTime")long checkOutTime);
}
