package com.example.repository;

import com.example.pojo.Admin;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface AdminDao  {

    @Select("select * from Admins where admin_id=#{Id} and password_HashwithSalt=#{password}")
    Admin getByIdAndPassword(Integer Id, String password); //根据id和密码查询管理员，密码为带盐哈希值

    @Select("select salt from Admins where admin_id=#{Id}")
    String getSaltById(Integer Id);    //根据id查询管理员的盐
}
