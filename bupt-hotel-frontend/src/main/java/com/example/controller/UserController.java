package com.example.controller;

import com.example.common.result.Result;
import com.example.common.result.Results;
import com.example.dto.req.DetailFeesReqDTO;
import com.example.dto.req.FreeRoomReqDTO;
import com.example.dto.req.RegisterReqDTO;
import com.example.dto.req.SettleBillReqDTO;
import com.example.dto.resp.DetailFeesRespDTO;
import com.example.dto.resp.FreeRoomRespDTO;
import com.example.dto.resp.RegisterRespDTO;
import com.example.dto.resp.SettleBillRespDTO;
import com.example.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * 前台只能接收旅客发送的请求，前台不能直接查询数据库、操作表，必须请求后台，获取后台的返回结果，处理后返回给旅客。
 */
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
//用户登记入住流程
    /**
     * 用户询问前台是否有某种类型【0：标准间 1：大床房】空闲房间
     * @param freeRoomReqDTO 用户信息
     * @return 前台返回从后台得到结果，返回给用户
     */
    @GetMapping("/user/room/freeRoom")
    public Result<FreeRoomRespDTO> getFreeRoom(@RequestBody FreeRoomReqDTO freeRoomReqDTO) {
        FreeRoomRespDTO freeRoomRespDTO= userService.getFreeRoom(freeRoomReqDTO);
        return Results.success(null);
    }
    /**
     * 用户提供信息供前台登记，前台通知后台将信息写入数据库
     * @param registerReqDTO 用户登记信息
     * @return 登记成功，前台从后台拿到【房间号 + 房间密码】交给用户
     */
    @PostMapping("/user/register")
    public Result<RegisterRespDTO> register(@RequestBody RegisterReqDTO registerReqDTO) {
        RegisterRespDTO registerRespDTO = userService.register(registerReqDTO);
        return Results.success(null);
    }
//用户结账流程
    /**
     * 用户申请结账，将房间号、房间密码发给前台，前台从后台拿到数据，返回费用简单给用户
     * @param settleBillReqDTO 旅客姓名、旅客身份证、房间号、房间密码
     * @return 费用简洁单
     */
    @GetMapping("/user/settle-bill")
    public Result<SettleBillRespDTO> settleBill(@RequestBody SettleBillReqDTO settleBillReqDTO) {
        SettleBillRespDTO settleBillRespDTO = userService.settleBill(settleBillReqDTO);
        return Results.success(null);
    }
    /**
     * 用户向前台申请获取空调费用详细单，前台查询后台获得用户住宿时间段内的空调详细计费记录
     * @param detailFeesReqDTO 房间号、房间密码
     * @return 空调费用详细单
     */
    @GetMapping("/user/settle-bill")
    public Result<DetailFeesRespDTO> getDetailFees(@RequestBody DetailFeesReqDTO detailFeesReqDTO) {
        DetailFeesRespDTO detailFeesRespDTO = userService.getDetailFees(detailFeesReqDTO);
        return Results.success(null);
    }
}
