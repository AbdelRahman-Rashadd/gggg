import java.util.ArrayList;
import java.util.List;

public class Department {
    private String departmentId;
    private String name;
    private String chairPersonId;
    private List<String> facultyMembers;
    private List<String> offeredCourses;

    public Department(String departmentId, String name) {
        setDepartmentId(departmentId);
        setName(name);
        this.facultyMembers = new ArrayList<>();
        this.offeredCourses = new ArrayList<>();
    }

    // Getters and Setters with validation
    public String getDepartmentId() { return departmentId; }
    public void setDepartmentId(String departmentId) {
        if (departmentId == null || !departmentId.matches("^[A-Z]{2,5}$")) {
            throw new IllegalArgumentException("Department ID must be 2-5 uppercase letters");
        }
        this.departmentId = departmentId.toUpperCase();
    }

    public String getName() { return name; }
    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Department name cannot be empty");
        }
        this.name = name;
    }

    public String getChairPersonId() { return chairPersonId; }
    public void setChairPersonId(String chairPersonId) {
        this.chairPersonId = chairPersonId;
    }

    public List<String> getFacultyMembers() { return new ArrayList<>(facultyMembers); }
    public List<String> getOfferedCourses() { return new ArrayList<>(offeredCourses); }

    // Business methods
    public boolean addFaculty(String facultyId) {
        if (facultyId != null && !facultyMembers.contains(facultyId)) {
            facultyMembers.add(facultyId);
            return true;
        }
        return false;
    }

    public boolean removeFaculty(String facultyId) {
        return facultyMembers.remove(facultyId);
    }

    public boolean addCourse(String courseId) {
        if (courseId != null && !offeredCourses.contains(courseId)) {
            offeredCourses.add(courseId);
            return true;
        }
        return false;
    }

    public boolean removeCourse(String courseId) {
        return offeredCourses.remove(courseId);
    }
}