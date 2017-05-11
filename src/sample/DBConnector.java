package sample;

/**
 * Created by ilya on 04.05.2017.
 */
import java.sql.*;
import java.util.Locale;
import java.sql.*;
import java.util.Locale;


public class DBConnector {
    private static final String JDBC_DRIVER = "oracle.jdbc.driver.OracleDriver";
    public static Connection conn = null;
    private static final String connStr = "jdbc:oracle:thin:@localhost:1521:xe";

    public static void dbConnect() throws SQLException, ClassNotFoundException {
        Locale.setDefault(Locale.ENGLISH);
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            System.out.println("Where is your Oracle JDBC Driver?");
            e.printStackTrace();
            throw e;
        }
        System.out.println("Oracle JDBC Driver Registered!");
        try {
            conn = DriverManager.getConnection(connStr,"system","199115");
        } catch (SQLException e) {
            System.out.println("Connection Failed! Check output console" + e);
            e.printStackTrace();
            throw e;
        }
    }
    public static void dbDisconnect() throws SQLException {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (Exception e){
            throw e;
        }
    }

    public static ResultSet dbExecuteQuery(String queryStmt) throws SQLException, ClassNotFoundException {
        Statement stmt = null;
        ResultSet resultSet = null;
        try {
            dbConnect();
            System.out.println("Select statement: " + queryStmt + "\n");
            stmt = conn.createStatement();
            resultSet = stmt.executeQuery(queryStmt);
            return  resultSet;

        } catch (SQLException e) {
            System.out.println("Problem occurred at executeQuery operation : " + e);
            throw e;
        }
    }

    public static void callProcedure(String command, String fname, String pname, String lname, String pos, int salary, String depname){
        CallableStatement cstmt=null;
        try {
            dbConnect();
            cstmt=conn.prepareCall(command);
            cstmt.setString("FNAME",fname);
            cstmt.setString("PNAME",pname);
            cstmt.setString("LNAME",lname);
            cstmt.setString("POS",pos);
            cstmt.setInt("SAL",salary);
            cstmt.setString("DEPNAME",depname);
            cstmt.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void updateInsertCommand(String command){
        Statement stmt=null;
        try {
            dbConnect();
            System.out.printf("UPDATE/INSERT COMMAND IS:  " + command);
            stmt = conn.createStatement();
            stmt.executeQuery(command);
        } catch (Exception e) {
            System.err.println("Error");
            e.printStackTrace(System.err);
        }
    }

}

