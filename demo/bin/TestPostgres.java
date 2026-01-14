import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class TestPostgres {
    public static void main(String[] args) throws Exception {
        String url = "jdbc:postgresql://<HOST>:<PORT>/<DB>";
        String user = "<USER>";
        String password = "<PASSWORD>";

        Connection conn = DriverManager.getConnection(url, user, password);
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM users");

        while (rs.next()) {
            System.out.println(rs.getString("first_name") + " " + rs.getString("last_name"));
        }

        conn.close();
    }
}
