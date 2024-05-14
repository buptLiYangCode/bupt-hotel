package com.example.service;



public interface UserService {
    FreeRoomRespDTO getFreeRoom(FreeRoomReqDTO freeRoomReqDTO);

    RegisterRespDTO register(RegisterReqDTO registerReqDTO);

    SettleBillRespDTO settleBill(SettleBillReqDTO settleBillReqDTO);

    DetailFeesRespDTO getDetailFees(DetailFeesReqDTO detailFeesReqDTO);

}
