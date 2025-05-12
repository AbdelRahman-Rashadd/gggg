import java.util.ArrayList;
import java.util.List;
import java.util.Map;
class Student extends User {
    private String studentId;
    private String admissionDate;
    private String academicStatus;
    private List<String> enrolledCourses;
    private double gpa;

    public Student(String id, String username, String password, String name, 
String email, String phone, String studentId, String admissionDate) {
        super(id, username, password, name, email, phone);
        setStudentId(studentId);
        setAdmissionDate(admissionDate);
        this.academicStatus = "ACTIVE";
        this.enrolledCourses = new ArrayList<>();
        this.gpa = 0.0;
    }

    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) {
        if (studentId == null || !studentId.matches("^[A-Za-z]{2}\\d{6}$")) {
            throw new IllegalArgumentException("Student ID must be in format AA123456");
        }
        this.studentId = studentId.toUpperCase();
    }

    public String getAdmissionDate() { return admissionDate; }
    public void setAdmissionDate(String admissionDate) {
        if (admissionDate == null || !admissionDate.matches("^\\d{4}-\\d{2}-\\d{2}$")) {
            throw new IllegalArgumentException("Admission date must be in YYYY-MM-DD format");
        }
        this.admissionDate = admissionDate;
    }

    public String getAcademicStatus() { return academicStatus; }
    public void setAcademicStatus(String academicStatus) {
        List<String> validStatuses = List.of("ACTIVE", "PROBATION", "GRADUATED", "DROPPED");
        if (!validStatuses.contains(academicStatus.toUpperCase())) {
            throw new IllegalArgumentException("Invalid academic status");
        }
        this.academicStatus = academicStatus;
    }

    public List<String> getEnrolledCourses() { return new ArrayList<>(enrolledCourses); }
    public double getGpa() { return gpa; }

    private void setGpa(double gpa) {
        if (gpa < 0 || gpa > 4.0) {
            throw new IllegalArgumentException("GPA must be between 0.0 and 4.0");
        }
        this.gpa = Math.round(gpa * 100) / 100.0;
    }

    public boolean enrollCourse(String courseId) {
        if (courseId == null || courseId.trim().isEmpty()) return false;
        if (enrolledCourses.size() >= 6) {
            throw new IllegalStateException("Maximum course load reached (6 courses)");
        }
        if (!enrolledCourses.contains(courseId)) {
            enrolledCourses.add(courseId);
            return true;
        }
        return false;
    }

    public boolean dropCourse(String courseId) {
        if (enrolledCourses.contains(courseId)) {
            enrolledCourses.remove(courseId);
            return true;
        }
        return false;
    }

    public void calculateGpa(Map <String, Double> courseGrades) {
        if (courseGrades == null || courseGrades.isEmpty()) {
            this.gpa = 0.0;
            return;
        }
        double totalPoints = 0.0;
        int totalCredits = 0;
        for (String courseId : enrolledCourses) {
            if (courseGrades.containsKey(courseId)) {
                totalPoints += courseGrades.get(courseId);
                totalCredits += 3;
            }
        }
        setGpa(totalCredits > 0 ? (totalPoints / totalCredits)  : 0.0);
    }

    public String getRole() { return "STUDENT"; }
    @Override 
    public void displayDashboard() {
        System.out.println("Student Dashboard");
        System.out.println("ID: " + getId());
        System.out.println("Name: " + getName());
        System.out.println("Email: " + getEmail());
        System.out.println("Phone: " + getPhone());
        System.out.println("Student ID: " + studentId);
        System.out.println("Admission Date: " + admissionDate);
        System.out.println("Academic Status: " + academicStatus);
        System.out.println("Enrolled Courses: " + enrolledCourses);
        System.out.println("GPA: " + gpa);
    }

    public void enrollInCourse(String courseId) {
        // Logic to enroll the student in a course
        if (!getEnrolledCourses().contains(courseId)) {
            getEnrolledCourses().add(courseId);
        }
    }
}