package com.example.pojo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Entity
@Table(name = "supervisors")
@Data
@AllArgsConstructor
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

}
