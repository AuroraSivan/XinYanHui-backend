package com.example.repository;

import com.example.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends JpaRepository<User, Integer> {
    User findByUsername(String username); //通过用户名uname查找用户
    User findByUsernameAndPassword(String username, String password);//通过用户名uname和密码查找用户
    User findByPhoneAndPassword(String phone, String password);
    User findByEmailAndPassword(String email, String password);
}
