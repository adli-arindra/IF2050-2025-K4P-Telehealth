package org.drpl.telebe.controller;

import org.drpl.telebe.dto.*;
import org.drpl.telebe.model.*;
import org.drpl.telebe.repository.UserRepository;
import org.drpl.telebe.repository.PatientRepository;
import org.drpl.telebe.repository.DoctorRepository;
import org.drpl.telebe.repository.PharmacistRepository;
import org.drpl.telebe.utils.BasicCipher;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.security.GeneralSecurityException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    private final PharmacistRepository pharmacistRepository;

    private static final String TINK_CIPHER_SECRET_KEY = "your-very-secret-tink-key";

    public AuthController(UserRepository userRepository,
                          PatientRepository patientRepository,
                          DoctorRepository doctorRepository,
                          PharmacistRepository pharmacistRepository) {
        this.userRepository = userRepository;
        this.patientRepository = patientRepository;
        this.doctorRepository = doctorRepository;
        this.pharmacistRepository = pharmacistRepository;
    }

    @PostMapping("/register/patient")
    public ResponseEntity<UserProfileResponse> registerPatient(@RequestBody PatientSignUpRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User with this email already exists.");
        }

        String encryptedPassword;
        try {
            encryptedPassword = BasicCipher.encrypt(request.getPassword());
        } catch (GeneralSecurityException e) {
            System.out.println("Error while encrypting password: " + e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to encrypt password.", e);
        }

        Patient newPatient = new Patient(
                request.getName(),
                request.getEmail(),
                encryptedPassword,
                request.getAlamat(),
                request.getTanggalLahir()
        );
        User savedUser = patientRepository.save(newPatient);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                new UserProfileResponse(
                        savedUser.getId(),
                        savedUser.getName(),
                        savedUser.getEmail(),
                        savedUser.getAlamat(),
                        savedUser.getTanggalLahir()
                ));
    }

    @PostMapping("/register/doctor")
    public ResponseEntity<UserProfileResponse> registerDoctor(@RequestBody DoctorSignUpRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User with this email already exists.");
        }

        String encryptedPassword;
        try {
            encryptedPassword = BasicCipher.encrypt(request.getPassword());
        } catch (GeneralSecurityException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to encrypt password.", e);
        }

        Doctor newDoctor = new Doctor(
                request.getName(),
                request.getEmail(),
                encryptedPassword,
                request.getAlamat(),
                request.getTanggalLahir(),
                request.getSpecialization()
        );
        User savedUser = doctorRepository.save(newDoctor);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                new UserProfileResponse(
                        savedUser.getId(),
                        savedUser.getName(),
                        savedUser.getEmail(),
                        savedUser.getAlamat(),
                        savedUser.getTanggalLahir()
                ));
    }

    @PostMapping("/register/pharmacist")
    public ResponseEntity<UserProfileResponse> registerPharmacist(@RequestBody PharmacistSignUpRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User with this email already exists.");
        }

        String encryptedPassword;
        try {
            encryptedPassword = BasicCipher.encrypt(request.getPassword());
        } catch (GeneralSecurityException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to encrypt password.", e);
        }

        Pharmacist newPharmacist = new Pharmacist(
                request.getName(),
                request.getEmail(),
                encryptedPassword,
                request.getAlamat(),
                request.getTanggalLahir()
        );
        User savedUser = pharmacistRepository.save(newPharmacist);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                new UserProfileResponse(
                        savedUser.getId(),
                        savedUser.getName(),
                        savedUser.getEmail(),
                        savedUser.getAlamat(),
                        savedUser.getTanggalLahir()
                ));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody AuthRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials."));

        String decryptedPassword;
        try {
            decryptedPassword = BasicCipher.decrypt(user.getHashedPassword());
        } catch (GeneralSecurityException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to decrypt password.", e);
        }

        if (!request.getPassword().equals(decryptedPassword)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials.");
        }

        UserType userType = user.getUserType();

        LoginResponse response = new LoginResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                userType
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserProfileResponse>> getAllUsers() {
        List<User> users = userRepository.findAll();

        List<UserProfileResponse> userProfiles = users.stream()
                .map(user -> new UserProfileResponse(
                        user.getId(),
                        user.getName(),
                        user.getEmail(),
                        user.getAlamat(),
                        user.getTanggalLahir()
                ))
                .collect(Collectors.toList());

        return ResponseEntity.ok(userProfiles);
    }

    @GetMapping("/profile/{id}")
    public ResponseEntity<UserProfileResponse> getUserProfileById(@PathVariable Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User profile not found."));

        return ResponseEntity.ok(
                new UserProfileResponse(
                        user.getId(),
                        user.getName(),
                        user.getEmail(),
                        user.getAlamat(),
                        user.getTanggalLahir()
                ));
    }
}