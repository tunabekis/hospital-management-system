package hospital;

/**
 * A hospital patient: their identity, attending doctor, visit date, and
 * personal medical care team.
 *
 * Patients are ordered primarily by a numeric key derived from their visit
 * date, so that the enclosing {@link BinarySearchTree} keeps them sorted
 * chronologically; patient name is used as a tiebreaker so that two
 * different patients who happen to share a visit date remain distinct
 * entries instead of colliding (see {@link #compareTo}).
 */
public class Patient implements Comparable<Patient>, Named {

    private static final int DAYS_PER_YEAR = 365;
    private static final int DAYS_PER_MONTH = 30;
    private static final int INITIAL_TEAM_CAPACITY = 16;

    private final String patientName;
    private final String doctorName;
    private final int visitDay;
    private final int visitMonth;
    private final int visitYear;
    private final int visitKey;
    private final BinarySearchTree<MedicalStaff> medicalTeam;

    public Patient(String patientName, String doctorName, int visitDay, int visitMonth, int visitYear) {
        this.patientName = patientName;
        this.doctorName = doctorName;
        this.visitDay = visitDay;
        this.visitMonth = visitMonth;
        this.visitYear = visitYear;
        this.visitKey = DAYS_PER_YEAR * visitYear + DAYS_PER_MONTH * (visitMonth - 1) + visitDay;
        this.medicalTeam = new BinarySearchTree<>(INITIAL_TEAM_CAPACITY);
    }

    @Override
    public int compareTo(Patient other) {
        int dateComparison = Integer.compare(this.visitKey, other.visitKey);
        if (dateComparison != 0) {
            return dateComparison;
        }
        return this.patientName.compareTo(other.patientName);
    }

    @Override
    public String getName() {
        return patientName;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public int getVisitDay() {
        return visitDay;
    }

    public int getVisitMonth() {
        return visitMonth;
    }

    public int getVisitYear() {
        return visitYear;
    }

    public BinarySearchTree<MedicalStaff> getMedicalTeam() {
        return medicalTeam;
    }

    @Override
    public String toString() {
        return patientName + ", " + visitYear + ", " + doctorName;
    }
}
