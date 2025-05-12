import java.util.ArrayList;
import java.util.List;

public class Course  {
    private String courseId;
    private String title;
    private String description;
    private int creditHours;
    private List<String> prerequisites;
    private String facultyId;
    private int capacity;
    private List<String> enrolledStudents;
    private String schedule;
    private String location;

    public Course(String courseId, String title, String description, int creditHours) {
        setCourseId(courseId);
        setTitle(title);
        setDescription(description);
        setCreditHours(creditHours);
        this.prerequisites = new ArrayList<>();
        this.enrolledStudents = new ArrayList<>();
        this.capacity = 30; // Default capacity
    }

    // Getters and Setters with validation
    public String getCourseId() { return courseId; }
    public void setCourseId(String courseId) {
        if (courseId == null || !courseId.matches("^[A-Z]{2,4}\\d{3}$")) {
            throw new IllegalArgumentException("Course ID must be in format ABC123");
        }
        this.courseId = courseId.toUpperCase();
    }

    public String getTitle() { return title; }
    public void setTitle(String title) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Title cannot be empty");
        }
        this.title = title;
    }

    public String getDescription() { return description; }
    public void setDescription(String description) {
        if (description == null || description.trim().isEmpty()) {
            throw new IllegalArgumentException("Description cannot be empty");
        }
        this.description = description;
    }

    public int getCreditHours() { return creditHours; }
    public void setCreditHours(int creditHours) {
        if (creditHours < 1 || creditHours > 4) {
            throw new IllegalArgumentException("Credit hours must be between 1 and 4");
        }
        this.creditHours = creditHours;
    }

    public List<String> getPrerequisites() { return new ArrayList<>(prerequisites); }
    
    public String getFacultyId() { return facultyId; }
    public void setFacultyId(String facultyId) {
        this.facultyId = facultyId;
    }

    public int getCapacity() { return capacity; }
    public void setCapacity(int capacity) {
        if (capacity < 10 || capacity > 100) {
            throw new IllegalArgumentException("Capacity must be between 10 and 100");
        }
        this.capacity = capacity;
    }

    public List<String> getEnrolledStudents() { return new ArrayList<>(enrolledStudents); }

    public String getSchedule() { return schedule; }
    public void setSchedule(String schedule) {
        if (schedule != null && !schedule.matches("^[MTWRF]+ \\d{2}:\\d{2}-\\d{2}:\\d{2}$")) {
            throw new IllegalArgumentException("Schedule must be in format MWF 09:00-10:30");
        }
        this.schedule = schedule;
    }

    public String getLocation() { return location; }
    public void setLocation(String location) {
        this.location = location;
    }

    // Business methods
    public boolean addPrerequisite(String courseId) {
        if (courseId != null && !prerequisites.contains(courseId)) {
            prerequisites.add(courseId);
            return true;
        }
        return false;
    }

    public boolean enrollStudent(String studentId) {
        if (studentId == null || studentId.trim().isEmpty()) {
            return false;
        }
        if (enrolledStudents.size() >= capacity) {
            throw new IllegalStateException("Course capacity reached");
        }
        if (!enrolledStudents.contains(studentId)) {
            enrolledStudents.add(studentId);
            return true;
        }
        return false;
    }

    public boolean dropStudent(String studentId) {
        return enrolledStudents.remove(studentId);
    }

    public int getAvailableSeats() {
        return capacity - enrolledStudents.size();
    }

    public boolean hasPrerequisites(List<String> completedCourses) {
        return completedCourses.containsAll(prerequisites);
    }
}