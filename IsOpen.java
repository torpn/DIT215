package dit215;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class IsOpen {
	
public static String isOpen(){
	String x = "";
	try {
		// 1. Get a connection to database
		Connection myConn = BarFinderIO.getConnection();
		
		// 2. Create a statement
		Date date = new Date();
		SimpleDateFormat dateFormatter = new SimpleDateFormat("HH:mm");
	    
		PreparedStatement stmt = myConn.prepareStatement("SELECT * FROM opening_hours_alldays INNER JOIN bars ON opening_hours_alldays.id=bars.id WHERE opening_hour > ?");
		stmt.setString(1, (dateFormatter.format(date)));
		ResultSet myRs = stmt.executeQuery();
		//
		
		while (myRs.next()){
			x+=(myRs.getString("id") + ", " + myRs.getString("name") + ", " + myRs.getString("opening_hour"));
			}
		}
		catch (Exception exc)	{
			exc.printStackTrace();
		}
	return x;
}
		public static void main(String[] args) {	
			System.out.println(isOpen());
	}
}