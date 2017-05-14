package sample;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by ilya on 14.05.2017.
 */
public class Profit {
    StringProperty name;
    IntegerProperty profit;

    public Profit(String name, int profit) {
        this.name = new SimpleStringProperty();
        this.profit = new SimpleIntegerProperty();
        this.name.set(name);
        this.profit.set(profit);
    }
    public IntegerProperty profitProperty(){return profit;}
    public StringProperty nameProperty(){return name;}
}
