package com.example.service;

import com.example.dto.UserUpdateDTO;
import com.example.vo.UserQueryVO;

public interface UserService {
    void openOrClose(String acNumber);

    void update(UserUpdateDTO userUpdateDTO);

    UserQueryVO query(String s);
}
