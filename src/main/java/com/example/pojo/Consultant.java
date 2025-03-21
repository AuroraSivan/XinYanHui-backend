package com.example.pojo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Entity
@Table(name="consultants")
@Data
@AllArgsConstructor
public class Consultant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ConsultantId;

    private String name;
    private String professionalInfo;
    private String password;
    private String salt;

    public Consultant() {
        // Default constructor
    }

}
