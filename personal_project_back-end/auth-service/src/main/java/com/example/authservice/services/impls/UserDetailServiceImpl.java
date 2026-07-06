package com.example.authservice.services.impls;

import com.example.authservice.services.RoleService;
import com.example.authservice.services.UserRoleService;
import com.example.authservice.services.UserService;
import com.example.common.service.entities.RoleEntity;
import com.example.common.service.entities.UserEntity;
import com.example.common.service.entities.UserRoleEntity;
import com.example.common.service.securities.CustomUserDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailServiceImpl implements UserDetailsService {
    @Autowired
    @Lazy
    UserService userService;
    @Autowired
    UserRoleService userRoleService;
    @Autowired
    RoleService roleService;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userService.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
        List<UserRoleEntity> userRoles = userRoleService.findAllByUserId(user.getId());
        if(userRoles.isEmpty()){
            return CustomUserDetail.build(user, new ArrayList<>());
        }
        List<Long> roleIds = userRoles.stream().map(userRole -> userRole.getRoleId()).toList();
        List<RoleEntity> roles = roleService.findAllByIdIn(roleIds);
        return CustomUserDetail.build(user,roles);
    }
}
