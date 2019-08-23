package com.itheima.ssm.service;

import com.itheima.ssm.domain.Role;
import com.itheima.ssm.domain.UserInfo;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface IUserService extends UserDetailsService {
    public List<UserInfo> findAll() throws Exception;

    public void save(UserInfo userInfo) throws Exception;

    public UserInfo findById(Integer id) throws Exception;

    public List<Role> findOtherRoles(Integer userId) throws Exception;

    public void addRoleToUser(Integer userId, Integer[] roleIds) throws Exception;
}
