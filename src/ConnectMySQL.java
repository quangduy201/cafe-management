import java.sql.*;
import java.util.Arrays;

public class ConnectMySQL {
    public static void main(String[] args) {
        var url = "jdbc:mysql://localhost:3306/qlcafe";
        var user = "root";
        var password = "";
        try (Connection conn = DriverManager.getConnection(url,user,password)) {
            System.out.println(conn.getCatalog());
        }catch (SQLException throwables){
            throwables.printStackTrace();
        }
    }
}
