import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.table.DefaultTableModel;

public class UniversityManagementGUI {
    private JFrame mainFrame;
    private University university;
    private User currentUser;

    public UniversityManagementGUI() {
        university = FileManager.loadUniversity();
        initializeUI();
    }

    private void initializeUI() {
        mainFrame = new JFrame("University Management System");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(800, 600);
        mainFrame.setLayout(new BorderLayout());

        showWelcomePanel();

        mainFrame.setVisible(true);
    }

    private void showWelcomePanel() {
        JPanel welcomePanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.CENTER;

        JLabel titleLabel = new JLabel("University Management System");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        welcomePanel.add(titleLabel, gbc);

        JButton loginButton = new JButton("Login");
        loginButton.setPreferredSize(new Dimension(150, 30));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        welcomePanel.add(loginButton, gbc);

        JButton signUpButton = new JButton("Sign Up");
        signUpButton.setPreferredSize(new Dimension(150, 30));
        gbc.gridx = 1;
        gbc.gridy = 1;
        welcomePanel.add(signUpButton, gbc);

        loginButton.addActionListener(e -> showLoginPanel());
        signUpButton.addActionListener(e -> showSignUpPanel());

        mainFrame.getContentPane().removeAll();
        mainFrame.add(welcomePanel, BorderLayout.CENTER);
        mainFrame.revalidate();
        mainFrame.repaint();
    }

