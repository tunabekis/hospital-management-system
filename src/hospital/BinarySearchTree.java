package hospital;

import java.util.ArrayList;
import java.util.List;

/**
 * A generic Binary Search Tree backed by three parallel {@link ArrayList}s:
 * one holding each node's value, and two holding the array index of that
 * node's left and right child ({@link #NO_CHILD} when absent). Node
 * placement is governed by {@link Comparable#compareTo}.
 *
 * Storing explicit child links - rather than deriving a child's position
 * from its parent's position via index arithmetic - keeps memory usage
 * proportional to the number of elements actually stored, regardless of how
 * balanced or skewed the resulting tree shape is. This tree does not
 * self-balance, so operations remain O(log n) on average but O(n) in the
 * worst case (e.g. elements inserted in sorted order), matching a classic
 * unbalanced BST.
 *
 * @param <T> the element type, which must be mutually comparable
 */
public class BinarySearchTree<T extends Comparable<T>> {

    private static final int NO_CHILD = -1;

    private final ArrayList<T> values;
    private final ArrayList<Integer> leftChild;
    private final ArrayList<Integer> rightChild;
    private int root = NO_CHILD;

    public BinarySearchTree(int initialCapacity) {
        values = new ArrayList<>(initialCapacity);
        leftChild = new ArrayList<>(initialCapacity);
        rightChild = new ArrayList<>(initialCapacity);
    }

    public void insert(T value) {
        if (root == NO_CHILD) {
            root = addNode(value);
            return;
        }
        insertAt(root, value);
    }

    private void insertAt(int index, T value) {
        int comparison = value.compareTo(values.get(index));
        if (comparison < 0) {
            int left = leftChild.get(index);
            if (left == NO_CHILD) {
                leftChild.set(index, addNode(value));
            } else {
                insertAt(left, value);
            }
        } else if (comparison > 0) {
            int right = rightChild.get(index);
            if (right == NO_CHILD) {
                rightChild.set(index, addNode(value));
            } else {
                insertAt(right, value);
            }
        }
        // Equal keys are intentionally left untouched here; callers that need
        // "insert or overwrite" semantics should locate the index first (see
        // HospitalDatabase.addPatient) and overwrite explicitly via overwriteAt.
    }

    private int addNode(T value) {
        values.add(value);
        leftChild.add(NO_CHILD);
        rightChild.add(NO_CHILD);
        return values.size() - 1;
    }

    public void delete(T value) {
        root = deleteAt(root, value);
    }

    /** Removes {@code value} from the subtree rooted at {@code index} and returns the new subtree root. */
    private int deleteAt(int index, T value) {
        if (index == NO_CHILD) {
            return NO_CHILD;
        }

        int comparison = value.compareTo(values.get(index));
        if (comparison < 0) {
            leftChild.set(index, deleteAt(leftChild.get(index), value));
            return index;
        }
        if (comparison > 0) {
            rightChild.set(index, deleteAt(rightChild.get(index), value));
            return index;
        }

        int left = leftChild.get(index);
        int right = rightChild.get(index);
        if (left == NO_CHILD || right == NO_CHILD) {
            values.set(index, null); // detach: this slot is no longer part of the tree
            return left == NO_CHILD ? right : left;
        }

        // Two children: splice in the in-order successor (the leftmost node
        // of the right subtree) and delete it from where it used to be.
        int successor = findMin(right);
        values.set(index, values.get(successor));
        rightChild.set(index, deleteAt(right, values.get(successor)));
        return index;
    }

    private int findMin(int index) {
        while (leftChild.get(index) != NO_CHILD) {
            index = leftChild.get(index);
        }
        return index;
    }

    public void inOrderTraversal() {
        inOrder(root);
        System.out.println();
    }

    private void inOrder(int index) {
        if (index == NO_CHILD) {
            return;
        }
        inOrder(leftChild.get(index));
        System.out.println(values.get(index));
        inOrder(rightChild.get(index));
    }

    public void inReverseOrderTraversal() {
        inReverseOrder(root);
        System.out.println();
    }

    private void inReverseOrder(int index) {
        if (index == NO_CHILD) {
            return;
        }
        inReverseOrder(rightChild.get(index));
        System.out.println(values.get(index));
        inReverseOrder(leftChild.get(index));
    }

    /** Returns the array index of the node equal to {@code value}, or -1 if absent. */
    public int findIndex(T value) {
        for (int i = 0; i < values.size(); i++) {
            T candidate = values.get(i);
            if (candidate != null && candidate.compareTo(value) == 0) {
                return i;
            }
        }
        return -1;
    }

    /** Returns the array index of the {@link Named} node whose name equals {@code name}, or -1 if absent. */
    public int findIndexByName(String name) {
        for (int i = 0; i < values.size(); i++) {
            T candidate = values.get(i);
            if (candidate instanceof Named && ((Named) candidate).getName().equals(name)) {
                return i;
            }
        }
        return -1;
    }

    /** Returns the value stored at the given array index, as returned by {@link #findIndex} / {@link #findIndexByName}. */
    public T getAt(int index) {
        return values.get(index);
    }

    /** Replaces the value already stored at the given array index, without changing the tree's shape. */
    public void overwriteAt(int index, T value) {
        values.set(index, value);
    }

    /** Returns all values currently in the tree, in ascending (in-order) sequence. */
    public List<T> valuesInOrder() {
        List<T> result = new ArrayList<>();
        collectInOrder(root, result);
        return result;
    }

    private void collectInOrder(int index, List<T> out) {
        if (index == NO_CHILD) {
            return;
        }
        collectInOrder(leftChild.get(index), out);
        out.add(values.get(index));
        collectInOrder(rightChild.get(index), out);
    }
}
