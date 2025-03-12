package com.example.pojo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Consultant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer consultantId;

    private String name;
    private String professionalInfo;
    private String password;
    private String salt;

    public Consultant() {
        // Default constructor
    }

    public Consultant(String name, String professionalInfo, String password, String salt) {
        this.name = name;
        this.professionalInfo = professionalInfo;
        this.password = password;
        this.salt = salt;
    }

    public Integer getConsultantId() {
        return consultantId;
    }

    public void setConsultantId(Integer consultantId) {
        this.consultantId = consultantId;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    @Override
    public String toString() {
        return "Consultant{" +
                "consultantId=" + consultantId +
                ", name='" + name + '\'' +
                ", professionalInfo='" + professionalInfo + '\'' +
                ", password='" + password + '\'' +
                ", salt='" + salt + '\'' +
                '}';
    }
}
