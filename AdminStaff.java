  class AdminStaff extends User {
    private String staffId;
    private String department;
    private String role;

    public AdminStaff(String userId, String username, String password, String name, String email, String contactInfo,
String staffId, String department, String role) {
        super(userId, username, password, name, email, contactInfo);
        this.staffId = staffId;
        this.department = department;
        this.role = role;
    }
    public String getStaffId() {
    return staffId;
}

public String getDepartment() {
    return department;
}

public String getRole() {
    return role;
}
public void setStaffId(String staffId) {
    this.staffId = staffId;
}

public void setDepartment(String department) {
    this.department = department;
}

public void setRole(String role) {
    this.role = role;
}

    public void registerStudent(Student student) { }
    public void createCourse(Course course) { }
    public void assignFaculty(Faculty faculty, Course course) { }
    public void generateReports() { }

    public void login() { System.out.println(getName() + " logged in as AdminStaff."); }
    public void logout() { System.out.println(getName() + " logged out."); }
    public void updateProfile() { System.out.println("Profile updated for " + getName()); }
    @Override
    public void displayDashboard() { System.out.println("AdminStaff Dashboard for " + getName()); }
}
