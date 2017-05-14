package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.util.converter.IntegerStringConverter;
import java.sql.*;


public class Controller {
    @FXML
    Button button,employInButton, empDeleteButton, projShowBut, projIncButton,projDelButton, depShowBut,depDelButton,depInsertButton,showProfitButton;
    @FXML
    ChoiceBox empCoiceBox, projDepCB;
    @FXML
    TextField empNameIn,empLastIn,empPatherIn,empSalaryIn,empPositionIn, projNameInc,projCostInc,projBegInc,projEndInc, depNameIns;
    //для сотрудников
    ObservableList<Employ> employesUsersData = FXCollections.observableArrayList();
    ObservableList<String> depEmployChoiceBox = FXCollections.observableArrayList();
    @FXML
    TableView<Employ> tableView;
    @FXML
    TableColumn<Employ,Integer> id, salary;
    @FXML
    TableColumn<Employ,String> first_name,pather_name,last_name,position;
    //Для проектов
    ObservableList<String> depChoiceBox = FXCollections.observableArrayList();
    ObservableList<Project> projectsUsersData = FXCollections.observableArrayList();
    @FXML
    TableView<Project> projTableView;
    @FXML
    TableColumn<Project, Integer> projID, projCOST, projDEP;
    @FXML
    TableColumn<Project, String> projNAME, projDATEBEG, projDATEEND, projDATEREAL;
    //ДЛЯ ОТДЕЛОВ
    ObservableList<Department> departmentsUserData = FXCollections.observableArrayList();
    @FXML
    TableView<Department> depTableView;
    @FXML
    TableColumn<Department,Integer> depIdCol;
    @FXML
    TableColumn<Department,String> depNameCol;
    //для статистики
    ObservableList<Profit> statisticUserData = FXCollections.observableArrayList();
    @FXML
    TableView<Profit> statTableViev;
    @FXML
    TableColumn<Profit,String> statNameCol;
    @FXML
    TableColumn<Profit,Integer> statProfitCol;

