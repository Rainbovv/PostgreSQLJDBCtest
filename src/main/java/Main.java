import java.sql.ResultSet;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) {

        DBConnector connector = DBConnector.getInstance();

        connector.createTable("test");

        connector.addColumn("test", "id","serial", "primary key");

        connector.addColumn("test", "name", "text");

        connector.insertRecord("test" , "name","'Eugen'");
        connector.insertRecord("test" , "name","'Vasile'");

        connector.updateRecord("test", "name", "'Ion'", "id=2");

        connector.deleteRecord("test","id=1");

        ResultSet resultSet = connector.selectAll("test");

        try {
            while (resultSet.next()) {
                String record = "";

                record += resultSet.getInt("id") + ". ";
                record += resultSet.getString("name");
                System.out.println(record);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        connector.dropColumn("test", "name");

        connector.dropTable("test");
    }
}