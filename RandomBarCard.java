package dit215;

import java.sql.*;
import static dit215.Random.*;

public class RandomBarCard {
	
public static String randomBarCard(){
	String x = "";
	try {
		// 1. Get a connection to database
		Connection myConn = BarFinderIO.getConnection();
		
		// 2. Create a statement
		PreparedStatement stmt = myConn.prepareStatement("SELECT * FROM bars WHERE id = ?");
		stmt.setInt(1, randomInt(919));
		ResultSet myRs = stmt.executeQuery();
		
		while (myRs.next()){
			x = (myRs.getInt("id") + ", " + myRs.getString("name"));
			}
		
		}
		catch (Exception exc)	{
			exc.printStackTrace();
		}
	return x;
}
		//public static void main(String[] args) {
		//System.out.println(RandomBarCard());
//	}
}