    private void showSignUpPanel() {
        JPanel signUpPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.LINE_END;

        JLabel titleLabel = new JLabel("Create New Account");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        signUpPanel.add(titleLabel, gbc);

        JLabel roleLabel = new JLabel("Account Type:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.LINE_END;
        signUpPanel.add(roleLabel, gbc);

        JComboBox<String> roleCombo = new JComboBox<>(new String[]{"Student", "Faculty", "Admin Staff", "System Admin"});
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        signUpPanel.add(roleCombo, gbc);

        JLabel usernameLabel = new JLabel("Username:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.LINE_END;
        signUpPanel.add(usernameLabel, gbc);

        JTextField usernameField = new JTextField(15);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        signUpPanel.add(usernameField, gbc);

        JLabel passwordLabel = new JLabel("Password:");
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.LINE_END;
        signUpPanel.add(passwordLabel, gbc);

        JPasswordField passwordField = new JPasswordField(15);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        signUpPanel.add(passwordField, gbc);

        JLabel confirmPasswordLabel = new JLabel("Confirm Password:");
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.LINE_END;
        signUpPanel.add(confirmPasswordLabel, gbc);

        JPasswordField confirmPasswordField = new JPasswordField(15);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        signUpPanel.add(confirmPasswordField, gbc);

        JButton createButton = new JButton("Create Account");
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        signUpPanel.add(createButton, gbc);

        JButton backButton = new JButton("Back");
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        signUpPanel.add(backButton, gbc);

        createButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            String confirmPassword = new String(confirmPasswordField.getPassword());
            String role = (String) roleCombo.getSelectedItem();

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(mainFrame, "Username and password cannot be empty", 
                    "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!password.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(mainFrame, "Passwords do not match", 
                    "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (password.length() < 6) {
                JOptionPane.showMessageDialog(mainFrame, "Password must be at least 6 characters", 
                    "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Check if username already exists
            if (university.getUserCredentials().containsKey(username)) {
                JOptionPane.showMessageDialog(mainFrame, "Username already exists", 
                    "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Create a new user based on role
            try {
                String id = generateId(role); // Generate ID with the correct format
                switch (role) {
                    case "Student":
                        Student student = new Student(
                            id, username, password, "New Student", 
                            "", "", id, "2023-01-01");
                        university.addStudent(student);
                        university.addUserCredential(username, password);
                        university.addUserRole(username, "STUDENT");
                        break;
                    case "Faculty":
                        Faculty faculty = new Faculty(
                            id, username, password, "New Faculty", 
                            "", "", id, "Computer Science", "N/A");
                        university.addFaculty(faculty);
                        university.addUserCredential(username, password);
                        university.addUserRole(username, "FACULTY");
                        break;
                    case "Admin Staff":
                        AdminStaff admin = new AdminStaff(
                            id, username, password, "New Admin", 
                            "", "", id, "Administration", "Staff");
                        university.addAdminStaff(admin);
                        university.addUserCredential(username, password);
                        university.addUserRole(username, "ADMIN");
                        break;
                    case "System Admin":
                        SystemAdmin sysAdmin = new SystemAdmin(
                            id, username, password, "System Admin", 
                            "", "", id, "High");
                        university.addSystemAdmin(sysAdmin);
                        university.addUserCredential(username, password);
                        university.addUserRole(username, "SYSTEM_ADMIN");
                        break;
                }

                FileManager.saveUniversity(university);
                JOptionPane.showMessageDialog(mainFrame, "Account created successfully!", 
                    "Success", JOptionPane.INFORMATION_MESSAGE);
                showLoginPanel();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(mainFrame, "Error creating account: " + ex.getMessage(), 
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        backButton.addActionListener(e -> showWelcomePanel());

        mainFrame.getContentPane().removeAll();
        mainFrame.add(signUpPanel, BorderLayout.CENTER);
        mainFrame.revalidate();
        mainFrame.repaint();
    }

    private String generateId(String role) {
        long time = System.currentTimeMillis();
        switch (role) {
            case "Student":
                return String.format("ST%06d", time % 1000000);
            case "Faculty":
                return String.format("F%04d", time % 10000);
            case "Admin Staff":
                return String.format("AD%04d", time % 10000);
            case "System Admin":
                return String.format("SA%03d", time % 1000);
            default:
                throw new IllegalArgumentException("Invalid role: " + role);
        }
    }

    private void showLoginPanel() {
        JPanel loginPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel titleLabel = new JLabel("Login");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        loginPanel.add(titleLabel, gbc);

        JLabel usernameLabel = new JLabel("Username:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.LINE_END;
        loginPanel.add(usernameLabel, gbc);

        JTextField usernameField = new JTextField(15);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        loginPanel.add(usernameField, gbc);

        JLabel passwordLabel = new JLabel("Password:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.LINE_END;
        loginPanel.add(passwordLabel, gbc);

        JPasswordField passwordField = new JPasswordField(15);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        loginPanel.add(passwordField, gbc);

        JButton loginButton = new JButton("Login");
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        loginPanel.add(loginButton, gbc);

        JButton backButton = new JButton("Back");
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        loginPanel.add(backButton, gbc);

        loginButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            currentUser = university.authenticateUser(username, password);
            if (currentUser != null) {
                showDashboard();
            } else {
                JOptionPane.showMessageDialog(mainFrame, "Invalid username or password", 
                    "Login Failed", JOptionPane.ERROR_MESSAGE);
            }
        });

        backButton.addActionListener(e -> showWelcomePanel());

        mainFrame.getContentPane().removeAll();
        mainFrame.add(loginPanel, BorderLayout.CENTER);
        mainFrame.revalidate();
        mainFrame.repaint();
    }

    private void showDashboard() {
        if (currentUser instanceof Student) {
            createStudentDashboard();
        } else if (currentUser instanceof Faculty) {
            createFacultyDashboard();
        } else if (currentUser instanceof AdminStaff) {
            createAdminDashboard();
        } else if (currentUser instanceof SystemAdmin) {
            createSystemAdminDashboard();
        } else {
            JOptionPane.showMessageDialog(mainFrame, "Unknown user role", 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void showCourseEnrollmentDialog() {
        JDialog enrollmentDialog = new JDialog(mainFrame, "Enroll in a Course", true);
        enrollmentDialog.setSize(400, 300);
        enrollmentDialog.setLayout(new BorderLayout());

        JPanel panel = new JPanel(new BorderLayout());
        JLabel label = new JLabel("Available Courses:");
        panel.add(label, BorderLayout.NORTH);

        DefaultListModel<String> courseListModel = new DefaultListModel<>();
        for (Course course : university.getAllCourses()) {
            courseListModel.addElement(course.getCourseId() + " - " + course.getTitle());
        }
        JList<String> courseList = new JList<>(courseListModel);
        panel.add(new JScrollPane(courseList), BorderLayout.CENTER);

        JButton enrollButton = new JButton("Enroll");
        enrollButton.addActionListener(e -> {
            int selectedIndex = courseList.getSelectedIndex();
            if (selectedIndex != -1) {
                String selectedCourse = courseListModel.get(selectedIndex);
                String courseId = selectedCourse.split(" - ")[0];
                ((Student) currentUser).enrollInCourse(courseId);
                JOptionPane.showMessageDialog(enrollmentDialog, "Enrolled successfully!", 
                    "Success", JOptionPane.INFORMATION_MESSAGE);
                enrollmentDialog.dispose();
            } else {
                JOptionPane.showMessageDialog(enrollmentDialog, "Please select a course to enroll.", 
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        panel.add(enrollButton, BorderLayout.SOUTH);
        enrollmentDialog.add(panel);
        enrollmentDialog.setLocationRelativeTo(mainFrame);
        enrollmentDialog.setVisible(true);
    }

    private void createStudentDashboard() {
        JPanel studentDashboard = new JPanel(new BorderLayout());

        // Welcome label
        JLabel welcomeLabel = new JLabel("Welcome, " + currentUser.getName() + "!");
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        studentDashboard.add(welcomeLabel, BorderLayout.NORTH);

        // Tabbed pane for student actions
        JTabbedPane tabbedPane = new JTabbedPane();

        // Enrolled courses tab
        JPanel coursesPanel = new JPanel(new BorderLayout());
        DefaultListModel<String> courseListModel = new DefaultListModel<>();
        for (String courseId : ((Student) currentUser).getEnrolledCourses()) {
            Course course = university.findCourseById(courseId);
            if (course != null) {
                courseListModel.addElement(course.getCourseId() + " - " + course.getTitle());
            }
        }
        JList<String> courseList = new JList<>(courseListModel);
        coursesPanel.add(new JScrollPane(courseList), BorderLayout.CENTER);

        // Add buttons for enrolling and dropping courses
        JPanel courseButtonPanel = new JPanel();
        JButton enrollButton = new JButton("Enroll in Course");
        JButton dropButton = new JButton("Drop Selected Course");

        enrollButton.addActionListener(e -> showCourseEnrollmentDialog());
        dropButton.addActionListener(e -> {
            int selectedIndex = courseList.getSelectedIndex();
            if (selectedIndex != -1) {
                String selectedCourse = courseListModel.get(selectedIndex);
                String courseId = selectedCourse.split(" - ")[0];
                ((Student) currentUser).dropCourse(courseId);
                courseListModel.remove(selectedIndex);
                JOptionPane.showMessageDialog(mainFrame, "Course dropped successfully!", 
                    "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(mainFrame, "Please select a course to drop.", 
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        courseButtonPanel.add(enrollButton);
        courseButtonPanel.add(dropButton);
        coursesPanel.add(courseButtonPanel, BorderLayout.SOUTH);

        tabbedPane.addTab("Enrolled Courses", coursesPanel);

        // Grades tab
        JPanel gradesPanel = new JPanel(new BorderLayout());
        String[] columnNames = {"Course ID", "Course Title", "Grade"};
        DefaultTableModel gradesModel = new DefaultTableModel(columnNames, 0);
        for (String courseId : ((Student) currentUser).getEnrolledCourses()) {
            Course course = university.findCourseById(courseId);
            if (course != null) {
                String grade = university.getGradeForStudentInCourse(((Student) currentUser).getStudentId(), courseId);
                gradesModel.addRow(new Object[]{course.getCourseId(), course.getTitle(), grade});
            }
        }
        JTable gradesTable = new JTable(gradesModel);
        gradesPanel.add(new JScrollPane(gradesTable), BorderLayout.CENTER);
        tabbedPane.addTab("Grades", gradesPanel);

        // Add the tabbed pane to the dashboard
        studentDashboard.add(tabbedPane, BorderLayout.CENTER);

        // Logout button
        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> {
            currentUser = null;
            showWelcomePanel();
        });
        studentDashboard.add(logoutButton, BorderLayout.SOUTH);

        // Update the main frame
        mainFrame.getContentPane().removeAll();
        mainFrame.add(studentDashboard, BorderLayout.CENTER);
        mainFrame.revalidate();
        mainFrame.repaint();
    }

    private void createFacultyDashboard() {
        JPanel facultyDashboard = new JPanel(new BorderLayout());

        JLabel welcomeLabel = new JLabel("Welcome, " + currentUser.getName() + "!");
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        facultyDashboard.add(welcomeLabel, BorderLayout.NORTH);

        JTabbedPane tabbedPane = new JTabbedPane();

        // Assigned courses tab
        JPanel coursesPanel = new JPanel(new BorderLayout());
        DefaultListModel<String> courseListModel = new DefaultListModel<>();
        for (String courseId : ((Faculty) currentUser).getTeachingCourses()) {
            Course course = university.findCourseById(courseId);
            if (course != null) {
                courseListModel.addElement(course.getCourseId() + " - " + course.getTitle());
            }
        }
        JList<String> courseList = new JList<>(courseListModel);
        coursesPanel.add(new JScrollPane(courseList), BorderLayout.CENTER);
        tabbedPane.addTab("Assigned Courses", coursesPanel);

        // Add the tabbed pane to the dashboard
        facultyDashboard.add(tabbedPane, BorderLayout.CENTER);

        // Logout button
        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> {
            currentUser = null;
            showWelcomePanel();
        });
        facultyDashboard.add(logoutButton, BorderLayout.SOUTH);

        mainFrame.getContentPane().removeAll();
        mainFrame.add(facultyDashboard, BorderLayout.CENTER);
        mainFrame.revalidate();
        mainFrame.repaint();
    }

    private void createAdminDashboard() {
        JPanel adminDashboard = new JPanel(new BorderLayout());

        JLabel welcomeLabel = new JLabel("Welcome, Admin!");
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        adminDashboard.add(welcomeLabel, BorderLayout.NORTH);

        JTabbedPane tabbedPane = new JTabbedPane();

        // Users management tab
        JPanel usersPanel = new JPanel(new BorderLayout());
        DefaultListModel<String> userListModel = new DefaultListModel<>();
        for (User user : university.getAllUsers()) {
            userListModel.addElement(user.getRole() + ": " + user.getName());
        }
        JList<String> userList = new JList<>(userListModel);
        usersPanel.add(new JScrollPane(userList), BorderLayout.CENTER);
        tabbedPane.addTab("Manage Users", usersPanel);

        // Add the tabbed pane to the dashboard
        adminDashboard.add(tabbedPane, BorderLayout.CENTER);

        // Logout button
        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> {
            currentUser = null;
            showWelcomePanel();
        });
        adminDashboard.add(logoutButton, BorderLayout.SOUTH);

        mainFrame.getContentPane().removeAll();
        mainFrame.add(adminDashboard, BorderLayout.CENTER);
        mainFrame.revalidate();
        mainFrame.repaint();
    }

    private void createSystemAdminDashboard() {
        JPanel systemAdminDashboard = new JPanel(new BorderLayout());

        JLabel welcomeLabel = new JLabel("Welcome, System Admin!");
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        systemAdminDashboard.add(welcomeLabel, BorderLayout.NORTH);

        JTabbedPane tabbedPane = new JTabbedPane();

        // System settings tab
        JPanel settingsPanel = new JPanel(new BorderLayout());
        JLabel settingsLabel = new JLabel("System Settings");
        settingsLabel.setHorizontalAlignment(SwingConstants.CENTER);
        settingsPanel.add(settingsLabel, BorderLayout.CENTER);
        tabbedPane.addTab("Settings", settingsPanel);

        // Add the tabbed pane to the dashboard
        systemAdminDashboard.add(tabbedPane, BorderLayout.CENTER);

        // Logout button
        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> {
            currentUser = null;
            showWelcomePanel();
        });
        systemAdminDashboard.add(logoutButton, BorderLayout.SOUTH);

        mainFrame.getContentPane().removeAll();
        mainFrame.add(systemAdminDashboard, BorderLayout.CENTER);
        mainFrame.revalidate();
        mainFrame.repaint();
    }

    // ... [Rest of the methods remain the same as in your original code]
    // (showCourseDetails, showStudentForm, showFacultyForm, 
    // showCourseForm, main method)

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new UniversityManagementGUI());
    }
}