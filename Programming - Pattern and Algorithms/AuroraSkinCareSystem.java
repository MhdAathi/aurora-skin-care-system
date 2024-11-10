import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

// Base Class: Person
// ! This class represents a generic person with basic contact information.
class Person {
    protected String name;
    protected String email;
    protected String contactNumber;

    // ! Constructor to initialize a Person object
    public Person(String name, String email, String contactNumber) {
        this.name = name;
        this.email = email;
        this.contactNumber = contactNumber;
    }

    // ! This method access the information without exposing the data directly
    public String getContactInfo() {
        return "Name: " + name + ", Email: " + email + ", Contact: " + contactNumber;
    }
}

// Patient Class extending Person
// ! Inheriting basic contact information from the Person class.
class Patient extends Person {
    private String nic; // * National Identity Card number of the patient

    public Patient(String name, String email, String contactNumber, String nic) {
        super(name, email, contactNumber); // * Call to the superclass constructor to set
        this.nic = nic;
    }

    public String getNIC() {
        return nic;
    }
}

// Doctor Class extending Person
// ! Inheriting properties and methods from the Person class.
class Doctor extends Person {
    private List<String> schedule = new ArrayList<>(); // * List to hold the doctor's availability
    protected String employeeID; // Unique identifier for the doctor

    public Doctor(String name, String email, String contactNumber, String employeeID) {
        super(name, email, contactNumber); // * Call to the superclass constructor
        this.employeeID = employeeID; // Set the employee ID
        addAvailability(); // ! Method to populate the doctor's schedule
    }

    // ! Private method to add availability times to the doctor's schedule
    private void addAvailability() {

    }

    public List<String> getSchedule() {
        return schedule;
    }

    public String getEmployeeDetails() {
        return "Employee ID: " + employeeID + ", " + getContactInfo();
    }
}

// Appointment Status Enum
// ! Represents the possible statuses of an appointment.
enum Status {
    BOOKED, CANCELED, COMPLETED;
}

// Appointment Class
// ! This class represents an appointment made by a patient with a doctor.
class Appointment {
    private static int counter = 1;
    private int appointmentID;
    private String date;
    private String time;
    private Status status;
    private Patient patient;
    private Doctor doctor;
    private Treatment treatment;
    private double registrationFee;

    // * Constructor to initialize an Appointment object with date, time,
    // patient,and doctor
    public Appointment(String date, String time, Patient patient, Doctor doctor, Treatment treatment) {
        this.appointmentID = counter++; // * Assign a unique ID and increment the counter
        this.date = date;
        this.time = time;
        this.status = Status.BOOKED; // ! Set the initial status to BOOKED
        this.patient = patient;
        this.doctor = doctor;
        this.treatment = treatment;
        this.registrationFee = 500.0; // ! Default registration fee
    }

    // * Getters for appointment details
    public int getAppointmentID() {
        return appointmentID;
    }

