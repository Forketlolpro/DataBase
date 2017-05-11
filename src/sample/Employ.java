package sample;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by ilya on 04.05.2017.
 */
public class Employ {
    private  SimpleStringProperty firstName;
    private  IntegerProperty employeeId;
    private  StringProperty lastName;
    private  StringProperty patherName;
    private  StringProperty position;
    private  IntegerProperty salary;

    Employ(int id, String first_name, String  pather_name,String last_name,String position,int salary) {
        this.employeeId = new SimpleIntegerProperty();
        this.firstName = new SimpleStringProperty();
        this.lastName = new SimpleStringProperty();
        this.patherName = new SimpleStringProperty();
        this.position = new SimpleStringProperty();
        this.salary = new SimpleIntegerProperty();

        this.employeeId.set(id);
        this.firstName.set(first_name);
        this.patherName.set(pather_name);
        this.lastName.set(last_name);
        this.position.set(position);
        this.salary.setValue(salary);
    }

    public int getEmployId(){return employeeId.get();};


    public IntegerProperty employeeIdProperty(){
        return employeeId;
    }
    public IntegerProperty salaryProperty(){
        return salary;
    }
    public StringProperty firstNameProperty(){return firstName;}
    public StringProperty patherNameProperty(){return patherName;}
    public StringProperty lastNameProperty(){return lastName;}
    public StringProperty positionProperty(){return position;}

    public void setPosition(String position){this.position.set(position);}
    public void setSalary (Integer salary){this.salary.set(salary);}
}
