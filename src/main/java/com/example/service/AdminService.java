package com.example.service;

import com.example.pojo.Admin;
import com.example.utils.Result;

public interface AdminService {
    Result<Admin> loginService(Integer AdminId, String password);
}
