package hospital;

/**
 * Marks a type that can be looked up by a human-readable name inside a
 * {@link BinarySearchTree}, decoupling name-based lookup from any specific
 * domain class (e.g. {@link Patient}, {@link MedicalStaff}).
 */
public interface Named {

    String getName();
}
