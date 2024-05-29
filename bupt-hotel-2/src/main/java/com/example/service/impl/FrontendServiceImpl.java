package com.example.service.impl;

import com.example.dao.entity.AirConditionerDO;
import com.example.dao.entity.DetailedFeesDO;
import com.example.dao.entity.RoomDO;
import com.example.dao.entity.UserDO;
import com.example.dao.mapper.AirConditionerMapper;
import com.example.dao.mapper.DetailedFeesMapper;
import com.example.dao.mapper.RoomMapper;
import com.example.dao.mapper.UserMapper;
import com.example.dto.FrontendRegisterDTO;
import com.example.dto.FrontendSettleBillDTO;
import com.example.service.FrontendService;
import com.example.vo.FrontendRegisterVO;
import com.example.vo.FrontendSettleBillVO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static com.example.common.SystemParam.ROOM_PRICE_TABLE;

@Service
@RequiredArgsConstructor
public class FrontendServiceImpl implements FrontendService {

    private final RoomMapper roomMapper;
    private final UserMapper userMapper;
    private final AirConditionerMapper airConditionerMapper;
    private final DetailedFeesMapper detailedFeesMapper;

    /**
     * 查询有无空闲房间
     *
     * @return t / f 返回结果给前台
     */
    @Override
    public boolean query() {
        LinkedList<RoomDO> roomDOS = roomMapper.getAll();
        for (RoomDO roomDO : roomDOS) {
            if (roomDO.isEmptyy())
                return true;
        }
        return false;
    }

    /**
     * 后台将信息写入数据库
     *
     * @param frontendRegisterDTO 旅客登记信息
     * @return 登记成功，后台返回【房间号 + 房间密码】给前台
     */
    @Override
    public FrontendRegisterVO register(FrontendRegisterDTO frontendRegisterDTO) {
        LinkedList<RoomDO> roomDOS = roomMapper.getAll();
        RoomDO roomDO = roomDOS.stream()
                .filter(RoomDO::isEmptyy)
                .toList().get(0);
        UserDO userDO = new UserDO();
        BeanUtils.copyProperties(frontendRegisterDTO, userDO);
        userDO.setCheckInTime(System.currentTimeMillis());
        userDO.setRoomNumber(roomDO.getRoomNumber());
        // 将旅客信息存储进数据库
        userMapper.insert(userDO);
        roomMapper.updateEmptyy(roomDO.getRoomNumber(), false);

        return FrontendRegisterVO.builder()
                .roomNumber(roomDO.getRoomNumber())
                .password(roomDO.getRoomPassword())
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
        if (!roomDO.getRoomPassword().equals(frontendSettleBillDTO.getRoomPassword()))
            return null;

        // 密码校验完毕，开始结账
        double roomPrice = ROOM_PRICE_TABLE.get(roomDO.getRoomNumber());
        int days = userDO.getDays();
        double roomFee = roomPrice * days;
        double airConditionerFee = airConditionerDO.getCurrFee();
        userMapper.update(userDO.getIdCard(), System.currentTimeMillis());
        return FrontendSettleBillVO.builder()
                .roomPrice(roomPrice)
                .startTime(userDO.getCheckInTime())
                .days(days)
                .roomFee(roomFee)
                .airConditionerFee(airConditionerFee)
                .totalFee(roomFee + airConditionerFee)
                .build();
    }

    /**
     * 查询旅客住宿时间段内的空调详细计费记录
     *
     * @param idCard 身份证号
     * @return 空调费用详细单
     */
    @Override
    public List<DetailedFeesDO> getDetailFees(String idCard) {
        UserDO userDO = userMapper.select(idCard);
        String acNumber = roomMapper.select(userDO.getRoomNumber()).getAcNumber();
        List<DetailedFeesDO> detailedFeesDOS = detailedFeesMapper.select(acNumber);
        return mergeByStartTime(detailedFeesDOS);
    }

    public List<DetailedFeesDO> mergeByStartTime(List<DetailedFeesDO> records) {
        List<DetailedFeesDO> mergedRecords = new ArrayList<>();
        DetailedFeesDO currentRecord = null;
        for (DetailedFeesDO record : records) {
            if (currentRecord == null) {
                currentRecord = record;
            } else if (currentRecord.getWindSpeed() == record.getWindSpeed() &&
                    record.getStartTime() - currentRecord.getEndTime() < 100L) {
                // 合并记录
                currentRecord.setEndTime(record.getEndTime());
                currentRecord.setMinutes(currentRecord.getMinutes() + record.getMinutes());
                currentRecord.setFee(currentRecord.getFee() + record.getFee());
            } else {
                // 添加当前记录到合并后的列表中
                mergedRecords.add(currentRecord);
                currentRecord = record;
            }
        }

        // 添加最后一个记录
        if (currentRecord != null) {
            mergedRecords.add(currentRecord);
        }

        return mergedRecords;
    }
}
