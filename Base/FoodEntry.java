package Base;
import java.io.Serializable;

public class FoodEntry implements Serializable {
    String name;
    int carbs, fats, proteins, calories;
    String mealCategory;

    public FoodEntry(String name, int carbs, int fats, int proteins, int calories, String mealCategory) {
        this.name = name;
        this.carbs = carbs;
        this.fats = fats;
        this.proteins = proteins;
        this.calories = calories;
        this.mealCategory = mealCategory;
    }

    @Override
    public String toString() {
        return name + " - " + mealCategory + " | Carbs: " + carbs + "g, Fats: " + fats + "g, Proteins: " + proteins + "g, Calories: " + calories;
    }
}


