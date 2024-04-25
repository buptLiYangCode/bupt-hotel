package com.example.controller;

import com.example.common.result.Result;
import com.example.common.result.Results;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 空调资源有限，同时只有部分空调处分，调度员负责调度房间空调出风
 */
@RestController
@RequiredArgsConstructor
public class DispatcherController {

    /**
     * 查询空闲资源个数
     */
    @GetMapping("/dispatcher/query")
    public Result<Integereger> queryFreeResources() {


        return Results.success(null);
    }

    /**
     * 当前有k个资源，查询等待列表前k个空调编号
     */
    @GetMapping("/dispatcher/unworkable")
    public Result<List<String>> queryTopKWaitingACNumbers(Integer k) {

        return Results.success(null);
    }

    /**
     * 给这k个空调分配资源
     */
    @PostMapping("/dispatcher/establish")
    public Result<Void> establishConnection(List<String> acNumberList) {

        return Results.success(null);
    }

    /**
     * 检查服务队列中空调是否有用尽时间片的
     */
    @GetMapping("/dispatcher/check-unworkable")
    public Result<List<String>> checkUnworkableACs() {
        return Results.success(null);
    }

    /**
     * 释放时间片用完的空调资源
     */
    @PostMapping("/dispatcher/release")
    public Result<Void> releaseResources(List<String> acNumberList) {

        return Results.success(null);
    }

}
