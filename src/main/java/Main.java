import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
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
//            insertDataAboutLocation(connection);
//            showLocationList(connection);
            insertEvent(connection);
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
        String insertSql = "INSERT INTO locations VALUES (null, ?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(insertSql);
        Scanner scanner = new Scanner(System.in);

        System.out.println("Please fill in the city");
        String city = scanner.nextLine();
        System.out.println("Whats the address?");
        String address = scanner.nextLine();
        System.out.println("What is the name of the location?");
        String name = scanner.nextLine();

        preparedStatement.setString(1, city);
        preparedStatement.setString(2, address);
        preparedStatement.setString(3, name);

        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    private static void insertEvent(Connection connection) throws SQLException {

        Scanner scanner = new Scanner(System.in);

        System.out.print("Type event name: ");
        String eventName = scanner.nextLine();
        System.out.print("Type start date: ");
        String startDate = scanner.nextLine();
        System.out.print("Type end date: ");
        String endDate = scanner.nextLine();

        System.out.println();

        showLocationList(connection);

        System.out.println();
        System.out.print("Pick one location: ");
        int locationId = scanner.nextInt();

        String sqlQuery = "INSERT INTO events VALUES (NULL, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);

        preparedStatement.setString(1, eventName);
        preparedStatement.setString(2, startDate);
        preparedStatement.setString(3, endDate);
        preparedStatement.setInt(4, locationId);

        preparedStatement.executeUpdate();
        preparedStatement.close();

    }

}
