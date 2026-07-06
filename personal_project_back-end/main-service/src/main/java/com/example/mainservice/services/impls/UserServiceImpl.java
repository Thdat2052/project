package com.example.mainservice.services.impls;

import com.example.common.service.constants.ErrorModelConstants;
import com.example.common.service.entities.RoleEntity;
import com.example.common.service.entities.UserRoleEntity;
import com.example.common.service.enums.RoleEnum;
import com.example.common.service.mappers.UserMapper;
import com.example.common.service.models.UserModel;
import com.example.common.service.entities.UserEntity;
import com.example.common.service.exceptions.AppException;
import com.example.common.service.models.UserRoleModel;
import com.example.common.service.models.UtilityIndexModel;
import com.example.common.service.models.requests.PagingQueryConditionRequest;
import com.example.common.service.services.impls.CommonServiceImpl;
import com.example.common.service.utils.AuthUtils;
import com.example.mainservice.repositories.UserRepository;
import com.example.mainservice.services.EmailService;
import com.example.mainservice.services.RoleService;
import com.example.mainservice.services.UserRoleService;
import com.example.mainservice.services.UserService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserServiceImpl extends CommonServiceImpl<UserEntity, Long, UserRepository>
        implements UserService {

    private UserRoleService userRoleService;
    private RoleService roleService;
    private PasswordEncoder passwordEncoder;
    private EmailService emailService;
    protected UserServiceImpl(UserRepository repo, UserRoleService userRoleService, RoleService roleService, PasswordEncoder passwordEncoder, EmailService emailService) {
        super(repo);
        this.userRoleService = userRoleService;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }

    @Transactional
    @Override
    public UserModel createOrUpdate(UserModel model)  {
        UserEntity userEntity = UserMapper.INSTANCE.toEntity(model);
        if(userEntity.getId() == null) {
            userEntity.setPassword(passwordEncoder.encode(model.getNewPassword()));
        }
        if(!ObjectUtils.isEmpty(userEntity.getId()) && userEntity.getId() > 0) {
            var oldUser = repo.findById(userEntity.getId()).orElseThrow(() ->
                    new AppException(ErrorModelConstants.USER_NOT_FOUND));
            userEntity.setPassword(oldUser.getPassword());
            userEntity.setUsername(oldUser.getUsername());
            if(repo.existsByIdNotAndCccd(userEntity.getId(), userEntity.getCccd()))
            throw new AppException(ErrorModelConstants.USER_EXISTS);
            userRoleService.deleteByUserIdIn(List.of(userEntity.getId()));
        } else if (repo.existsByUsernameOrCccd(userEntity.getEmail(), userEntity.getCccd())) {
            throw new AppException(ErrorModelConstants.USER_EXISTS);
        }
        var user = repo.save(userEntity);
        if (model.getRoles() != null && !model.getRoles().isEmpty()) {
            List<RoleEntity> roleEntities = roleService.findAllByNameIn(model.getRoles());
            List<UserRoleEntity> userRoleEntities = roleEntities.stream()
                    .map(role ->{
                        UserRoleEntity userRoleEntity = new UserRoleEntity();
                        userRoleEntity.setUserId(user.getId());
                        userRoleEntity.setRoleId(role.getId());
                        return userRoleEntity;
                    }).toList();
            userRoleService.save(userRoleEntities);
        }
        return UserMapper.INSTANCE.toModel(user);
    }

    @Override
    public Page<UserModel> findAllWithPagingUsingJpa(PagingQueryConditionRequest pagingQueryConditionRequest) {
        var userModels = repo.findAllByPaging(pagingQueryConditionRequest).map(UserMapper.INSTANCE::toModel);
        var userIds = userModels.stream().map(UserModel::getId).toList();
        List<UserRoleModel> userRoles = repo.findAllUserRoleByUserIdIn(userIds);
        Map<Long, List<RoleEnum>> roleMap = userRoles.stream()
                .collect(Collectors.groupingBy(
                        UserRoleModel::getUserId,
                        Collectors.mapping(UserRoleModel::getName, Collectors.toList())
                ));
        userModels.forEach(user -> user.setRoles(roleMap.getOrDefault(user.getId(), List.of())));
        return userModels;
    }
    @Override
    public UserModel findMe() throws AppException {
        String userName = AuthUtils.getCurrentUsername();
        var user = repo.findByUsername(userName);
        if (user == null) throw new AppException(ErrorModelConstants.USER_NOT_FOUND);
        var model =  UserMapper.INSTANCE.toModel(user);
        List<UserRoleEntity> userRoles = userRoleService.findAllByUserId(user.getId());
        if(userRoles.isEmpty()){
            model.setRoles(new ArrayList<>());
        }else{
            List<Long> roleIds = userRoles.stream().map(userRole -> userRole.getRoleId()).toList();
            List<RoleEntity> roles = roleService.findAllByIdIn(roleIds);
            List<RoleEnum> roleEnums = roles.stream().map(role -> role.getName()).toList();
            model.setRoles(roleEnums);
        }
        return model;
    }
    @Override
    public List<UserEntity>  findByIdIn(List<Long> ids) {
        return repo.findByIdIn(ids);
    }

    @Override
    public UserModel updateMe(UserModel model) throws AppException {
        var currentUser = findMe();
        if (!currentUser.getId().equals(model.getId())) {
            throw new AppException(ErrorModelConstants.USER_NOT_FOUND);
        }
        UserEntity userEntity = UserMapper.INSTANCE.toEntity(model);
        userEntity.setPassword(currentUser.getPassword());
        userEntity.setUsername(currentUser.getUsername());
        return UserMapper.INSTANCE.toModel(repo.save(userEntity));
    }

    @Override
    public void resetPassword(String username) throws AppException {
        var user = repo.findByUsername(username);
        if (user == null) {
            throw new AppException(ErrorModelConstants.USER_NOT_FOUND);
        }
        String newPassword = UUID.randomUUID().toString().substring(0, 8);
        user.setPassword(passwordEncoder.encode(newPassword));
        repo.save(user);
        emailService.sendEmailForgotPassword(user.getEmail(), newPassword);
    }

}