    // * Getter for registration fee
    public double getRegistrationFee() {
        return registrationFee;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public Patient getPatient() {
        return patient;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    // ! Method to update the date and time of the appointment
    public void updateDateTime(String date, String time) {
        this.date = date;
        this.time = time;
        System.out.println("Appointment updated to Date: " + date + ", Time: " + time);
    }

    // ! Same method name operates on different types of objects
    public String getDetails() {
        return "Appointment ID: " + appointmentID + ", Date: " + date + ", Time: " + time + ", Status: " + status +
                ", Patient: " + patient.name + ", Doctor: " + doctor.name + ", Treatment: " + treatment.getDetails();
    }

    public void confirm() {
        status = Status.BOOKED;
        System.out.println("Appointment confirmed for: " + patient.name + " with Dr. " + doctor.name);
    }

    public void cancel() {
        status = Status.CANCELED;
        System.out.println("Appointment canceled for: " + patient.name);
    }

    public Treatment getTreatment() {
        return treatment;
    }

    public static Treatment selectTreatment(Scanner scanner, List<Treatment> availableTreatments) {
        System.out.println("Available Treatments:");
        for (int i = 0; i < availableTreatments.size(); i++) {
            System.out.println((i + 1) + ". " + availableTreatments.get(i).getDetails());
        }
        System.out.print("Select a treatment by number: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        return availableTreatments.get(choice - 1);
    }
}

// Treatment Class
// ! This class represents a treatment that can be provided to a patient.
class Treatment {
    private int treatmentID;
    private String name;
    private double price;

    public Treatment(int treatmentID, String name, double price) {
        this.treatmentID = treatmentID;
        this.name = name;
        this.price = price;
    }

    // * Method to get a formatted string of treatment details
    public String getDetails() {
        return "Name: " + name + ", Price: LKR " + price;
    }

    // * Method to calculate the final price of the treatment
    public double calculateFinalPrice() {
        return price;
    }
}

// Payment Class
// ! This class represents a payment transaction for a specific amount.
class Payment {
    private static final double TAX_RATE = 0.025; // * Constant representing the tax rate (2.5%)
    private double amount; // * The amount to be processed for payment

    public Payment(double amount) {
        this.amount = amount;
    }

    // * Method to calculate the total amount including tax
    public double calculateTotalAmount() {
        return Math.round(amount * (1 + TAX_RATE) * 100.0) / 100.0;
    }
}

// Invoice Class
// ! This class represents an invoice generated for a specific appointment and
// treatment.
class Invoice {
    private int invoiceID;
    private Appointment appointment;
    private Treatment treatment;
    private Payment payment;

    public Invoice(int invoiceID, Appointment appointment, Treatment treatment) {
        this.invoiceID = invoiceID;
        this.appointment = appointment;
        this.treatment = treatment;
        this.payment = new Payment(treatment.calculateFinalPrice());
        // Create a payment object based on treatment price
    }

    // ! This Method allowing users to generate an invoice without needing to
    // understand calculations for taxes and totals.
    public void generateInvoice() {
        System.out.println("\n" + "=".repeat(30));
        System.out.println("               INVOICE               ");
        System.out.println("=" + "=".repeat(28) + "=");

        // Display appointment details
        System.out.printf("Appointment ID:    %d%n", appointment.getAppointmentID());
        System.out.printf("Patient:           %s%n", appointment.getPatient().name);
        System.out.printf("Date:              %s%n", appointment.getDate());
        System.out.printf("Time:              %s%n", appointment.getTime());
        System.out.println("-".repeat(30));

        // Display treatment details
        System.out.printf("%s%n", treatment.getDetails());
        System.out.printf("Registration Fee:  LKR %.2f%n", appointment.getRegistrationFee());

        // Calculate and display tax
        double tax = treatment.calculateFinalPrice() * 0.025;
        System.out.printf("Tax (2.5%%):       LKR %.2f%n", tax);

        // Calculate and display total amount
        double totalAmount = payment.calculateTotalAmount();
        System.out.printf("Total:            LKR %.2f%n", totalAmount);

        System.out.println("=" + "=".repeat(28) + "=");
        System.out.println("Thank you for choosing our services!");
        System.out.println("=" + "=".repeat(30));
    }
}

// Main Program Class
// ! This class serves as the entry point for the Aurora Skin Care Clinic.
public class AuroraSkinCareSystem {
    static List<Patient> patients = new ArrayList<>();
    static List<Doctor> doctors = new ArrayList<>();
    static List<Appointment> appointments = new ArrayList<>();
    static List<Treatment> availableTreatments = new ArrayList<>();
    static int invoiceCounter = 1; // * Counter for generating unique invoice IDs

    // ! Main method to run the application
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in); // * Scanner for user input

        // ! Manually add initial doctors
        doctors.add(new Doctor("Dr. Ijlan", "mohamedijlan02@gmail.com", "0776778795", "D001"));
        doctors.add(new Doctor("Dr. Brian", "jacobmichaelbrian01@gmail.com", "0764517561", "D002"));

        availableTreatments.add(new Treatment(1, "Acne Treatment", 2750.00));
        availableTreatments.add(new Treatment(2, "Skin Whitening", 7650.00));
        availableTreatments.add(new Treatment(3, "Mole Removal", 3850.00));
        availableTreatments.add(new Treatment(4, "Laser Treatment", 12500.00));

        // ! Handle user options with a switch statement
        while (true) {
            System.out.println("\n" + "=".repeat(40));
            System.out.println("       Welcome to Aurora Skin Care Clinic       ");
            System.out.println("=" + "=".repeat(38) + "=");
            System.out.println("Please select an option from the menu below:");
            System.out.println("1. Register Patient");
            System.out.println("2. Make Appointment");
            System.out.println("3. Update Appointment Details");
            System.out.println("4. View Appointment Details by Date");
            System.out.println("5. Search for Appointment by Name or ID");
            System.out.println("6. Search Doctor by Name or ID");
            System.out.println("7. Search Patient by Name or NIC");
            System.out.println("8. Cancel Appointment");
            System.out.println("9. Generate Invoice");
            System.out.println("10. Exit");
            System.out.print("Select an option: ");
            System.out.println("=" + "=".repeat(40));

            try {
                int option = scanner.nextInt(); // This line can throw InputMismatchException
                scanner.nextLine(); // Consume newline

                switch (option) {
                    case 1:
                        registerPatient(scanner);
                        break;
                    case 2:
                        makeAppointment(scanner);
                        break;
                    case 3:
                        updateAppointment(scanner);
                        break;
                    case 4:
                        viewAppointmentsByDate(scanner);
                        break;
                    case 5:
                        searchAppointment(scanner);
                        break;
                    case 6:
                        searchDoctor(scanner);
                        break;
                    case 7:
                        searchPatient(scanner);
                        break;
                    case 8:
                        cancelAppointment(scanner);
                        break;
                    case 9:
                        generateInvoice(scanner);
                        break;
                    case 10:
                        System.out.println("Exiting the system. Goodbye!");
                        return;
                    default:
                        System.out.println("Invalid option, please try again.");
                        break;
                }
                
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine(); // Clear the invalid input
            }
        }
    }

    // ! Method to register a new patient.
    public static void registerPatient(Scanner scanner) {
        System.out.println("\n" + "=".repeat(40));
        System.out.println("            Register New Patient            ");
        System.out.println("=" + "=".repeat(38) + "=");

        System.out.print("Enter Patient Name: ");
        String name = scanner.nextLine().trim();
        System.out.print("Enter Email: ");
        String email = scanner.nextLine().trim();
        System.out.print("Enter Contact Number: ");
        String contactNumber = scanner.nextLine().trim();
        System.out.print("Enter NIC: ");
        String nic = scanner.nextLine().trim();

        // Validate input (simple validation example)
        if (name.isEmpty() || email.isEmpty() || contactNumber.isEmpty() || nic.isEmpty()) {
            System.out.println("All fields are required. Please try again.");
            return;
        }

        patients.add(new Patient(name, email, contactNumber, nic));
        System.out.println("Patient Registered Successfully.");
        System.out.println("=" + "=".repeat(40));
    }

    // ! Method to make a new appointment.
    public static void makeAppointment(Scanner scanner) {
        System.out.println("\n" + "=".repeat(40));
        System.out.println("            Make a New Appointment            ");
        System.out.println("=" + "=".repeat(38) + "=");

        // Get patient NIC and verify if the patient exists
        System.out.print("Enter Patient NIC: ");
        String nic = scanner.nextLine().trim();
        Patient patient = findPatientByNic(nic);

        if (patient == null) {
            System.out.println("Patient not found. Please register the patient first.");
            return;
        }

        // Get appointment date and time
        System.out.print("Enter Date (| Mon | Wed | Fri | Sat |): ");
        String date = scanner.nextLine().trim();
        System.out.print("Enter Time (e.g., 10:00am): ");
        String time = scanner.nextLine().trim();

        // Display and select doctor
        System.out.println("Select Doctor:");
        for (int i = 0; i < doctors.size(); i++) {
            System.out.printf("%d: %s%n", i + 1, doctors.get(i).name);
        }
        System.out.print("Select Doctor (1-" + doctors.size() + "): ");
        int docChoice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        if (docChoice < 1 || docChoice > doctors.size()) {
            System.out.println("Invalid doctor selection. Please try again.");
            return;
        }
        Doctor doctor = doctors.get(docChoice - 1);

        // Assuming availableTreatments is a List<Treatment>
        System.out.println("Select Treatment Type:");
        for (int i = 0; i < availableTreatments.size(); i++) {
            System.out.printf("%d: %s%n", i + 1, availableTreatments.get(i).getDetails());
        }
        System.out.print("Select Treatment (1-" + availableTreatments.size() + "): ");
        int treatmentChoice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        if (treatmentChoice < 1 || treatmentChoice > availableTreatments.size()) {
            System.out.println("Invalid treatment selection. Please try again.");
            return;
        }
        

        // Get the actual Treatment object instead of a string
        Treatment selectedTreatment = availableTreatments.get(treatmentChoice - 1);

        // Create and add the appointment
        Appointment appointment = new Appointment(date, time, patient, doctor, selectedTreatment);
        appointments.add(appointment);

        System.out.println("Appointment booked successfully.");
        System.out.println("Appointment Details: " + appointment.getDetails());
        System.out.println("=" + "=".repeat(40));
    }

    // ! Method to update the details of an existing appointment.
    public static void updateAppointment(Scanner scanner) {
        System.out.println("\n" + "=".repeat(40));
        System.out.println("            Update Appointment Details            ");
        System.out.println("=" + "=".repeat(38) + "=");

        System.out.print("Enter Appointment ID to update: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // * Consume the newline character

        Appointment appointment = findAppointmentByID(id);
        if (appointment == null) {
            System.out.println("Appointment not found. Please check the ID and try again.");
            return;
        }

        System.out.print("Enter New Date (| Mon | Wed | Fri | Sat |): ");
        String newDate = scanner.nextLine().trim(); // * Get new appointment date
        System.out.print("Enter New Time (e.g., 10:00am): ");
        String newTime = scanner.nextLine().trim(); // * Get new appointment time

        // Update the appointment's date and time
        appointment.updateDateTime(newDate, newTime); // * Update method call
        System.out.println("Appointment updated successfully.");
        System.out.println("Updated Appointment Details: " + appointment.getDetails());
        System.out.println("=" + "=".repeat(40));
    }

    // ! Method to view appointments by a specific date.
    public static void viewAppointmentsByDate(Scanner scanner) {
        System.out.println("\n" + "=".repeat(40));
        System.out.println("         View Appointments by Date         ");
        System.out.println("=" + "=".repeat(38) + "=");

        System.out.print("Enter Date (| Mon | Wed | Fri | Sat |) to filter appointments: ");
        String date = scanner.nextLine().trim(); // Get the date from the user to filter appointments

        boolean found = false; // Flag to check if any appointments are found
        for (Appointment appTime : appointments) {
            if (appTime.getDate().equals(date)) { // Check if the appointment's date matches the input date
                System.out.println(appTime.getDetails());
                found = true; // Set the flag to true if at least one appointment is found
            }
        }

        if (!found) {
            System.out.println("No appointments found for the date: " + date);
        }

        System.out.println("=" + "=".repeat(40));
    }

    // ! Method to search for an appointment by ID or patient name.
    public static void searchAppointment(Scanner scanner) {
        System.out.print("Enter Appointment ID or Patient Name to search: ");
        String input = scanner.nextLine();

        try {
            int id = Integer.parseInt(input);
            Appointment appTime = findAppointmentByID(id);
            if (appTime != null) {
                System.out.println(appTime.getDetails());
            } else {
                System.out.println("No appointment found with ID: " + id);
            }
        } catch (NumberFormatException e) {
            // * Handle case where input is not an integer (search by patient name)
            for (Appointment appTime : appointments) {
                // * Check if the patient's name matches the input
                if (appTime.getPatient().name.equalsIgnoreCase(input)) {
                    System.out.println(appTime.getDetails());
                }
            }
        }
    }

    // ! Method to search for a doctor by name or ID.
    public static void searchDoctor(Scanner scanner) {
        System.out.println("\n" + "=".repeat(40));
        System.out.println("           Search for a Doctor           ");
        System.out.println("=" + "=".repeat(38) + "=");

        System.out.print("Enter Doctor Name or ID (D***): ");
        String input = scanner.nextLine().trim(); // Get input and trim whitespace

        boolean found = false; // Flag to check if the doctor is found
        for (Doctor doctor : doctors) {
            // Check if the doctor's name or employee ID matches the input (case
            // insensitive)
            if (doctor.name.equalsIgnoreCase(input) || doctor.employeeID.equalsIgnoreCase(input)) {
                System.out.println("Doctor Found: " + doctor.getEmployeeDetails());
                found = true; // Set the flag to true if the doctor is found
                break;
            }
        }

        if (!found) {
            System.out.println("Doctor not found. Please check the name or ID and try again.");
        }

        System.out.println("=" + "=".repeat(40));
    }

    // ! Method to search for a patient by name or NIC.
    public static void searchPatient(Scanner scanner) {
        System.out.println("\n" + "=".repeat(40));
        System.out.println("           Search for a Patient           ");
        System.out.println("=" + "=".repeat(38) + "=");

        System.out.print("Enter Patient Name or NIC to search: ");
        String input = scanner.nextLine().trim(); // Get input and trim whitespace

        for (Patient patient : patients) {
            // * Check if the patient's name or NIC matches the input (case insensitive)
            if (patient.name.equalsIgnoreCase(input) || patient.getNIC().equalsIgnoreCase(input)) {
                System.out.println("Patient Found: " + patient.getContactInfo() + ", NIC: " + patient.getNIC());
                return;
            }
        }
        System.out.println("Patient not found. Please check the name or NIC and try again.");

    }

    // ! Method to cancel an existing appointment.
    public static void cancelAppointment(Scanner scanner) {
        System.out.println("\n" + "=".repeat(40));
        System.out.println("            Cancel Appointment            ");
        System.out.println("=" + "=".repeat(38) + "=");

        System.out.print("Enter Appointment ID to cancel: ");
        int id = scanner.nextInt(); // * Get the Appointment ID from user input
        Appointment appointment = findAppointmentByID(id); // * Find the appointment by ID

        if (appointment != null) {
            appointment.cancel(); // * Call the cancel method on the appointment if found
        } else {
            System.out.println("Appointment not found.");
        }
        System.out.println("=" + "=".repeat(40));
    }

    // ! Method to generate an invoice for a specific appointment.
    public static void generateInvoice(Scanner scanner) {
        System.out.println("\n" + "=".repeat(40));
        System.out.println("            Generate Invoice            ");
        System.out.println("=" + "=".repeat(38) + "=");

        System.out.print("Enter Appointment ID: ");
        int id = scanner.nextInt();
        Appointment appointment = findAppointmentByID(id);

        if (appointment == null) {
            System.out.println("Appointment not found.");
            return;
        }

        System.out.print(
                "Select Treatment Type (1: Acne Treatment, 2: Skin Whitening, 3: Mole Removal, 4: Laser Treatment): ");
        int treatmentChoice = scanner.nextInt();
        Treatment treatment;

        // ! Determine the treatment based on user selection
        switch (treatmentChoice) {
            case 1:
                treatment = new Treatment(1, "Acne Treatment", 2750.00);
                break;
            case 2:
                treatment = new Treatment(2, "Skin Whitening", 7650.00);
                break;
            case 3:
                treatment = new Treatment(3, "Mole Removal", 3850.00);
                break;
            case 4:
                treatment = new Treatment(4, "Laser Treatment", 12500.00);
                break;
            default:
                System.out.println("Invalid treatment option.");
                return;
        }

        // * Create a new Invoice object using the generated invoice ID.
        Invoice invoice = new Invoice(invoiceCounter++, appointment, treatment);
        invoice.generateInvoice();

        System.out.println("=" + "=".repeat(40));
    }

    // ! Method to find a patient by NIC.
    public static Patient findPatientByNic(String nic) {
        // * Use a stream to filter the list of patients and find the first patient with
        // the matching NIC
        return patients.stream().filter(p -> p.getNIC().equals(nic)).findFirst().orElse(null);
    }

    // ! Method to search for a patient by NIC with user feedback.
    public static void searchPatientByNic(Scanner scanner) {
        System.out.println("\n" + "=".repeat(40));
        System.out.println("           Search for a Patient by NIC           ");
        System.out.println("=" + "=".repeat(38) + "=");

        System.out.print("Enter Patient NIC to search: ");
        String nic = scanner.nextLine().trim(); // Get input and trim whitespace

        Patient patient = findPatientByNic(nic); // Find patient by NIC

        if (patient != null) {
            System.out.println("Patient Found: " + patient.getContactInfo() + ", NIC: " + patient.getNIC());
        } else {
            System.out.println("Patient not found. Please check the NIC and try again.");
        }

        System.out.println("=" + "=".repeat(40));
    }

    // ! Method to find an appointment by ID.
    public static Appointment findAppointmentByID(int id) {
        // * Use a stream to filter the list of appointments and find the first
        // appointment with the matching ID
        return appointments.stream().filter(a -> a.getAppointmentID() == id).findFirst().orElse(null);
    }

    // ! Method to search for an appointment by ID with user feedback.
    public static void searchAppointmentByID(Scanner scanner) {
        System.out.println("\n" + "=".repeat(40));
        System.out.println("         Search for an Appointment by ID         ");
        System.out.println("=" + "=".repeat(38) + "=");

        System.out.print("Enter Appointment ID to search: ");
        int id = scanner.nextInt(); // Get the Appointment ID from user input
        scanner.nextLine(); // Consume the newline character

        Appointment appointment = findAppointmentByID(id); // Find appointment by ID

        if (appointment != null) {
            System.out.println("Appointment Found: " + appointment.getDetails()); // Display appointment details
        } else {
            System.out.println("No appointment found with ID: " + id); // Inform the user if no appointment is found
        }

        System.out.println("=" + "=".repeat(40));
    }
}
