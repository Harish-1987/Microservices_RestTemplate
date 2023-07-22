package com.patient_service.api.entity;

import lombok.*;
import javax.persistence.*;

@Entity
@Table(name="PATIENT_TB")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private int age;
    private String gender;

}
