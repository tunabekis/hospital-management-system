package hospital;

/**
 * In-memory hospital database: all patients live in a single BST ordered by
 * visit date, and each patient owns a nested BST for their personal medical
 * care team (five patients means five independent care-team trees).
 */
public class HospitalDatabase {

    private static final int INITIAL_PATIENT_CAPACITY = 16;

    private final BinarySearchTree<Patient> patients = new BinarySearchTree<>(INITIAL_PATIENT_CAPACITY);

    /** Adds a new patient, or overwrites the existing record for that patient name. */
    public void addPatient(String patientName, String doctorName, int visitDay, int visitMonth, int visitYear) {
        Patient patient = new Patient(patientName, doctorName, visitDay, visitMonth, visitYear);
        int index = patients.findIndexByName(patientName);
        if (index == -1) {
            patients.insert(patient);
        } else {
            patients.overwriteAt(index, patient);
        }
    }

    public void removePatient(String patientName) {
        int index = patients.findIndexByName(patientName);
        if (index == -1) {
            System.out.println("There is no patient by given name");
            return;
        }
        patients.delete(patients.getAt(index));
    }

    /** Adds a medical staff member to a patient's care team, or overwrites an existing member with the same name. */
    public void addMember(String patientName, String memberName, String memberRole) {
        Patient patient = findPatientOrWarn(patientName);
        if (patient == null) {
            return;
        }
        BinarySearchTree<MedicalStaff> team = patient.getMedicalTeam();
        int staffIndex = team.findIndexByName(memberName);
        MedicalStaff member = new MedicalStaff(memberName, memberRole);
        if (staffIndex == -1) {
            team.insert(member);
        } else {
            team.overwriteAt(staffIndex, member);
        }
    }

    public void removeMember(String patientName, String memberName) {
        Patient patient = findPatientOrWarn(patientName);
        if (patient == null) {
            return;
        }
        BinarySearchTree<MedicalStaff> team = patient.getMedicalTeam();
        int staffIndex = team.findIndexByName(memberName);
        if (staffIndex == -1) {
            System.out.println("No such medical staff member found in this patient's medical care team.");
            return;
        }
        team.delete(team.getAt(staffIndex));
    }

    public void showAllPatients() {
        patients.inOrderTraversal();
    }

    public void showPatient(String patientName) {
        int index = patients.findIndexByName(patientName);
        if (index == -1) {
            System.out.println("Patient not found.");
            return;
        }
        Patient patient = patients.getAt(index);
        System.out.println(patientName);
        System.out.println(patient.getVisitDay() + "/" + patient.getVisitMonth() + "/" + patient.getVisitYear());
        System.out.println(patient.getDoctorName());
        patient.getMedicalTeam().inOrderTraversal();
    }

    public void showDoctorPatients(String doctorName) {
        System.out.println(doctorName);
        for (Patient patient : patients.valuesInOrder()) {
            if (patient.getDoctorName().equals(doctorName)) {
                System.out.println(patient.getName() + ", " + patient.getVisitDay() + "/" + patient.getVisitMonth() + "/" + patient.getVisitYear());
            }
        }
    }

    public void showPatients(int visitYear) {
        System.out.println(visitYear);
        for (Patient patient : patients.valuesInOrder()) {
            if (patient.getVisitYear() == visitYear) {
                System.out.println(patient.getName() + ", " + patient.getVisitDay() + "/" + patient.getVisitMonth());
            }
        }
    }

    private Patient findPatientOrWarn(String patientName) {
        int index = patients.findIndexByName(patientName);
        if (index == -1) {
            System.out.println("No such patient is found.");
            return null;
        }
        return patients.getAt(index);
    }
}
