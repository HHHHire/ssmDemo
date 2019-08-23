package com.itheima.ssm.service;

import com.itheima.ssm.domain.Permission;
import com.itheima.ssm.domain.Role;

import java.util.List;

public interface IRoleService {

    public List<Role> findAll() throws Exception;

    public void save(Role role) throws Exception;

    public Role findById(Integer roleId) throws Exception;

    public List<Permission> findOtherPermissions(Integer roleId) throws Exception;

    public void addPermissionToRole(Integer roleId, Integer[] permissionIds) throws Exception;
}
