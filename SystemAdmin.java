import java.util.ArrayList;
import java.util.List;
class SystemAdmin extends User {
    private String adminId;
    private String securityLevel;
    private List<User> managedUsers = new ArrayList<>();

    public SystemAdmin(String userId, String username, String password, String name, String email, String contactInfo,
String adminId, String securityLevel) {
        super(userId, username, password, name, email, contactInfo);
        this.adminId = adminId;
        this.securityLevel = securityLevel;
    }

    public void createUser(User user) {
        if (user != null) {
            managedUsers.add(user);
            System.out.println("User created: " + user.getName() + " (" + user.getRole() + ")");
        }
    }

    public void deleteUser(User user) {
        if (user != null && managedUsers.remove(user)) {
            System.out.println("User deleted: " + user.getName());
        } else {
            System.out.println("User not found or could not be deleted.");
        }
    }

    public void listUsers() {
        System.out.println("--- Managed Users ---");
        for (User user : managedUsers) {
            System.out.println(user.getRole() + ": " + user.getName());
        }
    }

    public void modifySystemSettings() {
        System.out.println(getName() + " is modifying system settings.");
    }

    public void backupData() {
        System.out.println(getName() + " performed system backup.");
    }

    public void managePermissions() {
        System.out.println(getName() + " is managing user permissions.");
    }

    @Override
    public String getRole() {
        return "SYSTEM_ADMIN";
    }
    @Override
    public void displayDashboard() {
        System.out.println("System Admin Dashboard for " + getName());
    }
}
