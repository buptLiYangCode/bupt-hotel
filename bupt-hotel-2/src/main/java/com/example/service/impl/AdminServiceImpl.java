package com.example.service.impl;

import com.example.dao.entity.AirConditionerDO;
import com.example.dao.entity.RoomDO;
import com.example.dao.entity.RunningQueueDO;
import com.example.dao.entity.WaitingQueueDO;
import com.example.dao.mapper.AirConditionerMapper;
import com.example.dao.mapper.RoomMapper;
import com.example.dao.mapper.RunningQueueMapper;
import com.example.dao.mapper.WaitingQueueMapper;
import com.example.service.AdminService;
import com.example.vo.AdminQueryVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final RoomMapper roomMapper;
    private final AirConditionerMapper airConditionerMapper;
    private final WaitingQueueMapper waitingQueueMapper;
    private final RunningQueueMapper runningQueueMapper;

    @Override
    public List<AdminQueryVO> query5() {

        LinkedList<RoomDO> roomDOs = roomMapper.getAll();
        LinkedList<AdminQueryVO> list = new LinkedList<>();
        for (RoomDO roomDO : roomDOs) {
            AirConditionerDO airConditionerDO = airConditionerMapper.select(roomDO.getAcNumber());
            int flag = 0;
            List<WaitingQueueDO> waitingQueueMapperAll = waitingQueueMapper.getAll();
            List<String> waitingList = waitingQueueMapperAll.stream()
                    .map(WaitingQueueDO::getAcNumber)
                    .toList();
            List<RunningQueueDO> runningQueueMapperAll = runningQueueMapper.getAll();
            List<String> runingList = runningQueueMapperAll.stream()
                    .map(RunningQueueDO::getAcNumber)
                    .toList();
            if (runingList.contains(roomDO.getAcNumber()))
                flag = 2;
            if (waitingList.contains(roomDO.getAcNumber()))
                flag = 1;
            list.add(AdminQueryVO.builder()
                    .acNumber(roomDO.getAcNumber())
                    .currTemperature(roomDO.getCurrTemperature())
                    .targetTemperature(airConditionerDO.getTemperature())
                    .windSpeed(airConditionerDO.getWindSpeed())
                    .currFee(airConditionerDO.getCurrFee())
                    .flag(flag)
                    .build());
        }
        return list;
    }
}
