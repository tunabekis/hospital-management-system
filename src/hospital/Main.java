package hospital;

/**
 * Console demo that exercises {@link HospitalDatabase}: adding and
 * overwriting patients, managing their medical care teams, and querying the
 * database by patient, doctor, and visit year.
 */
public class Main {

    public static void main(String[] args) {
        HospitalDatabase hospitalDatabase = new HospitalDatabase();

        hospitalDatabase.showAllPatients();

        hospitalDatabase.addPatient("Michael Johnson", "Emma Thompson", 19, 12, 2022);
        hospitalDatabase.addPatient("Ethan Lee", "Olivia Sanchez", 8, 9, 2020);
        hospitalDatabase.addPatient("Noah Miller", "Olivia Sanchez", 27, 2, 2019);
        hospitalDatabase.addPatient("Liam Davis", "Isabella Martinez", 3, 4, 2022);
        hospitalDatabase.addPatient("Ava Taylor", "Isabella Martinez", 15, 5, 2024);
        hospitalDatabase.addPatient("Mason Moore", "William Anderson", 7, 6, 2021);
        hospitalDatabase.addPatient("Charlotte Garcia", "Lucas Lewis", 30, 10, 2023);
        hospitalDatabase.addPatient("Noah Miller", "Olivia Sanchez", 27, 2, 2019);
        hospitalDatabase.showAllPatients();

        hospitalDatabase.removePatient("Ava Taylor");
        hospitalDatabase.showAllPatients();
        hospitalDatabase.showPatient("Michael Johnson");

        hospitalDatabase.addMember("Mason Moore", "Daniel Roberts", "Nurse");
        hospitalDatabase.addMember("Mason Moore", "Victoria Stewart", "Radiologist");
        hospitalDatabase.addMember("Mason Moore", "Tyler Campbell", "Medical Assistant");
        hospitalDatabase.addMember("Mason Moore", "Hannah Martin", "Paramedic");
        hospitalDatabase.addMember("Michael Johnson", "Jack Allen", "Patient Care Technician");
        hospitalDatabase.addMember("Michael Johnson", "Oliver Nelson", "Anesthesiologist");
        hospitalDatabase.addMember("Michael Johnson", "Sophia Rivera", "Pathologist");
        hospitalDatabase.addMember("Michael Johnson", "Evan Hall", "Laboratory Technician");
        hospitalDatabase.addMember("Michael Johnson", "Megan Price", "Nurse");
        hospitalDatabase.addMember("Ava Taylor", "Brianna Reed", "Dietitian");
        hospitalDatabase.addMember("Charlotte Garcia", "Oliver Nelson", "Anesthesiologist");
        hospitalDatabase.addMember("Charlotte Garcia", "Trevor Jenkins", "Medical Equipment Technician");
        hospitalDatabase.addMember("Charlotte Garcia", "Justin Flores", "Speech-Language Pathologist");

        hospitalDatabase.showPatient("Mason Moore");
        hospitalDatabase.showPatient("Michael Johnson");

        hospitalDatabase.removeMember("Michael Johnson", "Evan Hall");
        hospitalDatabase.showPatient("Michael Johnson");

        hospitalDatabase.showDoctorPatients("Olivia Sanchez");
        hospitalDatabase.showDoctorPatients("Emma Thompson");
        hospitalDatabase.showPatients(2022);
    }
}
