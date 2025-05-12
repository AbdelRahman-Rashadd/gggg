import java.util.ArrayList;
import java.util.List;

public class Faculty extends User {
    private String facultyId;
    private String department;
    private String specialization;
    private List<String> teachingCourses;
    private String officeLocation;
    private String officeHours;

    public Faculty(String id, String username, String password, String name,
String email, String phone, String facultyId, String department,
String specialization) {
        super(id, username, password, name, email, phone);
        setFacultyId(facultyId);
        setDepartment(department);
        setSpecialization(specialization);
        this.teachingCourses = new ArrayList<>();
    }

    // Getters and Setters with validation
    public String getFacultyId() { return facultyId; }
    public void setFacultyId(String facultyId) {
        if (facultyId == null || !facultyId.matches("^F\\d{4}$")) {
            throw new IllegalArgumentException("Faculty ID must be in F1234 format");
        }
        this.facultyId = facultyId.toUpperCase();
    }

    public String getDepartment() { return department; }
    public void setDepartment(String department) {
        if (department == null || department.trim().isEmpty()) {
            throw new IllegalArgumentException("Department cannot be empty");
        }
        this.department = department;
    }

    public String getSpecialization() { return specialization; }
    public void setSpecialization(String specialization) {
        if (specialization == null || specialization.trim().isEmpty()) {
            throw new IllegalArgumentException("Specialization cannot be empty");
        }
        this.specialization = specialization;
    }

    public List<String> getTeachingCourses() { return new ArrayList<>(teachingCourses); }

    public String getOfficeLocation() { return officeLocation; }
    public void setOfficeLocation(String officeLocation) {
        this.officeLocation = officeLocation;
    }

  public String getOfficeHours() {
    return officeHours;
}
public void setOfficeHours(String officeHours) {
    if (officeHours != null && !officeHours.matches("^\\d{2}:\\d{2}-\\d{2}:\\d{2}$")) {
        throw new IllegalArgumentException("Office hours must be in format HH:MM-HH:MM");
    }
    this.officeHours = officeHours;
}
    
    public boolean assignCourse(String courseId) {
        if (courseId == null || courseId.trim().isEmpty()) {
            return false;
        }
        if (teachingCourses.size() >= 4) {
            throw new IllegalStateException("Maximum teaching load reached (4 courses)");
        }
        if (!teachingCourses.contains(courseId)) {
            teachingCourses.add(courseId);
            return true;
        }
        return false;
    }

    public boolean removeCourse(String courseId) {
        return teachingCourses.remove(courseId);
    }

    @Override
    public String getRole() { return "FACULTY"; }
    @Override
    public void displayDashboard() {
        System.out.println("Faculty Dashboard");
        System.out.println("Name: " + getName());
        System.out.println("Department: " + department);
        System.out.println("Specialization: " + specialization);
        System.out.println("Teaching Courses: " + teachingCourses);
        System.out.println("Office Location: " + officeLocation);
        System.out.println("Office Hours: " + officeHours);
    }
} 