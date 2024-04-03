package com.example.controller;

import com.example.common.result.Result;
import com.example.common.result.Results;
import com.example.dto.req.UserAirConditionerReqDTO;
import com.example.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 用户和后台直接交互。每个空调的状态都已经存在于数据库，每次交互只需要更新数据库中空调的状态即可。
 */
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * 用户更新空调状态
     * @param userAirConditionerReqDTO 空调状态
     * @return 后台返回操作结果
     */
    @PutMapping("/user/airConditioner")
    public Result<Void> updateAirConditionerStatus(@RequestBody UserAirConditionerReqDTO userAirConditionerReqDTO) {
        userService.updateAirConditionerStatus(userAirConditionerReqDTO);
        return Results.success();
    }

}
