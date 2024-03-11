package com.oril.service;

import com.oril.entities.UserVO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@AllArgsConstructor
public class UserService {

    public UserVO save(UserVO userVO) {
        //Simulate saving user to database
        String userId = String.valueOf(new Date().getTime());
        userVO.setId(userId);
        userVO.setRole("USER");
        //save user
        return userVO;
    }
}
