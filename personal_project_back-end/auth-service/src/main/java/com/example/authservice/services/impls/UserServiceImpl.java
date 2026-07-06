package com.example.authservice.services.impls;


import com.example.authservice.constants.ErrorModelConstants;
import com.example.authservice.dtos.requests.UserDto;
import com.example.authservice.mapper.UserMapper;
import com.example.authservice.repositories.UserRepository;
import com.example.authservice.services.RoleService;
import com.example.authservice.services.UserRoleService;
import com.example.authservice.services.UserService;
import com.example.common.service.securities.JwtTokenComponent;
import com.example.common.service.entities.RoleEntity;
import com.example.common.service.entities.UserEntity;
import com.example.common.service.entities.UserRoleEntity;
import com.example.common.service.enums.RoleEnum;
import com.example.common.service.exceptions.AppException;
import com.example.common.service.models.UserModel;
import com.example.common.service.services.impls.CommonServiceImpl;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl extends CommonServiceImpl<UserEntity, Long, UserRepository> implements UserService {

    @Autowired
    private UserRepository repository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoleService roleService;
    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private JwtTokenComponent jwtUtil;


    private static final String PASSWORD_DEFAULT = "12345";

    protected UserServiceImpl(UserRepository repo) {
        super(repo);
    }


    @Override
    public Optional<UserEntity> findByUsername(String username) {
        return repository.findByUsername(username);
    }

    @Override
    public boolean existsByUsername(String username) {
        return repository.existsByUsername(username);
    }

    @Override
    public boolean existsByEmail(String email) {
        return repository.existsByEmail(email);
    }

    @Override
    @Transactional
    public UserModel create(UserModel userModel) {
        return createData(userModel);
    }

    @Override
    @Transactional
    public List<UserModel> createAll(List<UserModel> users, String transactionId) {
        List<UserModel> userModels = new ArrayList<>();
        for(UserModel userModel: users){
            userModel.setTransactionId(transactionId);
            userModels.add(createData(userModel));
        }
        return userModels;
    }

    @Override
    public List<UserDto> findUserInformation(String username){
        return repository.findUserInformation(username);
    }

    @Override
    public List<UserEntity> findByTransactionId(String transactionId) {
        return repository.findByTransactionId(transactionId);
    }

    @Override
    @Transactional
    public Boolean rollBackWhenCreatedContractFail(String transactionId) {
        List<Long> userIds = findByTransactionId(transactionId).stream().map(userEntity -> userEntity.getId()).toList();
        repository.deleteByTransactionId(transactionId);
        userRoleService.deleteByUserIdIn(userIds);
        return Boolean.TRUE;
    }

    @Override
    public UserModel getInfoFromToken(String token) {
        Claims claims = jwtUtil.parseClaims(token);
        String username = (String) claims.get(Claims.SUBJECT);
        UserEntity user = findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorModelConstants.USER_NOTFOUND));
        return UserMapper.INSTANCE.toModel(user);
    }

    private UserModel createData(UserModel userModel){
        userModel.setPassword(PASSWORD_DEFAULT);
        userModel.setFullName(userModel.getUsername());
        UserEntity user = UserMapper.INSTANCE.toEntity(userModel, passwordEncoder);
        UserEntity userEntity = repository.save(user);
        RoleEntity roleEntity = roleService.findByName(RoleEnum.USER);
        UserRoleEntity userRoleEntity = new UserRoleEntity();
        userRoleEntity.setRoleId(roleEntity.getId());
        userRoleEntity.setUserId(userEntity.getId());
        userRoleService.save(userRoleEntity);
        return UserMapper.INSTANCE.toModel(userEntity);
    }
}
