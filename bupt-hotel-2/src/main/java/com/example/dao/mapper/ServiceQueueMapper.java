package com.example.dao.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ServiceQueueMapper {
    void delete(String acNumber);
}
