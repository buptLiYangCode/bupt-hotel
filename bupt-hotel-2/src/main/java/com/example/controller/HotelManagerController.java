package com.example.controller;

import com.example.common.result.Result;
import com.example.common.result.Results;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
// TODO：这个类最后再弄
public class HotelManagerController {

    // 生成日报
    @GetMapping("/hotel-manage/daily-report")
    public Result<Void> generateDailyReport() {
        return Results.success();
    }

    // 生成周报
    @GetMapping("/hotel-manage/weekly-report")
    public Result<Void> generateWeeklyReport() {
        return Results.success();
    }
}
