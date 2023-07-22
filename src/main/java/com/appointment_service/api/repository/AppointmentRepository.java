package com.appointment_service.api.repository;

import com.appointment_service.api.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    // Custom queries can be added here if needed
}
