package Base;
import java.io.*;
import java.util.Scanner;

public class SimpleMacroTracker {
    private static final String DATA_FOLDER = "user_data";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        UserManager userManager = new UserManager();
        User currentUser = null;

        while (currentUser == null) {
            System.out.println("1. Login\n2. Register\nChoose an option:");
            int choice = scanner.nextInt();
            scanner.nextLine();

            System.out.println("Enter username:");
            String username = scanner.nextLine();
            System.out.println("Enter password:");
            String password = scanner.nextLine();

            if (choice == 1 && userManager.login(username, password)) {
                System.out.println("Login successful!");
                currentUser = new User(username, password);
                loadUserData(currentUser);
            } else if (choice == 2 && userManager.register(username, password)) {
                System.out.println("Registration successful!");
                currentUser = new User(username, password);
            } else {
                System.out.println("Invalid credentials or registration failed. Try again.");
            }
        }

        MacroTracker tracker = currentUser.tracker;
        while (true) {
            System.out.println("1. Set Todays Goals\n2. Log Food\n3. Remove Logged Food\n4. Show Logged Food\n5. Show Progress\n6. Save & Exit\n7. Reset Data");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    System.out.println("Enter calorie goal:");
                    int goalCalories = scanner.nextInt();
                    System.out.println("Enter carb goal (g):");
                    int goalCarbs = scanner.nextInt();
                    System.out.println("Enter fat goal (g):");
                    int goalFats = scanner.nextInt();
                    System.out.println("Enter protein goal (g):");
                    int goalProteins = scanner.nextInt();
                    scanner.nextLine(); 
                    tracker.setGoals(goalCalories, goalCarbs, goalFats, goalProteins);
                    break;
            
                case 2:
                    System.out.println("Enter food name:");
                    String name = scanner.nextLine();

                    
                    int carbs = getValidInt(scanner, "Enter carbs (g):");
                    int fats = getValidInt(scanner, "Enter fats (g):");
                    int proteins = getValidInt(scanner, "Enter proteins (g):");
                    int calories = getValidInt(scanner, "Enter calories:");

                    scanner.nextLine(); 

                    System.out.println("Enter meal category: 'breakfast, lunch, dinner, snack' ");
                    String mealCategory = scanner.nextLine();

                    tracker.addFood(name, carbs, fats, proteins, calories, mealCategory);
                    break;
                case 3:
                    System.out.println("Enter food name to remove:");
                    tracker.removeFood(scanner.nextLine());
                    break;

                case 4:
                    tracker.showLog();
                    break;
                case 5:
                    tracker.showProgress();
                    break;
                case 6:
                    saveUserData(currentUser);
                    System.out.println("Data saved. Exiting...");
                    scanner.close();
                    return;
                case 7:
                    resetUserData(currentUser);
                    System.out.println("All user data has been reset.");
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }
    private static int getValidInt(Scanner scanner, String prompt) {
        System.out.println(prompt);
        while (!scanner.hasNextInt()) {
            System.out.println("Invalid input. Please enter a valid number.");
            scanner.next(); 
        }
        return scanner.nextInt();
    }

    private static void saveUserData(User user) {
        new File(DATA_FOLDER).mkdirs();
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DATA_FOLDER + user.username + ".dat"))) {
            oos.writeObject(user.tracker);
        } catch (IOException e) {
            System.out.println("Error saving data.");
        }
    }

    private static void loadUserData(User user) {
        File file = new File(DATA_FOLDER + user.username + ".dat");
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                user.tracker = (MacroTracker) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("Error loading data.");
            }
        }
    }

    private static void resetUserData(User user) {
        
        user.tracker = new MacroTracker();

        
        File file = new File(DATA_FOLDER + user.username + ".dat");
        if (file.exists() && file.delete()) {
            System.out.println("User data file deleted successfully.");
        } else {
            System.out.println("No user data file found to delete.");
        }
    }
}





