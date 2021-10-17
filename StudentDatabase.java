/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package studentdatabase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author user
 */
public class StudentDatabase {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        
        /*String derbyEmbeddedDriver = "org.apache.derby.jdbc.EmbeddedDriver";
        String msAccessDriver = "sun.jdbc.odbc.JdbcOdbcDriver";
        String msSQlDriver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
        String oracleDriver = "oracle.jdbc.driver.OracleDriver";*/

        String derbyClientDriver = "org.apache.derby.jdbc.ClientDriver";
        //String mySqlDriver = "com.mysql.cj.jdbc.Driver";
        //load driver
        Class.forName(derbyClientDriver);
        //Class.forName(mySqlDriver);
        //create connection
        /*
         * String url="jdbc:mysql://server[:port]/databaseName"; //for mySQL
         * String url="jdbc:derby:databaseName"; //for DerbyEmbedded
         * String url= "jdbc:odbc:Driver=:datasourceNameOfODBC" //for MS Accces
         * String url= "jdbc:sqlserver://server[:port]:database="databaseName" //for MS SQL Server 
         * String url= "jdbc:oracle:thin:@server:port:databaseName" //for Oracle
         */
        String url = "jdbc:derby://localhost:1527/Student";
        //String url="jdbc:mysql://localhost:3306/Student?serverTimezone=UTC";
        String user = "app";
        //String user = "root";
        String passwd = "app";
        //String passwd = "root";

        Connection con = DriverManager.getConnection(url, user, passwd);
        //create statement
       Statement stmt = con.createStatement();
       Student emp1 = new Student(1, "John", 4.0);
       Student emp2 = new Student(2, "Marry", 3.0);
       insertStudent(stmt, emp1);
       insertStudent(stmt, emp2);

        stmt.close();
        con.close();
    }
    public static void printAllStudent(ArrayList<Student> StudentList) {
        for(Student emp : StudentList) {
           System.out.print(emp.getId() + " ");
           System.out.print(emp.getName() + " ");
           System.out.println(emp.getGpa() + " ");
       }
    }
    
    public static ArrayList<Student> getAllStudent (Connection con) throws SQLException {
        String sql = "select * from Student order by id";
        PreparedStatement ps = con.prepareStatement(sql);
        ArrayList<Student> StudentList = new ArrayList<>();
        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
           Student Student = new Student();
           Student.setId(rs.getInt("id"));
           Student.setName(rs.getString("name"));
           Student.setGpa(rs.getDouble("Gpa"));
           StudentList.add(Student);
       }
       rs.close();
       return StudentList;
       
    }
    
   public static Student getStudentById(Statement stmt, int id) throws SQLException {
       Student emp = null;
       String sql = "select * from Student where id = " + id;
       ResultSet rs = stmt.executeQuery(sql);
       if (rs.next()) {
           emp = new Student();
           emp.setId(rs.getInt("id"));
           emp.setName(rs.getString("name"));
           emp.setGpa(rs.getDouble("Gpa"));
       }
       return emp;
   } 
   public static void insertStudent(Statement stmt, Student emp) throws SQLException {
       /*String sql = "insert into Student (id, name, Gpa)" +
                     " values (5, 'Mark', 12345)";*/
        String sql = "insert into Student (id, name, Gpa)" +
                     " values (" + emp.getId() + "," + "'" + emp.getName() + "'" + "," + emp.getGpa() + ")";
        int result = stmt.executeUpdate(sql);
        System.out.println("Insert " + result + " row");
   } 
   public static void deleteStudent(Statement stmt, Student emp) throws SQLException {
       String sql = "delete from Student where id = " + emp.getId();
       int result = stmt.executeUpdate(sql);
        //display result
        System.out.println("delete " + result + " row");
   }
   public static void updateStudentGpa(Statement stmt, Student emp) throws SQLException {
       String sql = "update Student set Gpa  = " + emp.getGpa() + 
               " where id = " + emp.getId();
       int result = stmt.executeUpdate(sql);
        //display result
        System.out.println("update " + result + " row");
   }
   public static void updateStudentName(Statement stmt, Student emp) throws SQLException {
       String sql = "update Student set name  = '" + emp.getName() + "'" + 
               " where id = " + emp.getId();
       int result = stmt.executeUpdate(sql);
        //display result
        System.out.println("update " + result + " row");
   }
   
   public static void insertStudentPreparedStatement(Connection con, Student emp) throws SQLException {
       String sql = "insert into Student (id, name, Gpa)" + 
               " values (?,?,?)";
       PreparedStatement ps = con.prepareStatement(sql);
       ps.setInt(1, emp.getId());
       ps.setString(2, emp.getName());
       ps.setDouble(3, emp.getGpa());
       int result = ps.executeUpdate();
        //display result
        System.out.println("Insert " + result + " row");
   }
   public static void deleteStudentPreparedStatement(Connection con, Student emp) throws SQLException {
       String sql ="delete from Student where id = ?";
       PreparedStatement ps = con.prepareStatement(sql);
       ps.setInt(1, emp.getId());
       int result = ps.executeUpdate();
        //display result
        System.out.println("Delete " + result + " row");
   }
   public static void updateStudentGpaPreparedStatement(Connection con, Student emp) throws SQLException {
       String sql = "update Student set Gpa  = ? where id = ? ";
       PreparedStatement ps = con.prepareStatement(sql);
       ps.setDouble(1, emp.getGpa());
       ps.setInt(2, emp.getId());
       int result = ps.executeUpdate();
        //display result
        System.out.println("update " + result + " row");
   }
   public static void updateStudentNamePreparedStatement(Connection con, Student emp) throws SQLException {
       String sql = "update Student set name  = ? where id = ? ";
       PreparedStatement ps = con.prepareStatement(sql);
       ps.setString(1, emp.getName());
       ps.setInt(2, emp.getId());
       int result = ps.executeUpdate();
        //display result
        System.out.println("update " + result + " row");
   }
   public static Student getStudentByIdPreparedStatement(Connection con, int id) throws SQLException {
       Student emp = null;
       String sql = "select * from Student where id = ?";
       PreparedStatement ps = con.prepareStatement(sql);
       ps.setInt(1, id);
       ResultSet rs = ps.executeQuery();
       if (rs.next()) {
           emp = new Student();
           emp.setId(rs.getInt("id"));
           emp.setName(rs.getString("name"));
           emp.setGpa(rs.getDouble("Gpa"));
       }
       return emp;
   }
}
