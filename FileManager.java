import java.io.*;

public class FileManager {
    private static final String UNIVERSITY_FILE = "university_data.ser";

    public static void saveUniversity(University university) {
        try (ObjectOutputStream oos = new ObjectOutputStream(
            new FileOutputStream(UNIVERSITY_FILE))) {
            oos.writeObject(university);
            System.out.println("University data saved successfully");
        } catch (IOException e) {
            System.err.println("Error saving university data: " + e.getMessage());
        }
    }

    public static University loadUniversity() {
        File file = new File(UNIVERSITY_FILE);
        if (!file.exists()) {
            System.out.println("No existing data found. Creating new university.");
            return new University("Alexandria University");
        }

        try (ObjectInputStream ois = new ObjectInputStream(
            new FileInputStream(UNIVERSITY_FILE))) {
            University university = (University) ois.readObject();
            System.out.println("University data loaded successfully");
            return university;
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading university data: " + e.getMessage());
            return new University("Alexandria University");
        }
    }
}