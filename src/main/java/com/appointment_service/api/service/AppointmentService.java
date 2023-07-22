package com.appointment_service.api.service;
// AppointmentService.java
import com.doctor_service.api.entity.Doctor;
import com.appointment_service.api.entity.Appointment;
import com.patient_service.api.entity.Patient;
import com.appointment_service.api.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final RestTemplate restTemplate;

    private static final String PATIENT_MICROSERVICE_URL = "http://localhost:8080/patients/";
    private static final String DOCTOR_MICROSERVICE_URL = "http://localhost:9191/doctors/";

    @Autowired
    public AppointmentService(AppointmentRepository appointmentRepository, RestTemplate restTemplate) {
        this.appointmentRepository = appointmentRepository;
        this.restTemplate = restTemplate;
    }

    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }

    public Appointment getAppointmentById(Long id) {
        return appointmentRepository.findById(id).orElse(null);
    }

    public Appointment createAppointment(Appointment appointment) {
        // Validate patientId and doctorId before saving the appointment
        if (isPatientValid(appointment.getPatientId()) && isDoctorValid(appointment.getDoctorId())) {
            return appointmentRepository.save(appointment);
        } else {
            throw new IllegalArgumentException("Invalid patientId or doctorId.");
        }
    }

    public Appointment updateAppointment(Long id, Appointment updatedAppointment) {
        Appointment existingAppointment = getAppointmentById(id);
        if (existingAppointment == null) {
            return null;
        }

        // Update the existing appointment with the fields from the updated appointment
        existingAppointment.setDoctorId(updatedAppointment.getDoctorId());
        existingAppointment.setPatientId(updatedAppointment.getPatientId());
        existingAppointment.setAppointmentDateTime(updatedAppointment.getAppointmentDateTime());

        // Validate patientId and doctorId before updating the appointment
        if (isPatientValid(existingAppointment.getPatientId()) && isDoctorValid(existingAppointment.getDoctorId())) {
            return appointmentRepository.save(existingAppointment);
        } else {
            throw new IllegalArgumentException("Invalid patientId or doctorId.");
        }
    }

    public void deleteAppointment(Long id) {
        appointmentRepository.deleteById(id);
    }

    private boolean isPatientValid(Long patientId) {
        // Perform the RestTemplate call to the Patient microservice to validate patientId
        ResponseEntity<Patient> response = restTemplate.getForEntity(PATIENT_MICROSERVICE_URL + patientId, Patient.class);
        return response.getStatusCode() == HttpStatus.OK;
    }

    private boolean isDoctorValid(Long doctorId) {
        // Perform the RestTemplate call to the Doctor microservice to validate doctorId
        ResponseEntity<Doctor> response = restTemplate.getForEntity(DOCTOR_MICROSERVICE_URL + doctorId, Doctor.class);
        return response.getStatusCode() == HttpStatus.OK;
    }
}
