class Enrollment {
    private Student student;
    private Course course;
    private String enrollmentDate;
    private String grade;
    private String status;

public Student getStudent() {
    return student;
}

public Course getCourse() {
    return course;
}

public String getEnrollmentDate() {
    return enrollmentDate;
}

public String getGrade() {
    return grade;
}

public void setStudent(Student student) {
    this.student = student;
}

public void setCourse(Course course) {
    this.course = course;
}

public void setEnrollmentDate(String enrollmentDate) {
    this.enrollmentDate = enrollmentDate;
}

public void setGrade(String grade) {
    this.grade = grade;
}

public void setStatus(String status) {
    this.status = status;
}
public double getGradePoints() {
    if (grade == null || grade.isEmpty()) {
        return 0.0; // No grade assigned
    }

    switch (grade.toUpperCase()) {
        case "A":
            return 4.0;
        case "B":
            return 3.0;
        case "C":
            return 2.0;
        case "D":
            return 1.0;
        case "F":
            return 0.0;
        default:
            System.err.println("Invalid grade: " + grade);
            return 0.0; // Default for invalid grades
    }
}
    public void assignGrade(String grade) { this.grade = grade; }
    public String getStatus() { return status; }
    public void withdraw() { this.status = "Withdrawn"; }
}