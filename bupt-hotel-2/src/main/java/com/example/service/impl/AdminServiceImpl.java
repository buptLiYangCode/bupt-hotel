package com.example.service.impl;

import com.example.dao.entity.RoomDO;
import com.example.dao.mapper.RoomMapper;
import com.example.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.LinkedList;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final RoomMapper roomMapper;
    @Override
    public String query5() {

        LinkedList<RoomDO> roomDOs = roomMapper.getAll();
        StringBuilder sb = new StringBuilder();
        for (RoomDO roomDO : roomDOs) {
            double currTemperature = roomDO.getCurrTemperature();
            sb.append(currTemperature).append("_____");
        }

        return sb.toString();
    }
}