    @FXML
    public void initialize() {
        //ДЛЯ СОТРУДНИКОВ
        tableView.setEditable(true);
        id.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
        first_name.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        pather_name.setCellValueFactory(new PropertyValueFactory<>("patherName"));
        last_name.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        position.setCellValueFactory(new PropertyValueFactory<>("position"));
        salary.setCellValueFactory(new PropertyValueFactory<>("salary"));

        salary.setCellFactory( TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        salary.setOnEditCommit(event -> {
            Employ employ = event.getRowValue();
            employ.setSalary(event.getNewValue());
            updateEmployees("salary", String.valueOf(event.getNewValue()), employ.getEmployId());
        });
        position.setCellFactory(TextFieldTableCell.forTableColumn());
        position.setOnEditCommit(event -> {
            Employ employ = event.getRowValue();
            employ.setPosition(event.getNewValue());
            updateEmployees("position", event.getNewValue(), employ.getEmployId());
        });

        button.addEventHandler(MouseEvent.MOUSE_CLICKED, mousEvent -> {
            employesUsersData.removeAll();
            for ( int i = 0; i<tableView.getItems().size(); i++) {
                tableView.getItems().clear();
            }
            depEmployChoiceBox.removeAll();
            empCoiceBox.getItems().clear();
            selectEmployeesFromDB();
            tableView.setItems(employesUsersData);
            empCoiceBox.setItems(depEmployChoiceBox);
        });
        employInButton.addEventHandler(MouseEvent.MOUSE_CLICKED,mouseEvent -> {
            String command = "{call insertEmployees (?, ?, ?, ?, ?, ?)}";
            DBConnector.callProcedure(command,empNameIn.getText(),empPatherIn.getText(),empLastIn.getText(),empPositionIn.getText(),Integer.valueOf(empSalaryIn.getText()),empCoiceBox.getSelectionModel().getSelectedItem().toString());
            empNameIn.clear();
            empPatherIn.clear();
            empLastIn.clear();
            empPositionIn.clear();
            empSalaryIn.clear();
        });
        empDeleteButton.addEventHandler(MouseEvent.MOUSE_CLICKED,mouseEvent -> {
            int id = tableView.getSelectionModel().getSelectedItem().getEmployId();
            StringBuilder sb = new StringBuilder("DELETE FROM DEPARTMENTS_EMPLOYEES WHERE EMPLOYEE_ID = "+id);
            DBConnector.updateInsertCommand(sb.toString());
            StringBuilder sb2 = new StringBuilder("DELETE FROM EMPLOYEES WHERE ID ="+id);
            DBConnector.updateInsertCommand(sb2.toString());
        });
        //ДЛЯ ПРОЕКТОВ
        projTableView.setEditable(true);
        projID.setCellValueFactory(new PropertyValueFactory<>("projectID"));
        projNAME.setCellValueFactory(new PropertyValueFactory<>("name"));
        projCOST.setCellValueFactory(new PropertyValueFactory<>("cost"));
        projDEP.setCellValueFactory(new PropertyValueFactory<>("department_id"));
        projDATEBEG.setCellValueFactory(new PropertyValueFactory<>("date_beg"));
        projDATEEND.setCellValueFactory(new PropertyValueFactory<>("date_end"));
        projDATEREAL.setCellValueFactory(new PropertyValueFactory<>("date_end_real"));

        projDATEREAL.setCellFactory(TextFieldTableCell.forTableColumn());
        projDATEREAL.setOnEditCommit(event -> {
            Project project = event.getRowValue();
            project.setDateReal(event.getNewValue());
            updateProject("date_end_real", event.getNewValue(), project.getProjectId());
        });

        projShowBut.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            projectsUsersData.removeAll();
            for ( int i = 0; i<projTableView.getItems().size(); i++) {
                projTableView.getItems().clear();
            }
            projDepCB.getItems().clear();
            selectProjectsFromDB();
            projTableView.setItems(projectsUsersData);
            projDepCB.setItems(depEmployChoiceBox);

        });
        projIncButton.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            String command = "{call insertProject (?,?,?,?,?)}";
            CallableStatement cstmt=null;
            try {
                DBConnector.dbConnect();
                cstmt=DBConnector.conn.prepareCall(command);
                cstmt.setString("PNAME",projNameInc.getText());
                cstmt.setInt("PCOST", Integer.parseInt(projCostInc.getText()));
                cstmt.setString("DEPNAME",projDepCB.getSelectionModel().getSelectedItem().toString());
                cstmt.setDate("DATEBEG",java.sql.Date.valueOf(projBegInc.getText()));
                cstmt.setDate("DATEEND", java.sql.Date.valueOf(projEndInc.getText()));
                cstmt.execute();
            } catch (Exception e) {
                e.printStackTrace();
            }
            projNameInc.clear();
            projBegInc.clear();
            projEndInc.clear();
            projCostInc.clear();
        });
        projDelButton.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent ->{
            int id = projTableView.getSelectionModel().getSelectedItem().getProjectId();
            StringBuilder sb = new StringBuilder("DELETE FROM PROJECTS WHERE ID ="+id);
            DBConnector.updateInsertCommand(sb.toString());
        });

        //ДЛЯ ОТДЕЛОВ
        depTableView.setEditable(true);
        depIdCol.setCellValueFactory(new PropertyValueFactory<>("departmentID"));
        depNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        depShowBut.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent ->{
            departmentsUserData.removeAll();
            for ( int i = 0; i<depTableView.getItems().size(); i++) {
                depTableView.getItems().clear();
            }
            selectDepartmentFromDB();
            depTableView.setItems(departmentsUserData);
        });
        depDelButton.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent ->{
            int id = depTableView.getSelectionModel().getSelectedItem().getDepartmentID();
            StringBuilder sb = new StringBuilder("DELETE FROM DEPARTMENTS WHERE ID ="+id);
            DBConnector.updateInsertCommand(sb.toString());
        });
        depInsertButton.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent ->{
            StringBuilder command =  new StringBuilder("INSERT INTO departments (departments.NAME) VALUES ('"+depNameIns.getText()+"')");
            Statement cstmt=null;
            try {
                DBConnector.dbConnect();
                cstmt=DBConnector.conn.createStatement();
                cstmt.executeQuery(command.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
            projNameInc.clear();
            projBegInc.clear();
            projEndInc.clear();
            projCostInc.clear();
        });
        //ДЛЯ СТАТИСТИКИ
        statTableViev.setEditable(true);
        statNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        statProfitCol.setCellValueFactory(new PropertyValueFactory<>("profit"));

        showProfitButton.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent ->{
            statisticUserData.removeAll();
            for ( int i = 0; i<statTableViev.getItems().size(); i++) {
                statTableViev.getItems().clear();
            }
            selectStatisticFromDB();
            statTableViev.setItems(statisticUserData);
        });
    }

    void selectDepartmentFromDB() {
        try {
            ResultSet resultSet = DBConnector.dbExecuteQuery("SELECT * FROM DEPARTMENTS");
            while (resultSet.next()){
                departmentsUserData.add(new Department(resultSet.getInt("ID"),resultSet.getString("NAME")));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    void updateEmployees(String collum,String newValue ,int id){
        System.out.printf("UPDATE EMPLOYEES SET "+collum+" = "+newValue+" WHERE id="+id);
        StringBuilder stringBuilder =  new StringBuilder("UPDATE EMPLOYEES SET "+collum+ " = '"+newValue+"' WHERE ID = "+id);
        DBConnector.updateInsertCommand(stringBuilder.toString());
    }
    void updateProject(String collum, String newValue, int id){

        PreparedStatement pstmt = null;
        try {
            pstmt = DBConnector.conn.prepareStatement(
                    "UPDATE PROJECTS SET "+collum+" = ? WHERE ID = "+id);
            pstmt.setDate(1,java.sql.Date.valueOf(newValue));
            pstmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    void selectEmployeesFromDB(){
        try {
            ResultSet resultSet =  DBConnector.dbExecuteQuery("SELECT * FROM EMPLOYEES");
            while (resultSet.next()) {
                employesUsersData.add(new Employ(resultSet.getInt("ID"),resultSet.getString("FIRST_NAME"),
                                resultSet.getString("PATHER_NAME"),resultSet.getString("LAST_NAME"),
                                resultSet.getString("POSITION"),resultSet.getInt("SALARY")));
            }
            ResultSet resultSet1 =  DBConnector.dbExecuteQuery("SELECT name FROM DEPARTMENTS");
            while (resultSet1.next()) {
                depEmployChoiceBox.add(resultSet1.getString("NAME"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    void selectProjectsFromDB(){
        try {
            ResultSet resultSet = DBConnector.dbExecuteQuery("SELECT * FROM PROJECTS");
            while (resultSet.next()){

                projectsUsersData.add(new Project(resultSet.getInt("ID"),
                        resultSet.getString("NAME"),
                        resultSet.getInt("COST"),
                        resultSet.getInt("DEPARTMENT_ID"),
                        resultSet.getDate("DATE_BEG")==null ? "null":resultSet.getDate("DATE_BEG").toString(),
                        resultSet.getDate("DATE_END")==null ?"null":resultSet.getDate("DATE_END").toString(),
                        resultSet.getDate("DATE_END_REAL")==null ?"null":resultSet.getDate("DATE_END_REAL").toString()));
            }
            ResultSet resultSet1 =  DBConnector.dbExecuteQuery("SELECT name FROM DEPARTMENTS");
            while (resultSet1.next()) {
                depEmployChoiceBox.add(resultSet1.getString("NAME"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    void selectStatisticFromDB(){
        try{
            ResultSet resultSet = DBConnector.dbExecuteQuery("SELECT NAME, COST-EXPENSES profit " +
                    "FROM(SELECT  PROJECTS.NAME,PROJECTS.COST,sum((EMPLOYEES.SALARY)*MONTHS_BETWEEN(PROJECTS.DATE_END,PROJECTS.DATE_BEG)) expenses from PROJECTS, DEPARTMENTS,DEPARTMENTS_EMPLOYEES,EMPLOYEES " +
                    "WHERE PROJECTS.DEPARTMENT_ID=DEPARTMENTS.ID AND DEPARTMENTS_EMPLOYEES.DEPARTMENT_ID=DEPARTMENTS.ID AND DEPARTMENTS_EMPLOYEES.EMPLOYEE_ID = EMPLOYEES.ID " +
                    "AND PROJECTS.ID IN (SELECT PROJECTS.ID FROM PROJECTS WHERE DATE_END_REAL is not null AND DATE_BEG<SYSDATE) group by PROJECTS.NAME, PROJECTS.COST)");

            while (resultSet.next()){
                statisticUserData.add(new Profit(resultSet.getString("NAME"),resultSet.getInt("profit")));
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
