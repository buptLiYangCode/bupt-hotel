package com.example.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * 空调价表实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("t_price")
public class PriceDO {
    /*
    空调风速
    */
    private Integer windSpeed;

    /*
    空调温度
    */
    private Integer temperature;

    /*
    分钟价格
    */
    private BigDecimal price;
}
