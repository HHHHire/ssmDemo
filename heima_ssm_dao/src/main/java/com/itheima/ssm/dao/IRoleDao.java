package com.itheima.ssm.dao;

import com.itheima.ssm.domain.Permission;
import com.itheima.ssm.domain.Role;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

public interface IRoleDao {

    @Select("select * from role where id in (select roleId from users_role where userId = #{id})")
    @Results({
            @Result(id = true, property = "id", column = "id"),
            @Result(property = "roleName", column = "roleName"),
            @Result(property = "roleDesc", column = "roleDesc"),
            @Result(property = "permissions", column = "id", javaType = java.util.List.class, many = @Many(select = "com.itheima.ssm.dao.IPermissionsDao.findPermissionByRoleId")),
    })
    public List<Role> findRoleByUserId(Integer id) throws Exception;

    @Select("select * from role")
    public List<Role> findAll() throws Exception;

    @Insert("insert into role(roleName, roleDesc) values(#{roleName},#{roleDesc})")
    public void save(Role role) throws Exception;

    @Select("select * from role where id = #{roleId}")
    public Role findById(Integer roleId) throws Exception;

    @Select("select * from permission where id not in(select permissionId from role_permission where roleId=#{roleId})")
    public List<Permission> findOtherPermissions(Integer roleId) throws Exception;

    @Insert("insert into role_permission(permissionId,roleId) values(#{permissionId},#{roleId})")
    public void addPermissionToRole(@Param("roleId") Integer roleId, @Param("permissionId") Integer permissionId) throws Exception;
}
