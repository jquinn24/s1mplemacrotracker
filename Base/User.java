package Base;
import java.io.Serializable;

public class User implements Serializable {
    String username;
    String password;
    MacroTracker tracker;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.tracker = new MacroTracker();
    }
}

