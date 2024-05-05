package com.example.service.impl;

import com.example.common.SystemParam;
import com.example.dao.entity.AirConditionerDO;
import com.example.dao.entity.RoomDO;
import com.example.dao.entity.UserDO;
import com.example.dao.mapper.*;
import com.example.dto.req.FrontendDetailFeesReqDTO;
import com.example.dto.req.FrontendFreeRoomReqDTO;
import com.example.dto.req.FrontendRegisterReqDTO;
import com.example.dto.req.FrontendSettleBillReqDTO;
import com.example.dto.resp.FrontendDetailFeesRespDTO;
import com.example.dto.resp.FrontendRegisterRespDTO;
import com.example.dto.resp.FrontendSettleBillRespDTO;
import com.example.service.FrontendService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FrontendServiceImpl implements FrontendService {

    private final RoomMapper roomMapper;
    private final RoomReservationMapper roomReservationMapper;
    private final UserMapper userMapper;
    private final AirConditionerMapper airConditionerMapper;
    private final DetailedFeesMapper detailedFeesMapper;
    private final SystemParam systemParam = SystemParam.getInstance();

    /**
     * 查询某个【时间段】是否有某种类型【0：标准间 1：大床房】空闲房间
     *
     * @param frontendFreeRoomReqDTO 房间类型、入住时间、离开时间
     * @return t / f 返回结果给前台
     */
    @Override
    public boolean getFreeRoom(FrontendFreeRoomReqDTO frontendFreeRoomReqDTO) {
        // 给旅客分配房间
        Long checkInTime = frontendFreeRoomReqDTO.getCheckInTime();
        Long checkOutTime = frontendFreeRoomReqDTO.getCheckOutTime();
        // 查询已经预订的房间
        List<String> reservedRooms = roomReservationMapper.selectReservedRooms(checkInTime, checkOutTime);
        // 查询所有对应类型的房间
        List<RoomDO> allRooms = roomMapper.selectAllRooms(frontendFreeRoomReqDTO.getRoomType());
        // 可选房间集合
        List<RoomDO> collect = allRooms.stream()
                .filter(r -> !reservedRooms.contains(r.getRoomNumber()))
                .toList();
        return collect.size() > 0;
    }

    /**
     * 后台将信息写入数据库
     *
     * @param frontendRegisterReqDTO 旅客登记信息
     * @return 登记成功，后台返回【房间号 + 房间密码】给前台
     */
    @Override
    public FrontendRegisterRespDTO register(FrontendRegisterReqDTO frontendRegisterReqDTO) {
        // 将旅客信息存储进数据库
        userMapper.insert(frontendRegisterReqDTO);
        // 给旅客分配房间
        Long checkInTime = frontendRegisterReqDTO.getCheckInTime();
        Long checkOutTime = frontendRegisterReqDTO.getCheckOutTime();
        // 查询已经预订的房间
        List<String> reservedRooms = roomReservationMapper.selectReservedRooms(checkInTime, checkOutTime);
        // 查询所有对应类型的房间
        List<RoomDO> allRooms = roomMapper.selectAllRooms(frontendRegisterReqDTO.getRoomType());
        RoomDO firstRoom = allRooms.stream()
                .filter(r -> !reservedRooms.contains(r.getRoomNumber()))
                .toList().get(0);
        return FrontendRegisterRespDTO.builder()
                .roomNumber(firstRoom.getRoomNumber())
                .password(firstRoom.getRoomPassword())
                .build();
    }

    /**
     * 根据旅客身份信息查询房费和空调费用
     *
     * @param frontendSettleBillReqDTO 旅客姓名、身份证号、房间号、房间密码
     * @return 费用简洁单
     */
    @Override
    public FrontendSettleBillRespDTO settleBill(FrontendSettleBillReqDTO frontendSettleBillReqDTO) {
        // 查询旅客信息
        UserDO userDO = userMapper.select(frontendSettleBillReqDTO.getIdCard());
        // 查询房间信息
        RoomDO roomDO = roomMapper.select(frontendSettleBillReqDTO.getRoomNumber());
        // 查询空调信息
        AirConditionerDO airConditionerDO = airConditionerMapper.select(roomDO.getAcNumber());
        if (!roomDO.getRoomPassword().equals(frontendSettleBillReqDTO.getRoomPassword())) {
            return null;
        }
        // 密码校验完毕，开始结账
        double roomPrice = roomDO.getRoomType() == 0 ? systemParam.getStandardRoomPrice() : systemParam.getDeluxeRoomPrice();
        int days = (int)(userDO.getCheckOutTime() - userDO.getCheckInTime()) / 1000 / 3600 / 24;
        double roomFee = roomPrice * days;
        double airConditionerFee = airConditionerDO.getCurrFee();
        airConditionerMapper.update(AirConditionerDO.builder()
                .acNumber(roomDO.getAcNumber())
                .currFee(0.00)
                .build());
        return FrontendSettleBillRespDTO.builder()
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
     * @param frontendDetailFeesReqDTO 房间号、入住时间、离开时间
     * @return 空调费用详细单
     */
    @Override
    public List<FrontendDetailFeesRespDTO> getDetailFees(FrontendDetailFeesReqDTO frontendDetailFeesReqDTO) {
        return detailedFeesMapper.select(frontendDetailFeesReqDTO);
    }
}
