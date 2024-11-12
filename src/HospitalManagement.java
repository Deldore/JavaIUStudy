import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

class Bill {
    private static int idCounter = 0;
    private int id;
    private String name;
    private double amount;

    public Bill(String name, double amount) {
        this.id = idCounter++;
        this.name = name;
        this.amount = amount;
    }
    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public double getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return "Bill ID: " + id + " Name: " + name + " Amount: $" + amount;
    }
}

class Patient {
    private static int idCounter = 0;
    private int id;
    private String name;
    private Bill bill;
    public Patient(String name) {
        this.id = idCounter++;
        this.name = name;
    }
    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public Bill getBill() {
        return bill;
    }
    public void setBill(Bill bill) {
        this.bill = bill;
    }
    public void payBill() {
        if (bill != null) {
            System.out.println("Patient " + name + " has payed the bill:" + bill);
            bill = null;
        } else {
            System.out.println("No bill to pay for patient " + name);
        }
    }
    @Override
    public String toString() {
        return "Patient ID: " + id + " Name: " + name + "Bill: " + (bill == null ? "No bills" : bill);
    }
}

class Receptionist {
    private static int idCounter = 1;
    private int id;
    private String name;
    private List<Patient> appointments;

    public Receptionist(String name) {
        this.id = idCounter++;
        this.name = name;
        this.appointments = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void giveAppointment(Patient patient) {
        appointments.add(patient);
        System.out.println("Appointment given to " + patient.getName() + " by receptionist " + name);
    }

    public void generateBill(Patient patient, String billName, double amount) {
        Bill bill = new Bill(billName, amount);
        patient.setBill(bill);
        System.out.println("Bill generated for " + patient.getName() + " by receptionist " + name + ": " + bill);
    }

    @Override
    public String toString() {
        return "Receptionist ID: " + id + ", Name: " + name;
    }
}

class Doctor {
    private static int idCounter = 1;
    private int id;
    private String name;
    private List<Patient> checkedPatients;

    public Doctor(String name) {
        this.id = idCounter++;
        this.name = name;
        this.checkedPatients = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void checkPatient(Patient patient) {
        checkedPatients.add(patient);
        System.out.println("Patient " + patient.getName() + " checked by doctor " + name);
    }

    @Override
    public String toString() {
        return "Doctor ID: " + id + ", Name: " + name;
    }
}

public class HospitalManagement {
    private static Map<Integer, Patient> patients = new HashMap<>();
    private static Map<Integer, Receptionist> receptionists = new HashMap<>();
    private static Map<Integer, Doctor> doctors = new HashMap<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Welcome to the Hospital Management System!");
            System.out.println("Choose your role:");
            System.out.println("1. Patient");
            System.out.println("2. Receptionist");
            System.out.println("3. Doctor");
            System.out.println("* 4. Exit");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1: {
                    handlePatient(scanner);
                    break;
                }
                case 2: {
                    handleReceptionist(scanner);
                    break;
                }
                case 3: {
                    handleDoctor(scanner);
                    break;
                }
                case 4: {
                    return;
                }
                default: {
                    System.out.println("Invalid choice. Try again.");
                }
            }
        }
    }
    private static void handlePatient(Scanner scanner) {
        System.out.println("Please enter your name:");
        String name = scanner.nextLine();
        Patient patient = new Patient(name);
        patients.put(patient.getId(), patient);
        System.out.println("Patient " + patient.getName() + " has been added to the database");
        while (true) {
            System.out.println("Choose an action:");
            System.out.println("1. Pay Bill");
            System.out.println("2. Exit");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1: {
                    patient.payBill();
                    break;
                }
                case 2: {
                    return;
                }
                default: {
                    System.out.println("Invalid choice. Try again.");
                }
            }
        }
    }
    private static void handleReceptionist(Scanner scanner) {
        System.out.print("Enter receptionist name: ");
        String name = scanner.nextLine();
        Receptionist receptionist = new Receptionist(name);
        receptionists.put(receptionist.getId(), receptionist);
        System.out.println("Receptionist registered: " + receptionist);

        while (true) {
            System.out.println("1. Give Appointment");
            System.out.println("2. Generate Bill");
            System.out.println("3. Exit");
            System.out.print("Choose an action: ");
            int action = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (action) {
                case 1:
                    System.out.print("Enter patient ID: ");
                    int patientId = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    Patient patient = patients.get(patientId);
                    if (patient != null) {
                        receptionist.giveAppointment(patient);
                    } else {
                        System.out.println("Patient not found.");
                    }
                    break;
                case 2:
                    System.out.print("Enter patient ID: ");
                    patientId = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    patient = patients.get(patientId);
                    if (patient != null) {
                        System.out.print("Enter bill name: ");
                        String billName = scanner.nextLine();
                        System.out.print("Enter bill amount: ");
                        double amount = scanner.nextDouble();
                        scanner.nextLine(); // Consume newline
                        receptionist.generateBill(patient, billName, amount);
                    } else {
                        System.out.println("Patient not found.");
                    }
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void handleDoctor(Scanner scanner) {
        System.out.print("Enter doctor name: ");
        String name = scanner.nextLine();
        Doctor doctor = new Doctor(name);
        doctors.put(doctor.getId(), doctor);
        System.out.println("Doctor registered: " + doctor);

        while (true) {
            System.out.println("1. Check Patient");
            System.out.println("2. Exit");
            System.out.print("Choose an action: ");
            int action = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (action) {
                case 1:
                    System.out.print("Enter patient ID: ");
                    int patientId = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    Patient patient = patients.get(patientId);
                    if (patient != null) {
                        doctor.checkPatient(patient);
                    } else {
                        System.out.println("Patient not found.");
                    }
                    break;
                case 2:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
