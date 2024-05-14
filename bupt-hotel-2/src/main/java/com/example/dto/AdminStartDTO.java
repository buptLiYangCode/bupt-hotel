package com.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminStartDTO {
    /*
    空调模式：0表示制冷、1制热
    */
    private int mode;

    /*
    调温上限
     */
    private int upperBound;

    /*
    调温上限
     */
    private int lowerBound;

}
