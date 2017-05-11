package sample;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.sql.Date;

/**
 * Created by ilya on 10.05.2017.
 */
public class Project {
    private IntegerProperty projectID;
    private StringProperty name;
    private IntegerProperty cost;
    private IntegerProperty department_id;
    private StringProperty date_beg;
    private StringProperty date_end;
    private StringProperty date_end_real;

    public Project(int projectID, String name, int cost, int department_id, String date_beg, String date_end, String date_end_real) {
        this.projectID = new SimpleIntegerProperty();
        this.name = new SimpleStringProperty();
        this.cost = new SimpleIntegerProperty();
        this.department_id = new SimpleIntegerProperty();
        this.date_beg = new SimpleStringProperty();
        this.date_end = new SimpleStringProperty();
        this.date_end_real = new SimpleStringProperty();

        this.projectID.set(projectID);
        this.name.set(name);
        this.cost.set(cost);
        this.department_id.set(department_id);
        this.date_beg.set(date_beg);
        this.date_end.set(date_end);
        this.date_end_real.set(date_end_real);


    }

    public void setDateReal(String string){date_end_real.set(string);}

    public int getProjectId(){return projectID.get();}

    public IntegerProperty projectIDProperty(){
        return projectID;
    }
    public IntegerProperty costProperty(){
        return cost;
    }
    public StringProperty nameProperty(){return name;}
    public IntegerProperty department_idProperty(){
        return department_id;
    }
    public StringProperty date_begProperty(){return date_beg;}
    public StringProperty date_endProperty(){return date_end;}
    public StringProperty date_end_realProperty(){return date_end_real;}
}
