package com.patient_service.api.repository;

// PatientRepository.java

import com.patient_service.api.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

    // Custom queries can be added here if needed
}
