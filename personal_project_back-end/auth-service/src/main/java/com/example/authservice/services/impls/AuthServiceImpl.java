package com.example.authservice.services.impls;

import com.example.authservice.dtos.requests.UserDto;
import com.example.authservice.mapper.UserMapper;
import com.example.authservice.dtos.requests.LoginRequest;
import com.example.authservice.dtos.requests.RegisterRequest;
import com.example.common.service.models.requests.TokenValidationRequest;
import com.example.authservice.services.AuthService;
import com.example.authservice.services.UserRoleService;
import com.example.authservice.services.UserService;
import com.example.common.service.securities.JwtTokenComponent;
import com.example.common.service.entities.UserEntity;
import com.example.common.service.enums.RoleEnum;
import com.example.common.service.utils.CollectionUtils;
import com.example.common.service.utils.StringUtils;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    UserRoleService userRoleService;

    @Autowired
    private JwtTokenComponent jwtUtil;


    @Autowired
    private AuthenticationManager authenticationManager;


    public String register(RegisterRequest registerRequest) throws Exception {
        if (userService.existsByUsername(registerRequest.getUsername())) {
            throw new Exception("Username is exist");
        }
        if (userService.existsByEmail(registerRequest.getEmail())) {
            throw new Exception("Email is exist");
        }

        UserEntity user = UserMapper.INSTANCE.toEntity(registerRequest, passwordEncoder);
        userService.save(user);
        return "User registered successfully";
    }

    public String login(LoginRequest authRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
        );

        return jwtUtil.generateAccessToken(authRequest.getUsername());
    }

    public  boolean validateToken(TokenValidationRequest tokenValidationRequest){
        String token = tokenValidationRequest.getToken();
        List<RoleEnum> roleNeeded = tokenValidationRequest.getRoleNeeded();
        if(StringUtils.isBlank(token)) return false;
        if(CollectionUtils.isEmptyOrNull(roleNeeded)) return false;
        if (!jwtUtil.validateAccessToken(token))  return false;
        Claims claims = jwtUtil.parseClaims(token);
        String username = (String) claims.get(Claims.SUBJECT);
        List<UserDto> users = userService
                .findUserInformation(username);
        if(users.isEmpty()) return false;
        return users.stream()
                .anyMatch(user -> user.getRoleName() != null && tokenValidationRequest.getRoleNeeded().contains(user.getRoleName()));

    }
}

