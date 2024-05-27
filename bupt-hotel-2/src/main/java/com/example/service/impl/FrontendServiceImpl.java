package com.example.service.impl;

import com.example.dao.entity.AirConditionerDO;
import com.example.dao.entity.RoomDO;
import com.example.dao.entity.UserDO;
import com.example.dao.mapper.*;
import com.example.dto.FrontendDetailFeesDTO;
import com.example.dto.FrontendFreeRoomDTO;
import com.example.dto.FrontendRegisterDTO;
import com.example.dto.FrontendSettleBillDTO;
import com.example.service.FrontendService;
import com.example.vo.FrontendDetailFeesVO;
import com.example.vo.FrontendRegisterVO;
import com.example.vo.FrontendSettleBillVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FrontendServiceImpl implements FrontendService {

    private final RoomMapper roomMapper;
    private final UserMapper userMapper;
    private final AirConditionerMapper airConditionerMapper;
    private final DetailedFeesMapper detailedFeesMapper;

    /**
     * 查询某个【时间段】是否有某种类型【0：标准间 1：大床房】空闲房间
     *
     * @param frontendFreeRoomDTO 房间类型、入住时间、离开时间
     * @return t / f 返回结果给前台
     */
    @Override
    public boolean getFreeRoom(FrontendFreeRoomDTO frontendFreeRoomDTO) {
        // 给旅客分配房间
        long checkInTime = frontendFreeRoomDTO.getCheckInTime();
        long checkOutTime = frontendFreeRoomDTO.getCheckOutTime();
        // 查询已经预订的房间
        List<String> reservedRooms = null;
        // 查询所有对应类型的房间
        List<RoomDO> allRooms = roomMapper.selectByType(frontendFreeRoomDTO.getRoomType());
        // 可选房间集合
        List<RoomDO> collect = allRooms.stream()
                .filter(r -> !reservedRooms.contains(r.getRoomNumber()))
                .toList();
        return collect.size() > 0;
    }

    /**
     * 后台将信息写入数据库
     *
     * @param frontendRegisterDTO 旅客登记信息
     * @return 登记成功，后台返回【房间号 + 房间密码】给前台
     */
    @Override
    public FrontendRegisterVO register(FrontendRegisterDTO frontendRegisterDTO) {
        // 将旅客信息存储进数据库
        userMapper.insert(frontendRegisterDTO);
        // 给旅客分配房间
        long checkInTime = frontendRegisterDTO.getCheckInTime();
        long checkOutTime = frontendRegisterDTO.getCheckOutTime();
        // 查询已经预订的房间
        List<String> reservedRooms = null;
        // 查询所有对应类型的房间
        List<RoomDO> allRooms = roomMapper.selectByType(frontendRegisterDTO.getRoomType());
        RoomDO firstRoom = allRooms.stream()
                .filter(r -> !reservedRooms.contains(r.getRoomNumber()))
                .toList().get(0);
        return FrontendRegisterVO.builder()
                .roomNumber(firstRoom.getRoomNumber())
                .password(firstRoom.getRoomPassword())
                .build();
    }

    /**
     * 根据旅客身份信息查询房费和空调费用
     *
     * @param frontendSettleBillDTO 旅客姓名、身份证号、房间号、房间密码
     * @return 费用简洁单
     */
    @Override
    public FrontendSettleBillVO settleBill(FrontendSettleBillDTO frontendSettleBillDTO) {
        // 查询旅客信息
        UserDO userDO = userMapper.select(frontendSettleBillDTO.getIdCard());
        // 查询房间信息
        RoomDO roomDO = roomMapper.select(frontendSettleBillDTO.getRoomNumber());
        // 查询空调信息
        AirConditionerDO airConditionerDO = airConditionerMapper.select(roomDO.getAcNumber());
        if (!roomDO.getRoomPassword().equals(frontendSettleBillDTO.getRoomPassword())) {
            return null;
        }
        // 密码校验完毕，开始结账
        double roomPrice = 1;
        int days = (int)(userDO.getCheckOutTime() - userDO.getCheckInTime()) / 1000 / 3600 / 24;
        double roomFee = roomPrice * days;
        double airConditionerFee = airConditionerDO.getCurrFee();
        airConditionerMapper.update(AirConditionerDO.builder()
                .acNumber(roomDO.getAcNumber())
                .currFee(0.00)
                .build());
        return FrontendSettleBillVO.builder()
                .roomPrice(roomPrice)
                .days(days)
                .roomFee(roomFee)
                .airConditionerFee(airConditionerFee)
                .totalFee(roomFee+airConditionerFee)
                .build();
    }

    /**
     * 查询旅客住宿时间段内的空调详细计费记录
     *
     * @param frontendDetailFeesDTO 房间号、入住时间、离开时间
     * @return 空调费用详细单
     */
    @Override
    public List<FrontendDetailFeesVO> getDetailFees(FrontendDetailFeesDTO frontendDetailFeesDTO) {
        return detailedFeesMapper.select(frontendDetailFeesDTO);
    }
}
