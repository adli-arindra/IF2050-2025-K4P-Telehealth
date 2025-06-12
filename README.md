# Telehealth+ Application

## Penjelasan Singkat Mengenai Aplikasi
Telehealth+ adalah aplikasi sistem layanan kesehatan jarak jauh yang menghubungkan pasien, dokter, dan apoteker. Aplikasi ini menyediakan fitur autentikasi pengguna, komunikasi chat antar pengguna, manajemen rekam medis, resep obat, dan pemesanan obat. Backend menggunakan Spring Boot dengan REST API, sedangkan frontend adalah aplikasi desktop berbasis JavaFX.

## Cara Menjalankan Aplikasi

### Backend
1. Pastikan Java dan Gradle sudah terinstall.
2. Buka terminal di direktori `telehealth-be`.
3. Jalankan perintah berikut untuk menjalankan backend:
   ```
   ./gradlew bootRun
   ```
4. Backend akan berjalan di `http://localhost:8080`.

### Frontend
1. Pastikan Java sudah terinstall.
2. Buka terminal di direktori `telehealth-fe`.
3. Jalankan aplikasi JavaFX dengan menjalankan:
   ```
   ./gradlew run
   ```
4. Aplikasi desktop akan terbuka dan terhubung ke backend.

## Daftar Modul yang Diimplementasi

### Backend Modules
- **AuthController**: Modul autentikasi dan manajemen pengguna (pasien, dokter, apoteker). Bertugas mengelola registrasi, login, dan profil pengguna.
- **ChatController**: Modul komunikasi chat antar pengguna. Bertugas membuat sesi chat, mengirim dan menerima pesan.
- **DoctorController**: Modul manajemen data dokter.
- **MedicalRecordController**: Modul manajemen rekam medis pasien.
- **PrescriptionController**: Modul manajemen resep obat.
- **OrderController**: Modul pemesanan obat.
- **LLMController**: Modul integrasi dengan Large Language Model (jika ada).

### Frontend Modules
- **MainController**: Modul utama yang mengatur navigasi halaman berdasarkan tipe pengguna.
- **Auth Controllers**: Modul untuk halaman autentikasi seperti SignIn, SignUp, dan SplashScreen.
- **Doctor Controllers**: Modul untuk halaman dokter seperti ChatRoom dan ChatSessions.
- **Patient Controllers**: Modul untuk halaman pasien seperti HomePage, DoctorSelection, MedicalRecord, Prescription, dan ProfilePage.
- **Pharmacist Controllers**: Modul untuk halaman apoteker seperti OrdersPage.

*Catatan: Tidak ada screenshot tampilan layar yang disertakan.*

## Daftar Tabel Basis Data yang Diimplementasi

| Nama Tabel     | Atribut                                                                                  |
|----------------|------------------------------------------------------------------------------------------|
| `user`         | id, name, email, hashedPassword, alamat, tanggalLahir, userType                          |
| `patient`      | (inherit dari user)                                                                       |
| `doctor`       | (inherit dari user), specialization                                                      |
| `pharmacist`   | (inherit dari user)                                                                       |
| `medicine`     | id, name, description, dosage, price                                                    |
| `prescription` | id, patient_id, doctor_id, medicine_id, dosage, instructions, dateIssued                 |
| `order`        | id, patient_id, pharmacist_id, prescription_id, orderDate, status                        |
| `chat_session` | id, sessionName, createdDate                                                             |
| `chat_message` | id, chatSession_id, sender_id, message, timestamp, prescription_id (optional)            |
| `medical_record`| id, patient_id, doctor_id, diagnosis, treatment, recordDate                             |

*Catatan: Atribut tabel diambil dari model JPA yang diimplementasikan di backend.*

## Struktur Direktori Proyek

```
telehealth/
├── telehealth-be/                  # Backend Spring Boot application
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/org/drpl/telebe/
│   │   │   │   ├── controller/      # REST API controllers (Auth, Chat, Doctor, etc.)
│   │   │   │   ├── model/           # JPA entity models (User, Medicine, Prescription, etc.)
│   │   │   │   ├── repository/      # Spring Data repositories
│   │   │   │   ├── dto/             # Data Transfer Objects
│   │   │   │   ├── utils/           # Utility classes (e.g., BasicCipher)
│   │   │   │   └── Main.java        # Spring Boot application entry point
│   │   └── resources/
│   │       └── application.properties  # Configuration file
│   ├── build.gradle                # Gradle build file for backend
│   └── gradlew, gradlew.bat        # Gradle wrapper scripts
├── telehealth-fe/                  # Frontend JavaFX application
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/org/drpl/telefe/
│   │   │   │   ├── app/             # JavaFX application classes and controllers
│   │   │   │   ├── domain/          # Domain models for frontend
│   │   │   │   ├── dto/             # Data Transfer Objects
│   │   │   │   ├── fetcher/         # Classes to fetch data from backend APIs
│   │   │   │   ├── utils/           # Utility classes
│   │   │   │   ├── Main.java        # JavaFX application launcher
│   │   │   │   └── Global.java      # Global constants
│   │   └── resources/
│   │       ├── org/drpl/telefe/app/view/  # FXML view files
│   │       ├── styles/              # CSS stylesheets
│   │       └── images/              # Image assets
│   ├── build.gradle                # Gradle build file for frontend
│   └── gradlew, gradlew.bat        # Gradle wrapper scripts
├── README.md                      # Project documentation
├── .gitignore                    # Git ignore file
│ 
└── Doc                   # Capture screen layar
