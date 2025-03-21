package com.example.pojo;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Table(name = "users")
@Entity
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer user_id;

    private String username;

    @Column(name = "password_Hashwithsalt")
    private String password;
    private String salt;
    private String email;
    private String phone;
    private Date register_date;

    public User() {
    }

    public User(String username, String password){
        this.username = username;
        this.password = password;
    }

    public User(Integer user_id, String username, String password, String salt, String email, String phone, Date register_date) {
        this.user_id = user_id;
        this.username = username;
        this.password = password;
        this.salt = salt;
        this.email = email;
        this.phone = phone;
        this.register_date = register_date;
    }

}
