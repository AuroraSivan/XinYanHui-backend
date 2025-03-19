package com.example;

import com.example.pojo.Admin;
import com.example.pojo.Consultant;
import com.example.pojo.Supervisor;
import com.example.repository.AdminDao;
import com.example.repository.UserDao;
import com.example.service.AdminService;
import com.example.service.ConsultantService;
import com.example.service.SupervisorService;
import com.example.utils.PasswordHashWithSalt;
import com.example.utils.Result;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class XinYanHuiApplicationTests {
    @Autowired
    private AdminDao adminDao;

    @Autowired
    private AdminService adminService;

    @Autowired
    private ConsultantService consultantService;

    @Autowired
    private SupervisorService supervisorService;

    @Autowired
    private UserDao userDao;

    @Test
    void contextLoads() {
    }

    @Test
    public void testPasswordHashWithSalt() throws Exception {
        String password = "a12345";
        String salt = PasswordHashWithSalt.generateSalt();
        String hashedPassword1 = PasswordHashWithSalt.hashPassword(password, salt);
        String hashedPassword2 = PasswordHashWithSalt.hashPassword(password, salt);

        System.out.println("Original Password: " + password);
        System.out.println("Salt: " + salt);
        System.out.println("Hashed Password: " + hashedPassword1);
        if(hashedPassword1.equals(hashedPassword2)){
            System.out.println("Password hashes match!");
        } else {
            System.out.println("Password hashes do not match!");
        }

    }

    @Test
    public void testGetSaltByAdminId() {
        String salt = adminDao.getSaltById(401);
        System.out.println("Salt: " + salt);
    }

    @Test
    public void testLoginAsConsultant() {
        Result<Consultant> result = consultantService.loginService(1, "a12345");
        System.out.println(result);
    }

}
