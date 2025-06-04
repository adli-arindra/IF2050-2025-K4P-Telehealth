package org.drpl.telebe.controller;

import org.drpl.telebe.dto.DoctorSpecializationType;
import org.drpl.telebe.model.Doctor;
import org.drpl.telebe.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/doctors")
public class DoctorController {

    @Autowired
    private DoctorRepository doctorRepository;

    @GetMapping
    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }

    @GetMapping("/by-specialization")
    public List<Doctor> getDoctorsBySpecialization(@RequestParam DoctorSpecializationType specialization) {
        return doctorRepository.findBySpecialization(specialization);
    }

    @GetMapping("/specializations")
    public List<String> getAllSpecializations() {
        return Arrays.stream(DoctorSpecializationType.values())
                .map(Enum::name)
                .collect(Collectors.toList());
    }
}
