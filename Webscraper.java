package dit215;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import static dit215.ExecuteSQL.*;
import static dit215.BarFinderIO.*;

public class Webscraper {
	public static void main(String[] args) throws IOException{
		String active = "http://www.alktweb.goteborg.se/Default.aspx?flik=2"; 
		String filePath = "C:/Users/Fixzer/workspace/Barfect/src/dit215/allbars.html"; // Change this for testing.
		File ourHtml = new File("C:/Users/Fixzer/workspace/Barfect/src/dit215/allbars.html"); // change.
		
		int maxBars = 917;
		Bar[] bars = new Bar[maxBars];
		
		String username, password, serverAddress, dbName, query;
		
		for(int barNumber = 0; barNumber < maxBars; barNumber++){
			
			String nameQuery = "ctl00_DG1_Hyperlink2_";
			String addressQuery = "ctl00_DG1_Label4_";
			String postNumberQuery = "ctl00_DG1_Label5_";
			String cityQuery  = "ctl00_DG1_Label6_";
			bars[barNumber] = new Bar();
			bars[barNumber].setName(webScraper(active, barNumber, nameQuery));
			bars[barNumber].setAddress(webScraper(active, barNumber, addressQuery));
			bars[barNumber].setPostNumber(Integer.parseInt(webScraper(active, barNumber, postNumberQuery).replaceAll("\\s", "")));
			bars[barNumber].setCity(webScraper(active, barNumber, cityQuery));
			
			System.out.println(bars[barNumber].getName());
			System.out.println(bars[barNumber].getAddress());
			System.out.println(bars[barNumber].getPostNumber());
			System.out.println(bars[barNumber].getCity());
			
			
			
		}
		inputFromHtml(ourHtml);
		
		username = "team_02";
		password = "xWIgxDH7WM68kNM1";
		serverAddress = "leia.skip.chalmers.se";
		dbName = "team_02";
		
		int port = 3306;
		
		for(int barNumber = 0; barNumber < maxBars; barNumber++){
		/*	
		try {
			String addressQuery = "insert into address (id, street, city, postal_code) values ('"+barNumber+"', "+bars[barNumber].getAddress()+", '"+bars[barNumber].getCity()+"', '"+bars[barNumber].getPostNumber()+"');";
			//executeSQL(username, password, serverAddress, dbName, query, port);
		} catch (SQLException ex) {
			System.out.println("Error: ");
			System.out.println(ex.getMessage());
			System.out.println(ex.getSQLState());
			System.out.println(ex.getErrorCode());
		}
		*/
		}
	}



}