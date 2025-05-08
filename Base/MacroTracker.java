package Base;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MacroTracker implements Serializable {
    private List<FoodEntry> foodLog = new ArrayList<>();
    private int goalCalories, goalCarbs, goalFats, goalProteins;

    public void setGoals(int calories, int carbs, int fats, int proteins) {
        this.goalCalories = calories;
        this.goalCarbs = carbs;
        this.goalFats = fats;
        this.goalProteins = proteins;
    }

    public void addFood(String name, int carbs, int fats, int proteins, int calories, String mealCategory) {
        foodLog.add(new FoodEntry(name, carbs, fats, proteins, calories, mealCategory));
    }

    public void removeFood(String name) {
        boolean foodFound = foodLog.removeIf(food -> food.name.equalsIgnoreCase(name));

        if (!foodFound) {
            System.out.println("Food not found.");
        } else {
            System.out.println("Food removed successfully.");
        }
    }

    public void showLog() {
        if (foodLog.isEmpty()) {
            System.out.println("No foods have been Logged.");
        } else {
            for (FoodEntry food : foodLog) {
                System.out.println(food);
            }
        }
    }

    public void showProgress() {
        int totalCalories = 0, totalCarbs = 0, totalFats = 0, totalProteins = 0;
        for (FoodEntry food : foodLog) {
            totalCalories += food.calories;
            totalCarbs += food.carbs;
            totalFats += food.fats;
            totalProteins += food.proteins;
        }
        System.out.println("Goal Calories: " + goalCalories + ", Current: " + totalCalories);
        System.out.println("Goal Carbs: " + goalCarbs + "g, Current: " + totalCarbs + "g");
        System.out.println("Goal Fats: " + goalFats + "g, Current: " + totalFats + "g");
        System.out.println("Goal Proteins: " + goalProteins + "g, Current: " + totalProteins + "g");
    }
    public void saveToFile(String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write(goalCalories + " " + goalCarbs + " " + goalFats + " " + goalProteins + "\n");
            for (FoodEntry food : foodLog) {
                writer.write(food.name + "," + food.carbs + "," + food.fats + "," + food.proteins + "," + food.calories + "," + food.mealCategory + "\n");
            }
        } catch (IOException e) {
            System.out.println("Error saving user data: " + e.getMessage());
        }
    }

    
    public void loadFromFile(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String[] goals = reader.readLine().split(" ");
            this.goalCalories = Integer.parseInt(goals[0]);
            this.goalCarbs = Integer.parseInt(goals[1]);
            this.goalFats = Integer.parseInt(goals[2]);
            this.goalProteins = Integer.parseInt(goals[3]);

            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                foodLog.add(new FoodEntry(data[0], Integer.parseInt(data[1]), Integer.parseInt(data[2]), Integer.parseInt(data[3]), Integer.parseInt(data[4]), data[5]));
            }
        } catch (IOException e) {
            System.out.println("No existing data found for user.");
        }
    }
   
}


