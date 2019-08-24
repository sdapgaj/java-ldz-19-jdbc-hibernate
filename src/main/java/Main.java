import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/javaldz19?serverTimezone=Europe/Warsaw";
        String user = "root";
        String password = "";
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            insertDataAboutLocation(connection);
            showLocationList(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void showLocationList(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM locations");

        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String city = resultSet.getString("city");
            String address = resultSet.getString("address");
            String name = resultSet.getString("name");
            System.out.println(id + " - " + name + " - " + city + " - " + address);
        }

        resultSet.close();
        statement.close();
    }

    private static void insertDataAboutLocation(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Please fill in the city");
        String city = scanner.nextLine();
        System.out.println("Whats the address?");
        String address = scanner.nextLine();
        System.out.println("What is the name of the location?");
        String name = scanner.nextLine();
        String insert = String.format("INSERT INTO locations VALUES (null, '%s' , '%s' , '%s')"
                , city, address, name);

        statement.executeUpdate(insert);
        statement.close();
    }

}
