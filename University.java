import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class University implements Serializable {
    private String name;
    private List<Student> students;
    private List<Faculty> faculty;
    private List<Course> courses;
    private List<Department> departments;
    private Map<String, String> userCredentials; // username -> password
    private Map<String, String> userRoles;      // username -> role
    private Map<String, Map<String, String>> grades; // studentId -> (courseId -> grade)
    private List<AdminStaff> adminStaff;
    private List<SystemAdmin> systemAdmins;

    public University(String name) {
        setName(name);
        this.students = new ArrayList<>();
        this.faculty = new ArrayList<>();
        this.courses = new ArrayList<>();
        this.departments = new ArrayList<>();
        this.userCredentials = new HashMap<>();
        this.userRoles = new HashMap<>();
        this.grades = new HashMap<>();
        this.adminStaff = new ArrayList<>();
        this.systemAdmins = new ArrayList<>();
    }

    // Getters and Setters with validation
    public String getName() { return name; }
    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("University name cannot be empty");
        }
        this.name = name;
    }

    public List<Student> getStudents() { return new ArrayList<>(students); }
    public List<Faculty> getFaculty() { return new ArrayList<>(faculty); }
    public List<Course> getCourses() { return new ArrayList<>(courses); }
    public List<Department> getDepartments() { return new ArrayList<>(departments); }
    public List<AdminStaff> getAdminStaff() { return new ArrayList<>(adminStaff); }
    public List<SystemAdmin> getSystemAdmins() { return new ArrayList<>(systemAdmins); }

    // Business methods
    public boolean addStudent(Student student) {
        if (student == null) return false;
        if (students.stream().anyMatch(s -> s.getId().equals(student.getId()))) {
            throw new IllegalArgumentException("Student with this ID already exists");
        }
        students.add(student);
        userCredentials.put(student.getUsername(), student.getPassword());
        userRoles.put(student.getUsername(), "STUDENT");
        return true;
    }

    public boolean addFaculty(Faculty facultyMember) {
        if (facultyMember == null) return false;
        if (faculty.stream().anyMatch(f -> f.getId().equals(facultyMember.getId()))) {
            throw new IllegalArgumentException("Faculty with this ID already exists");
        }
        faculty.add(facultyMember);
        userCredentials.put(facultyMember.getUsername(), facultyMember.getPassword());
        userRoles.put(facultyMember.getUsername(), "FACULTY");
        return true;
    }

    public boolean addCourse(Course course) {
        if (course == null) return false;
        if (courses.stream().anyMatch(c -> c.getCourseId().equals(course.getCourseId()))) {
            throw new IllegalArgumentException("Course with this ID already exists");
        }
        courses.add(course);
        return true;
    }

    public boolean addDepartment(Department department) {
        if (department == null) return false;
        if (departments.stream().anyMatch(d -> d.getDepartmentId().equals(department.getDepartmentId()))) {
            throw new IllegalArgumentException("Department with this ID already exists");
        }
        departments.add(department);
        return true;
    }

    public User authenticateUser(String username, String password) {
        for (Student student : students) {
            if (student.getUsername().equals(username) && student.getPassword().equals(password)) {
                return student;
            }
        }
        for (Faculty faculty : faculty) {
            if (faculty.getUsername().equals(username) && faculty.getPassword().equals(password)) {
                return faculty;
            }
        }
        for (AdminStaff adminStaff : adminStaff) {
            if (adminStaff.getUsername().equals(username) && adminStaff.getPassword().equals(password)) {
                return adminStaff;
            }
        }
        for (SystemAdmin systemAdmin : systemAdmins) {
            if (systemAdmin.getUsername().equals(username) && systemAdmin.getPassword().equals(password)) {
                return systemAdmin;
            }
        }
        return null; // Return null if no user is found
    }

    public Student findStudentById(String studentId) {
        return students.stream()
            .filter(s -> s.getStudentId().equals(studentId))
            .findFirst()
            .orElse(null);
    }

    public Faculty findFacultyById(String facultyId) {
        return faculty.stream()
            .filter(f -> f.getFacultyId().equals(facultyId))
            .findFirst()
            .orElse(null);
    }
public Course findCourseById(String courseId) {
    for (Course course : courses) {
        if (course.getCourseId().equals(courseId)) {
            return course;
        }
    }
    return null; // Return null if the course is not found
}

    public Department findDepartmentById(String departmentId) {
        return departments.stream()
            .filter(d -> d.getDepartmentId().equals(departmentId))
            .findFirst()
            .orElse(null);
    }
    public void addUserCredential(String username, String password) {
        if (userCredentials == null) {
            userCredentials = new HashMap<>();
        }
        userCredentials.put(username, password);
    }
public void addUserRole(String username, String role) {
    if (userRoles == null) {
        userRoles = new HashMap<>();
    }
    userRoles.put(username, role);
}
    // Existing methods and fields in the University class

// Add this method to return user credentials
public Map<String, String> getUserCredentials() {
    // Assuming you have a Map<String, String> field named userCredentials
    return userCredentials;
}

// Add this method to the University class
public String getGradeForStudentInCourse(String studentId, String courseId) {
    // Implement logic to retrieve the grade for the student in the specified course
    // Example: Assuming you have a Map<String, Map<String, String>> grades where
    // the outer key is the studentId, the inner key is the courseId, and the value is the grade
    if (grades.containsKey(studentId) && grades.get(studentId).containsKey(courseId)) {
        return grades.get(studentId).get(courseId);
    }
    return "N/A"; // Return "N/A" if no grade is found
}

// Add this method to the University class
public List<Course> getAllCourses() {
    // Assuming you have a list of courses in the University class
    return this.courses; // Replace 'courses' with the actual field name storing courses
}

// Method to get all users
public List<User> getAllUsers() {
    List<User> allUsers = new ArrayList<>();
    allUsers.addAll(getStudents());
    allUsers.addAll(getFaculty());
    allUsers.addAll(getAdminStaff());
    allUsers.addAll(getSystemAdmins());
    return allUsers;
}

// Method to add an AdminStaff to the university
public void addAdminStaff(AdminStaff adminStaff) {
    // Assuming there is a list to store AdminStaff objects
    this.adminStaff.add(adminStaff);
}

// Method to add a SystemAdmin to the university
public void addSystemAdmin(SystemAdmin systemAdmin) {
    // Assuming there is a list to store SystemAdmin objects
    this.systemAdmins.add(systemAdmin);
}
}