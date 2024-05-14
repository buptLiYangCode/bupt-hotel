package com.example.service;

import com.example.dto.UserUpdateDTO;

public interface UserService {
    void openOrClose(String acNumber);

    void update(UserUpdateDTO userUpdateDTO);

}
