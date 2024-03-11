package com.oril.service;

import com.oril.entities.AuthRequest;
import com.oril.entities.UserVO;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@AllArgsConstructor
public class AuthService {

    private final RestTemplate restTemplate;
    private final JwtUtil jwtUtil;

    public AuthResponse register(AuthRequest request) { //Envio un usuario a registrar y me respondes con dos tokens
        //validation if exists in db
        request.setPassword(BCrypt.hashpw(request.getPassword(), BCrypt.gensalt()));
        UserVO registerdUser = restTemplate.postForObject("http://user-service/users", request, UserVO.class);

        String accessToken = jwtUtil.generateToken(registerdUser.getId(), registerdUser.getRole(), "ACCESS");
        String refreshToken = jwtUtil.generateToken(registerdUser.getId(), registerdUser.getRole(), "REFRESH");

        return new AuthResponse(accessToken, refreshToken);
    }
}
