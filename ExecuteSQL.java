package dit215;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import java.sql.Connection;

public class ExecuteSQL {
		/*
		*	This method starts a connection to our SQL server(only local host at the moment),
		* and then executes the choosen query set to a String.
		* The username and password for these methods are for the SQL server.
		* TO USE THIS METHOD FOR MULTIPLE QUERIES YOU NEED TO PUT IT IN A LOOP IN YOUR MAIN CLASS.
		*/

public static void executeSQL(String userName, String password, String serverAddress, String dbName, String query, int port) throws SQLException{
		Connection SQLConnection = null;
		Statement SQLStatement = null;
		MysqlDataSource setup = new MysqlDataSource();
		// Inlog for the SQL server in question.
		setup.setUser(userName);
		setup.setPassword(password);
		setup.setServerName(serverAddress);
		setup.setDatabaseName(dbName);
		setup.setPort(port);
		try {
				SQLConnection = setup.getConnection();
				SQLStatement = SQLConnection.createStatement();
				SQLStatement.executeUpdate(query); // Our SQL query.
				SQLConnection.close();
		} catch (SQLException ex) {
			//This is where our errors get handled.
			
			System.out.println("Error: ");
			System.out.println(ex.getMessage());
			System.out.println(ex.getSQLState());
			System.out.println(ex.getErrorCode());
		}
}
	/*
	*This is just some finished SQL queries to use with our BANK system.
	*just for referal.
	*It returns a SQL query.
	*/
	public static String insertQuery(String tLog, int tLogNr,String dateLog, String opLog, int tAID){

	String query = "insert into Transactions (tLog, tLogNr, dateLog, opLog, tAID) values ('"+tLog+"', "+tLogNr+", '"+dateLog+"', '"+opLog+"', "+tAID+");";
	return query;
}
	/*
	*These methods are used once to Create the tables in the DB.
	*It returns a SQL query.
	*/
	public static String createTable(){
	String query = "create table Transactions (tLogNr int(9) not null primary key, tLog varchar(20), dateLog varchar(10), opLog varchar(20), tAID int(9), foreign key (tAID) references Account (aID));";
	return query;
}

}
