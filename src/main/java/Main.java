import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        DriverManager.getConnection("jdbc:postgresql://localhost:5432/test2", "postgres", "postgres");
    }
}
