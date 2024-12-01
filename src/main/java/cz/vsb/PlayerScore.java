package cz.vsb;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class PlayerScore {
    private final StringProperty name;
    private final IntegerProperty wins;

    public PlayerScore(String name, int wins) {
        this.name = new SimpleStringProperty(name);
        this.wins = new SimpleIntegerProperty(wins);
    }

    public StringProperty nameProperty() {
        return name;
    }

    public IntegerProperty winsProperty() {
        return wins;
    }

    public String getName() {
        return name.get();
    }

    public int getWins() {
        return wins.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public void setWins(int wins) {
        this.wins.set(wins);
    }
}
