package com.hospital.management.config;

import com.hospital.management.model.*;
import com.hospital.management.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Initializes the database with sample data on first run.
 */
@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initData(
            UserRepository userRepo,
            PatientRepository patientRepo,
            DoctorRepository doctorRepo,
            WardRepository wardRepo,
            BedRepository bedRepo,
            MedicineRepository medicineRepo,
            AppointmentRepository appointmentRepo,
            LabReportRepository labReportRepo,
            BillRepository billRepo,
            PrescriptionRepository prescriptionRepo,
            PasswordEncoder passwordEncoder
    ) {
        return args -> {
            // Seed prescriptions if missing
            if (prescriptionRepo.count() == 0 && patientRepo.count() > 0 && doctorRepo.count() > 0) {
                seedPrescriptions(prescriptionRepo, patientRepo, doctorRepo);
            }

            // Only seed if database is empty
            if (userRepo.count() > 0) return;

            System.out.println("🏥 Seeding database with initial data...");

            // ---- Users ----
            User admin = userRepo.save(User.builder()
                    .username("admin")
                    .password(passwordEncoder.encode("admin123"))
                    .fullName("System Administrator")
                    .email("admin@hospital.com")
                    .phone("9876543210")
                    .role(Role.ADMIN)
                    .enabled(true)
                    .build());

            User doctorUser1 = userRepo.save(User.builder()
                    .username("dr.sharma")
                    .password(passwordEncoder.encode("doctor123"))
                    .fullName("Dr. Rajesh Sharma")
                    .email("rajesh.sharma@hospital.com")
                    .phone("9876543211")
                    .role(Role.DOCTOR)
                    .enabled(true)
                    .build());

            User doctorUser2 = userRepo.save(User.builder()
                    .username("dr.patel")
                    .password(passwordEncoder.encode("doctor123"))
                    .fullName("Dr. Priya Patel")
                    .email("priya.patel@hospital.com")
                    .phone("9876543212")
                    .role(Role.DOCTOR)
                    .enabled(true)
                    .build());

            User doctorUser3 = userRepo.save(User.builder()
                    .username("dr.khan")
                    .password(passwordEncoder.encode("doctor123"))
                    .fullName("Dr. Amir Khan")
                    .email("amir.khan@hospital.com")
                    .phone("9876543213")
                    .role(Role.DOCTOR)
                    .enabled(true)
                    .build());

            User receptionist = userRepo.save(User.builder()
                    .username("reception")
                    .password(passwordEncoder.encode("reception123"))
                    .fullName("Anita Verma")
                    .email("reception@hospital.com")
                    .phone("9876543214")
                    .role(Role.RECEPTIONIST)
                    .enabled(true)
                    .build());

            // ---- Doctors ----
            Doctor doc1 = doctorRepo.save(Doctor.builder()
                    .fullName("Dr. Rajesh Sharma")
                    .specialization("Cardiology")
                    .qualification("MBBS, MD (Cardiology)")
                    .phone("9876543211")
                    .email("rajesh.sharma@hospital.com")
                    .gender("Male")
                    .consultationFee(800.0)
                    .availableDays("Mon,Tue,Wed,Thu,Fri")
                    .availableTimeStart("09:00")
                    .availableTimeEnd("17:00")
                    .status("Active")
                    .user(doctorUser1)
                    .build());

            Doctor doc2 = doctorRepo.save(Doctor.builder()
                    .fullName("Dr. Priya Patel")
                    .specialization("Pediatrics")
                    .qualification("MBBS, MD (Pediatrics)")
                    .phone("9876543212")
                    .email("priya.patel@hospital.com")
                    .gender("Female")
                    .consultationFee(600.0)
                    .availableDays("Mon,Tue,Wed,Thu,Fri,Sat")
                    .availableTimeStart("10:00")
                    .availableTimeEnd("18:00")
                    .status("Active")
                    .user(doctorUser2)
                    .build());

            Doctor doc3 = doctorRepo.save(Doctor.builder()
                    .fullName("Dr. Amir Khan")
                    .specialization("Orthopedics")
                    .qualification("MBBS, MS (Orthopedics)")
                    .phone("9876543213")
                    .email("amir.khan@hospital.com")
                    .gender("Male")
                    .consultationFee(700.0)
                    .availableDays("Mon,Wed,Fri,Sat")
                    .availableTimeStart("08:00")
                    .availableTimeEnd("16:00")
                    .status("Active")
                    .user(doctorUser3)
                    .build());

            // ---- Patients ----
            Patient p1 = patientRepo.save(Patient.builder()
                    .fullName("Amit Kumar")
                    .dateOfBirth(LocalDate.of(1985, 3, 15))
                    .gender("Male")
                    .phone("9898989898")
                    .email("amit.kumar@email.com")
                    .address("12, MG Road, Kolkata")
                    .bloodGroup("O+")
                    .allergies("None")
                    .medicalHistory("Hypertension")
                    .emergencyContactName("Sunita Kumar")
                    .emergencyContactPhone("9898989899")
                    .status("Active")
                    .build());

            Patient p2 = patientRepo.save(Patient.builder()
                    .fullName("Sneha Roy")
                    .dateOfBirth(LocalDate.of(1990, 7, 22))
                    .gender("Female")
                    .phone("9797979797")
                    .email("sneha.roy@email.com")
                    .address("45, Park Street, Kolkata")
                    .bloodGroup("A+")
                    .allergies("Penicillin")
                    .medicalHistory("Asthma")
                    .emergencyContactName("Rahul Roy")
                    .emergencyContactPhone("9797979798")
                    .status("Active")
                    .build());

            Patient p3 = patientRepo.save(Patient.builder()
                    .fullName("Ravi Gupta")
                    .dateOfBirth(LocalDate.of(1978, 11, 5))
                    .gender("Male")
                    .phone("9696969696")
                    .email("ravi.gupta@email.com")
                    .address("78, Salt Lake, Kolkata")
                    .bloodGroup("B+")
                    .allergies("Sulfa drugs")
                    .medicalHistory("Diabetes Type 2")
                    .emergencyContactName("Meera Gupta")
                    .emergencyContactPhone("9696969697")
                    .status("Active")
                    .build());

            Patient p4 = patientRepo.save(Patient.builder()
                    .fullName("Kavita Singh")
                    .dateOfBirth(LocalDate.of(1995, 1, 10))
                    .gender("Female")
                    .phone("9595959595")
                    .email("kavita.singh@email.com")
                    .address("23, New Town, Kolkata")
                    .bloodGroup("AB+")
                    .status("Active")
                    .build());

            Patient p5 = patientRepo.save(Patient.builder()
                    .fullName("Suresh Das")
                    .dateOfBirth(LocalDate.of(1960, 6, 18))
                    .gender("Male")
                    .phone("9494949494")
                    .email("suresh.das@email.com")
                    .address("90, Howrah, Kolkata")
                    .bloodGroup("O-")
                    .medicalHistory("Heart Disease, High Cholesterol")
                    .emergencyContactName("Lakshmi Das")
                    .emergencyContactPhone("9494949495")
                    .status("Active")
                    .build());

            // ---- Wards ----
            Ward generalWard = wardRepo.save(Ward.builder()
                    .wardName("General Ward A")
                    .wardType("General")
                    .totalBeds(20)
                    .floorNumber(1)
                    .description("General ward on the first floor")
                    .active(true)
                    .build());

            Ward icuWard = wardRepo.save(Ward.builder()
                    .wardName("ICU Ward")
                    .wardType("ICU")
                    .totalBeds(10)
                    .floorNumber(2)
                    .description("Intensive Care Unit")
                    .active(true)
                    .build());

            Ward privateWard = wardRepo.save(Ward.builder()
                    .wardName("Private Ward B")
                    .wardType("Private")
                    .totalBeds(8)
                    .floorNumber(3)
                    .description("Private rooms with attached bathroom")
                    .active(true)
                    .build());

            // ---- Beds ----
            for (int i = 1; i <= 5; i++) {
                bedRepo.save(Bed.builder()
                        .bedNumber("G-" + String.format("%02d", i))
                        .ward(generalWard)
                        .status(i <= 3 ? "Available" : "Occupied")
                        .dailyRate(500.0)
                        .patient(i == 4 ? p1 : i == 5 ? p2 : null)
                        .build());
            }
            for (int i = 1; i <= 3; i++) {
                bedRepo.save(Bed.builder()
                        .bedNumber("ICU-" + String.format("%02d", i))
                        .ward(icuWard)
                        .status(i == 1 ? "Occupied" : "Available")
                        .dailyRate(2000.0)
                        .patient(i == 1 ? p5 : null)
                        .build());
            }
            for (int i = 1; i <= 3; i++) {
                bedRepo.save(Bed.builder()
                        .bedNumber("PVT-" + String.format("%02d", i))
                        .ward(privateWard)
                        .status("Available")
                        .dailyRate(1500.0)
                        .build());
            }

            // ---- Medicines ----
            medicineRepo.save(Medicine.builder()
                    .name("Paracetamol 500mg").genericName("Acetaminophen").category("Tablet")
                    .manufacturer("Cipla").price(5.0).stockQuantity(500).reorderLevel(100)
                    .batchNumber("BAT-001").active(true).build());
            medicineRepo.save(Medicine.builder()
                    .name("Amoxicillin 250mg").genericName("Amoxicillin").category("Capsule")
                    .manufacturer("Sun Pharma").price(12.0).stockQuantity(300).reorderLevel(50)
                    .batchNumber("BAT-002").active(true).build());
            medicineRepo.save(Medicine.builder()
                    .name("Omeprazole 20mg").genericName("Omeprazole").category("Capsule")
                    .manufacturer("Dr. Reddy's").price(8.0).stockQuantity(40).reorderLevel(50)
                    .batchNumber("BAT-003").active(true).build());
            medicineRepo.save(Medicine.builder()
                    .name("Cetirizine 10mg").genericName("Cetirizine").category("Tablet")
                    .manufacturer("Cipla").price(3.0).stockQuantity(600).reorderLevel(100)
                    .batchNumber("BAT-004").active(true).build());
            medicineRepo.save(Medicine.builder()
                    .name("Ibuprofen 400mg").genericName("Ibuprofen").category("Tablet")
                    .manufacturer("Mankind").price(6.0).stockQuantity(20).reorderLevel(50)
                    .batchNumber("BAT-005").active(true).build());
            medicineRepo.save(Medicine.builder()
                    .name("Cough Syrup").genericName("Dextromethorphan").category("Syrup")
                    .manufacturer("Dabur").price(85.0).stockQuantity(150).reorderLevel(30)
                    .batchNumber("BAT-006").active(true).build());

            // ---- Appointments (today) ----
            appointmentRepo.save(Appointment.builder()
                    .patient(p1).doctor(doc1)
                    .appointmentDate(LocalDate.now())
                    .appointmentTime(LocalTime.of(10, 0))
                    .reason("Routine heart checkup")
                    .status("Scheduled").build());
            appointmentRepo.save(Appointment.builder()
                    .patient(p2).doctor(doc2)
                    .appointmentDate(LocalDate.now())
                    .appointmentTime(LocalTime.of(11, 30))
                    .reason("Follow-up for asthma")
                    .status("Scheduled").build());
            appointmentRepo.save(Appointment.builder()
                    .patient(p3).doctor(doc1)
                    .appointmentDate(LocalDate.now())
                    .appointmentTime(LocalTime.of(14, 0))
                    .reason("Blood pressure monitoring")
                    .status("Scheduled").build());
            appointmentRepo.save(Appointment.builder()
                    .patient(p4).doctor(doc3)
                    .appointmentDate(LocalDate.now())
                    .appointmentTime(LocalTime.of(15, 30))
                    .reason("Knee pain consultation")
                    .status("Scheduled").build());

            // ---- Lab Reports ----
            labReportRepo.save(LabReport.builder()
                    .patient(p1).doctor(doc1)
                    .testName("Complete Blood Count (CBC)")
                    .testCategory("Blood Test")
                    .result("Hemoglobin: 14.2 g/dL\nWBC: 6,800 /mcL\nRBC: 4.8 million/mcL\nPlatelets: 250,000 /mcL")
                    .normalRange("Hemoglobin: 13.5 - 17.5 g/dL")
                    .remarks("Normal complete blood profile.")
                    .cost(450.0)
                    .status("Completed")
                    .build());

            labReportRepo.save(LabReport.builder()
                    .patient(p2).doctor(doc2)
                    .testName("Chest X-Ray (PA View)")
                    .testCategory("X-Ray")
                    .result("Bilateral lung fields clear. No evidence of active consolidation or pleural effusion.")
                    .normalRange("Normal lung fields")
                    .remarks("Clear chest radiography.")
                    .cost(800.0)
                    .status("Completed")
                    .build());

            labReportRepo.save(LabReport.builder()
                    .patient(p3).doctor(doc1)
                    .testName("Fasting Blood Sugar (FBS) & HbA1c")
                    .testCategory("Biochemistry")
                    .result("Pending sample analysis")
                    .normalRange("FBS: 70 - 100 mg/dL, HbA1c: < 5.7%")
                    .remarks("Awaiting lab technician review")
                    .cost(600.0)
                    .status("In Progress")
                    .build());

            labReportRepo.save(LabReport.builder()
                    .patient(p5).doctor(doc1)
                    .testName("Lipid Profile & Serum Electrolytes")
                    .testCategory("Blood Test")
                    .normalRange("Cholesterol: < 200 mg/dL")
                    .remarks("Urgent cardiac profile check")
                    .cost(1100.0)
                    .status("Pending")
                    .build());

            // ---- Bills ----
            Bill b1 = Bill.builder()
                    .patient(p1)
                    .consultationFee(800.0)
                    .medicineCost(250.0)
                    .labTestCost(450.0)
                    .roomCharges(1000.0)
                    .otherCharges(0.0)
                    .discount(100.0)
                    .paidAmount(2520.0)
                    .paymentMethod("UPI")
                    .status("Paid")
                    .description("OPD Consultation + Blood Work + 2 Days Observation")
                    .build();
            b1.calculateTotal();
            b1.setPaidAmount(b1.getTotalAmount()); // Full payment
            billRepo.save(b1);

            Bill b2 = Bill.builder()
                    .patient(p2)
                    .consultationFee(600.0)
                    .medicineCost(380.0)
                    .labTestCost(800.0)
                    .roomCharges(0.0)
                    .otherCharges(50.0)
                    .discount(0.0)
                    .paidAmount(1000.0)
                    .paymentMethod("Card")
                    .status("Partial")
                    .description("Pediatric Consult & Chest Radiography")
                    .build();
            b2.calculateTotal();
            billRepo.save(b2);

            Bill b3 = Bill.builder()
                    .patient(p3)
                    .consultationFee(800.0)
                    .medicineCost(120.0)
                    .labTestCost(600.0)
                    .roomCharges(0.0)
                    .otherCharges(0.0)
                    .discount(0.0)
                    .paidAmount(0.0)
                    .paymentMethod("Cash")
                    .status("Pending")
                    .description("Diabetes management checkup")
                    .build();
            b3.calculateTotal();
            billRepo.save(b3);

            System.out.println("✅ Database seeded successfully!");
            System.out.println("📋 Default credentials:");
            System.out.println("   Admin    → admin / admin123");
            System.out.println("   Doctor   → dr.sharma / doctor123");
            System.out.println("   Reception→ reception / reception123");
        };
    }

    private void seedPrescriptions(PrescriptionRepository prescriptionRepo, PatientRepository patientRepo, DoctorRepository doctorRepo) {
        System.out.println("💊 Seeding sample medical prescriptions (Rx)...");
        java.util.List<Patient> patients = patientRepo.findAll();
        java.util.List<Doctor> doctors = doctorRepo.findAll();
        if (patients.isEmpty() || doctors.isEmpty()) return;

        Patient p1 = patients.get(0);
        Doctor doc1 = doctors.get(0);

        Prescription rx1 = Prescription.builder()
                .prescriptionNumber("RX-2026-1001")
                .patient(p1)
                .doctor(doc1)
                .prescriptionDate(LocalDate.now())
                .diagnosis("Type 2 Diabetes Mellitus & Essential Hypertension")
                .symptoms("Mild fatigue, polydipsia, high morning blood sugar levels")
                .vitals("BP: 138/88 mmHg | Pulse: 76 bpm | SpO2: 98% | Temp: 98.4°F")
                .advice("Low carbohydrate diet, 30 mins brisk walking daily. Restrict salt intake.")
                .followUpDate(LocalDate.now().plusDays(30))
                .status("Active")
                .build();

        PrescriptionItem item1 = PrescriptionItem.builder()
                .prescription(rx1)
                .medicineName("Metformin 500mg")
                .dosage("1 Tablet")
                .frequency("1-0-1 (Morning & Night)")
                .timing("After Food")
                .duration("30 Days")
                .quantity(60)
                .instructions("Take with or immediately after meals")
                .build();

        PrescriptionItem item2 = PrescriptionItem.builder()
                .prescription(rx1)
                .medicineName("Telmisartan 40mg")
                .dosage("1 Tablet")
                .frequency("1-0-0 (Morning only)")
                .timing("After Food")
                .duration("30 Days")
                .quantity(30)
                .instructions("Take daily in the morning")
                .build();

        rx1.addItem(item1);
        rx1.addItem(item2);
        prescriptionRepo.save(rx1);

        if (patients.size() > 1 && doctors.size() > 1) {
            Patient p2 = patients.get(1);
            Doctor doc2 = doctors.get(1);

            Prescription rx2 = Prescription.builder()
                    .prescriptionNumber("RX-2026-1002")
                    .patient(p2)
                    .doctor(doc2)
                    .prescriptionDate(LocalDate.now().minusDays(2))
                    .diagnosis("Acute Respiratory Infection & Bronchitis")
                    .symptoms("Frequent dry cough, mild chest congestion, sore throat")
                    .vitals("BP: 120/78 mmHg | Pulse: 80 bpm | SpO2: 99% | Temp: 99.2°F")
                    .advice("Steam inhalation twice daily. Avoid chilled water and cold drinks. Adequate hydration.")
                    .followUpDate(LocalDate.now().plusDays(7))
                    .status("Dispensed")
                    .build();

            PrescriptionItem item3 = PrescriptionItem.builder()
                    .prescription(rx2)
                    .medicineName("Amoxicillin 500mg")
                    .dosage("1 Capsule")
                    .frequency("1-0-1 (Morning & Night)")
                    .timing("After Food")
                    .duration("5 Days")
                    .quantity(10)
                    .instructions("Complete full 5-day course without stopping")
                    .dispensed(true)
                    .build();

            PrescriptionItem item4 = PrescriptionItem.builder()
                    .prescription(rx2)
                    .medicineName("Paracetamol 650mg")
                    .dosage("1 Tablet")
                    .frequency("SOS (As needed)")
                    .timing("After Food")
                    .duration("3 Days")
                    .quantity(6)
                    .instructions("Take only if fever or headache persists")
                    .dispensed(true)
                    .build();

            rx2.addItem(item3);
            rx2.addItem(item4);
            prescriptionRepo.save(rx2);
        }
        System.out.println("✅ Sample prescriptions (Rx) seeded successfully!");
    }
}
