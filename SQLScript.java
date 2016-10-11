package dit215;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

public class SQLScript {

	public static void main(String[] args) {

		Connection SQLConnection = null;
		Statement SQLStatement = null;

		int result;
		String userName, password, host, dbName;

		userName = "root";
		password = "123123";
		host = "localhost";
		dbName = "bank";

		String query;
		query = "insert into BarName(aID, balance) values ("+aID+", "+ba+");"
		//query = "use " + dbName + ";";
		//query = "create table Account (name int(9) not null primary key, balance float);";
		// End of testing VARS.
		MysqlDataSource setup = new MysqlDataSource();
		setup.setUser(userName);
		setup.setPassword(password);
		setup.setServerName(host);
		setup.setDatabaseName(dbName);

		try {

			for(int barId = 0; barId < barMax; barId++){
				//insert query string here.
				SQLConnection = setup.getConnection();
				SQLStatement = SQLConnection.createStatement();
				setup.setDatabaseName(dbName);
				SQLStatement.executeUpdate(query);
				SQLConnection.close();
			}


		}catch(SQLException ex){

			System.out.println("Error: " + ex.getMessage());
			System.out.println("SQL State: " + ex.getSQLState());
			System.out.println("Error Number: " + ex.getErrorCode());
		}

	}

}
