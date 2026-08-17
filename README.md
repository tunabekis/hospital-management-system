# Hospital Management System

A console-based hospital patient database built on a hand-written, generic
Binary Search Tree (BST). Each patient carries their own nested BST of
medical care team members — five patients means five independent care-team
trees, all sharing the same generic `BinarySearchTree<T>` implementation.

## Features

- **Patient records** — add, update, and remove patients, each with a name,
  attending doctor, and visit date.
- **Medical care teams** — attach/detach staff (nurses, radiologists, etc.)
  to a specific patient's care team.
- **Queries** — list all patients (sorted by visit date), look up a single
  patient's full record, list every patient under a given doctor, or list
  every patient who visited in a given year.
- **Duplicate-safe writes** — re-adding a patient or staff member by the
  same name updates their existing record instead of creating a duplicate.

## Technology

- **Java 17** (standard library only — no external dependencies or build
  tool required).
- A custom generic `BinarySearchTree<T extends Comparable<T>>`, implemented
  with parallel `ArrayList`s (`values`, `leftChild`, `rightChild`) instead
  of `java.util` collections such as `TreeMap`, as an educational exercise
  in implementing tree data structures directly.

## Project Structure

```
hospital-management-system/
├── src/hospital/
│   ├── BinarySearchTree.java   Generic BST: insert, delete, traversal, lookup
│   ├── Named.java              Interface for name-based BST lookups
│   ├── Patient.java            Patient record + its own care-team BST
│   ├── MedicalStaff.java       A single care-team member
│   ├── HospitalDatabase.java   Public API over the patient BST
│   └── Main.java                Demo entry point
└── README.md
```

## Building and Running

From the project root:

```bash
# Compile
javac -d out src/hospital/*.java

# Run the demo
java -cp out hospital.Main
```

The demo in `Main.java` populates the database with sample patients and
doctors, edits and queries their records, and manages a few care teams —
useful both as a smoke test and as a usage example for `HospitalDatabase`.

## Design Notes

- **Ordering.** Patients are ordered by visit date (a single integer key
  computed from day/month/year), with patient name as a tiebreaker so two
  patients who happen to share a visit date remain distinct tree nodes.
  `showAllPatients` therefore prints patients in chronological order.
- **Identity.** A patient's *identity* — used for updates, removal, and
  care-team lookups — is their name, not their visit date; `addPatient`
  looks up by name before deciding whether to insert or overwrite.
- **BST storage.** Nodes are stored in parallel `ArrayList`s with explicit
  child-index links, rather than deriving a child's array position from its
  parent's position. This keeps memory proportional to the number of
  elements actually stored, no matter how skewed the insertion order is
  (e.g. patients added in strict chronological order, which is the most
  natural real-world usage pattern).
- **Balance.** The tree does not self-balance, so — like a classic textbook
  BST — operations are fast on average but can degrade to O(n) on adversarial
  insertion orders (e.g. already-sorted input).
