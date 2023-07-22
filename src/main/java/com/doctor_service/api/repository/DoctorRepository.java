package com.doctor_service.api.repository;

import com.doctor_service.api.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    // Custom queries can be added here if needed
}
