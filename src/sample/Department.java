package sample;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by ilya on 11.05.2017.
 */

public class Department {
    private IntegerProperty departmentID;
    private StringProperty name;

    public Department(int departmentID, String name) {
        this.departmentID = new SimpleIntegerProperty();
        this.name = new SimpleStringProperty();
        this.departmentID.set(departmentID);
        this.name.set(name);
    }
}
