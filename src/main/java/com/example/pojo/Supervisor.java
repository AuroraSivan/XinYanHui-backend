package com.example.pojo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Supervisor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer supervisorId;

    private String name;
    private String professionalInfo;
    private String passwordHashwithSalt;
    private String salt;

    public Supervisor() {
    }

    public Supervisor(String name, String professionalInfo, String passwordHashwithSalt, String salt) {
        this.name = name;
        this.professionalInfo = professionalInfo;
        this.passwordHashwithSalt = passwordHashwithSalt;
        this.salt = salt;
    }

    public Integer getSupervisorId() {
        return supervisorId;
    }

    public void setSupervisorId(Integer supervisorId) {
        this.supervisorId = supervisorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfessionalInfo() {
        return professionalInfo;
    }

    public void setProfessionalInfo(String professionalInfo) {
        this.professionalInfo = professionalInfo;
    }

    public String getPasswordHashwithSalt() {
        return passwordHashwithSalt;
    }

    public void setPasswordHashwithSalt(String passwordHashwithSalt) {
        this.passwordHashwithSalt = passwordHashwithSalt;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    @Override
    public String toString() {
        return "Supervisor{" +
                "supervisorId=" + supervisorId +
                ", name='" + name + '\'' +
                ", professionalInfo='" + professionalInfo + '\'' +
                ", passwordHashwithSalt='" + passwordHashwithSalt + '\'' +
                ", salt='" + salt + '\'' +
                '}';
    }
}
