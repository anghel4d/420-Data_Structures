package ca.qc.johnabbott.cs406;

/**
 * Represents a student's grade.
 */
public class Grade implements Comparable<Grade> {
    // Fields
    private long studentID;
    private String firstName;
    private String lastName;
    private double grade;

    /**
     * Parse input line to construct a grade.
     * @param line
     */
    public Grade(String line) {
        // Set student ID
        String[] fields = line.split(",");
        studentID = Long.parseLong(fields[0]);

        // Set student name
        String[] fullName = fields[1].split("_");
        firstName = fullName[0];
        lastName = fullName[1];

        // Set student grade
        grade = Double.parseDouble(fields[2]);
    }

    // Public Getters
    public long getStudentID(){
        return studentID;
    }

    public String getFirstName(){
        return firstName;
    }

    public String getLastName(){
        return lastName;
    }

    public double getGrade(){
        return grade;
    }

    // Class Methods
    public int compareTo(Grade other) {
        return (int) (this.grade - other.grade);
    }

    // Overrides
    @Override
    public String toString() {
        return String.format("%d %s %s %f", studentID, firstName, lastName, grade);
    }
}
