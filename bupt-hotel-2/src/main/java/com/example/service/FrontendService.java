package com.example.service;

import com.example.dto.FrontendDetailFeesDTO;
import com.example.dto.FrontendFreeRoomDTO;
import com.example.dto.FrontendRegisterDTO;
import com.example.dto.FrontendSettleBillDTO;
import com.example.vo.FrontendDetailFeesVO;
import com.example.vo.FrontendRegisterVO;
import com.example.vo.FrontendSettleBillVO;

import java.util.List;

public interface FrontendService {

    boolean getFreeRoom(FrontendFreeRoomDTO freeRoomReqDTO);

    FrontendRegisterVO register(FrontendRegisterDTO frontendRegisterDTO);

    FrontendSettleBillVO settleBill(FrontendSettleBillDTO frontendSettleBillDTO);

    List<FrontendDetailFeesVO> getDetailFees(FrontendDetailFeesDTO frontendDetailFeesDTO);
}
