package com.example.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FrontendRegisterVO {
    /** 房间号 */
    private String roomNumber;
    /** 房间密码 */
    private String password;
}
