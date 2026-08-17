package hospital;

/**
 * A single member of a patient's medical care team (e.g. a nurse or
 * radiologist), identified by name and ordered alphabetically.
 */
public class MedicalStaff implements Comparable<MedicalStaff>, Named {

    private final String name;
    private final String role;

    public MedicalStaff(String name, String role) {
        this.name = name;
        this.role = role;
    }

    @Override
    public int compareTo(MedicalStaff other) {
        return this.name.compareTo(other.name);
    }

    @Override
    public String getName() {
        return name;
    }

    public String getRole() {
        return role;
    }

    @Override
    public String toString() {
        return name + ", " + role;
    }
}
