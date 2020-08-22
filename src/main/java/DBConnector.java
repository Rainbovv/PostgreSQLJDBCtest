import java.sql.*;

public class DBConnector {
	
	private DBConnector() {}


	public ResultSet executeQuery(String query, boolean resultSetNeeded) {

		ResultSet resultSet = null;

		String URL = "jdbc:postgresql://localhost/ecommerce?user=postgres" +
				"&password=Rainbow@446571&ssl=false";
		
		try (Connection connection = DriverManager.getConnection(URL);
		     Statement statement = connection.createStatement()) {

			if (resultSetNeeded) resultSet = statement.executeQuery(query);

			else statement.executeUpdate(query);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultSet;

	}


	public void createTable(String name) {

		String query = "CREATE TABLE IF NOT EXISTS " + name + "()";

		executeQuery(query, false);
	}

	
	public void dropTable(String name) {

		String query = "DROP TABLE " + name;

		executeQuery(query, false);
	}

	public void addColumn(String tableName, String columnName, String type) {

		addColumn(tableName, columnName, type, "");
	}
	
	public void addColumn(String tableName, String columnName,
	                      String type, String constraints) {

		String query = String.format("ALTER TABLE %s ADD COLUMN %s %s %s",
				tableName, columnName, type, constraints);

		executeQuery(query, false);
	}

	
	public void dropColumn(String tableName, String columnName) {

		String query = String.format("ALTER TABLE %s DROP COLUMN %s",
				tableName, columnName);

		executeQuery(query,false);
	}

	
	public void insertRecord(String tableName, String columns, String values) {

		String query = String.format("INSERT INTO %s (%s) VALUES(%s)",
				tableName, columns, values);

		executeQuery(query, false);
	}

	
	public void updateRecord(String tableName, String columnToUpdate, String newValue) {

		updateRecord(tableName, columnToUpdate, newValue, null);
	}

	public void updateRecord(String tableName, String columnToUpdate,
	                         String newValue, String condition) {

		String query = String.format("UPDATE %s SET %s=%s",
				tableName, columnToUpdate, newValue);

		if (condition != null) query += "WHERE " + condition;

		executeQuery(query, false);
	}

	
	public void deleteRecord(String tableName, String condition) {

		String query = String.format("DELETE FROM %s WHERE %s", tableName, condition);

		executeQuery(query, false);
	}

	
	public ResultSet select(String tableName, String selectedColumns, String condition) {

		String query = String.format("SELECT %s FROM %s", selectedColumns, tableName);

		if (condition != null) query += "WHERE" + condition;

		return executeQuery(query, true);
	}


	public ResultSet selectAll(String tableName) {

		return select(tableName,"*",null);
	}


	public ResultSet selectWithCondition(String tableName, String condition) {

		return select(tableName, "*", condition);
	}


	public ResultSet selectColumns(String tableName, String columns) {

		return select(tableName, columns, null);
	}
	
	
	private static class SingletonHolder {
		private final static DBConnector INSTANCE = new DBConnector();
	}
	
	public static DBConnector getInstance() {
		return SingletonHolder.INSTANCE;
	}
}