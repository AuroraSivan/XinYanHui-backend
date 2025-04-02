package com.example.repository;

import com.example.pojo.User;
import org.apache.ibatis.annotations.Select;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends JpaRepository<User, Integer> {
    @Select("SELECT * FROM users WHERE username = #{username}")
    User findByUsername(String username); // 通过用户名查找用户

    @Select("SELECT * FROM users WHERE username = #{username} AND password = #{password}")
    User findByUsernameAndPassword(String username, String password); // 通过用户名和密码查找用户

    @Select("SELECT * FROM users WHERE phone = #{phone} AND password = #{password}")
    User findByPhoneAndPassword(String phone, String password); // 通过电话和密码查找用户

    @Select("SELECT * FROM users WHERE email = #{email} AND password = #{password}")
    User findByEmailAndPassword(String email, String password); // 通过邮箱和密码查找用户

    @Select("SELECT * FROM users WHERE phone = #{phone}")
    User findByPhone(String phone); // 通过电话查找用户

    @Select("SELECT * FROM users WHERE email = #{email}")
    User findByEmail(String email); // 通过邮箱查找用户
}
