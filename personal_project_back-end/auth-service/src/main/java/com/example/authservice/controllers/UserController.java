package com.example.authservice.controllers;

import com.example.authservice.dtos.requests.LoginRequest;
import com.example.authservice.dtos.requests.RegisterRequest;
import com.example.authservice.services.AuthService;
import com.example.authservice.services.UserService;
import com.example.common.service.models.UserModel;
import com.example.common.service.models.requests.TokenValidationRequest;
import com.example.common.service.utils.AuthUtils;
import com.example.common.service.utils.UrlUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(UrlUtils.AUTH_URL)
public class UserController {
    @Autowired
    private AuthService authService;
    @Autowired
    private UserService userService;

    //    @Secured("ADMIN")
    @PostMapping()
    public ResponseEntity<Object> createUsers(HttpServletRequest request,
                                              @Valid @RequestBody List<UserModel> userModels){
        String transactionId = request.getHeader("TransactionId");
        return ResponseEntity.ok(userService.createAll(userModels , transactionId));
    }

    @GetMapping("/rollback")
    public ResponseEntity<Object> rollBackWhenCreatedContractFail(HttpServletRequest request){
        String transactionId = request.getHeader("TransactionId");
        return ResponseEntity.ok(userService.rollBackWhenCreatedContractFail(transactionId));
    }

    @GetMapping("/token")
    public ResponseEntity<UserModel> getInfoFromToken(HttpServletRequest request){
        String token = AuthUtils.getTokenFromHeader(request);
        return ResponseEntity.ok(userService.getInfoFromToken(token));
    }


    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody RegisterRequest registerRequest) throws Exception {
        return ResponseEntity.ok(authService.register(registerRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody LoginRequest authRequest) {
        String token = authService.login(authRequest);
        return ResponseEntity.ok(token);
    }

    @PostMapping("/validate")
    public ResponseEntity<Boolean> validateToken(@RequestBody TokenValidationRequest tokenValidationRequest) {
        return ResponseEntity.ok(authService.validateToken(tokenValidationRequest));
    }

}
