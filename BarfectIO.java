package dit215;

import java.awt.Component;
import java.awt.TextField;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.PrintWriter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import dit215.Bar;

		

		/*README
		 * This Java Class is for adding methods for the Barfect Project Team 02. 
		 * It uses the package dit215 so be sure to work your project in the right package.
		 * 
		 * Just ask me if you got any questions.s
		 * 
		 * So feel free to add methods but remember to add comment with what you have done and your name.
		 * This will be available in our private GitHub repository, if you don't have access send me an 
		 * message on SLACK and register your student email on Github. //Joacim Eberlen */

public class BarfectIO {
	
		
	//Gets number of lines in a file for Arrays or anywhere you want to know the number of lines.
	//Joacim Eberlen
	public static int nrOfLines(String file) throws IOException{
		
		LineNumberReader input = new LineNumberReader(new FileReader(file));
			
			
			input.skip(Integer.MAX_VALUE);
			
			int lineNumber = input.getLineNumber() + 1;
			
			return lineNumber;
	}
	//The outputs your active site to the the path your choose.
	// Joacim Eberlen
	
	//ADD A NEW WEBSCRAPERMETHOD ALL CONENT TO FILE.
	public static void outputToFile(String path, String activeSite, Bar[] bars){
		
			try {
				PrintWriter toFile = new PrintWriter(path);
				toFile.println(bars[1].getName());
				
			} catch (FileNotFoundException e) {
				
				e.printStackTrace();
			}
		
		
	}

	//Use this to get input from a HTML file, your need to create a new file.
	//Joacim Eberlen
	public static void inputFromHtml(File ourHtml) throws IOException{
		File dir = new File (".");
		
		FileInputStream input = new FileInputStream(ourHtml);
		BufferedReader read = new BufferedReader(new InputStreamReader(input));
		String readingLine = null;
		while((readingLine = read.readLine()) != null){
			System.out.println(readingLine);
		}
	}
	
	//This is the basic method for scraping an entire website, using an uri.
	//Joacim Eberlen
	public static String webScraper(String activeSite, int barNumber, String query) throws IOException{
		
		Document pageForScraping;
		pageForScraping = Jsoup.connect(activeSite).timeout(0).get();
		
		/*
		 Elements body=pageForScraping.select("div#middleContainer");
	       
	        for(Element step : body){
	            System.out.println("start for");
	            String method = step.select("div.CContainer iframe.http://www.alktweb.goteborg.se/").text();
	            System.out.println(method);
	            System.out.println("end for");
	        }
		*/
		Element contentByIdName = pageForScraping.getElementById(query + barNumber); // minimum 0. maximum 673
		String html = contentByIdName.text();
		
		return html;
		
	}
	
	//Joacim Eberlen
	public static String toFile(String activeSite, String query, int barNumber) throws IOException{
		
		Document activePage;
		activePage = Jsoup.connect(activeSite).maxBodySize(1000).get();
		Element con = activePage.body();
		String html = con.html();
		
		return html;
	}
	
	//making a connection to the DB
	//justinas stirbys
	public static Connection getConnection(){
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		}	catch(Exception ex){
			
		}
		Connection conn = null;
		String uri = "jdbc:mysql://leia.skip.chalmers.se:3306/team_02?useSSL=false";
		String username = "team_02";
		String password = "xWIgxDH7WM68kNM1";
	
		try {
			conn = DriverManager.getConnection(uri,	username, password);

		}	catch (SQLException ex)	{
			System.out.println("Error: ");
			System.out.println(ex.getMessage());
			System.out.println(ex.getSQLState());
			System.out.println(ex.getErrorCode());
		}
		
	return conn;
	}
	
	//Get bar name from DB
	//justinas stirbys
	public static LinkedList<Bar> getBarNames(String barName){
		Connection conn = null;
		String sqlBarName = "SELECT * FROM bars WHERE name LIKE ?";
		LinkedList<Bar> barList = new LinkedList<Bar>();
		try {
			conn = getConnection();
			PreparedStatement prepedStmt = conn.prepareStatement(sqlBarName);
			prepedStmt.setString(1, "%"+barName+"%");
			ResultSet rs = prepedStmt.executeQuery();
			
			while (rs.next()) {									
				String name = rs.getString("name");
				int id = rs.getInt("id");
				
				barList.add(new Bar(name, id));
				
			}
			
		}	catch (SQLException ex)	{
				ex.printStackTrace();
				
		}	finally{
			try {
				conn.close();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		
		}
		
		return barList;
	}
		
	//Get bars associated with an address
	//justinas stirbys
	public static LinkedList<Bar> getBarsByAddress(String streetName){
		Connection conn = null;
		String sqlBarAddress = "SELECT bars.id, name, street, city FROM bars INNER JOIN address ON bars.id = address.id WHERE street LIKE ?";	
		LinkedList<Bar> barList = new LinkedList<Bar>();
		try {
			conn = getConnection();
			PreparedStatement prepedStmt = conn.prepareStatement(sqlBarAddress);
			prepedStmt.setString(1, "%"+streetName+"%");
			ResultSet rs = prepedStmt.executeQuery();
			while (rs.next()) {									
				int id = rs.getInt("id");
				String street = rs.getString("street");
				String city = rs.getString("city");
				String name = rs.getString("name");
				
				barList.add(new Bar(name, street, city, id));
			}
				
		}	catch (SQLException ex)	{
				ex.printStackTrace();
		
		}	finally {
			try {
				conn.close();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
		return barList;
	}
	
	//Use bar name to get working hours
	//justinas stirbys
	public static LinkedList<Bar> getBarOpenHoursByName(String barName){
		Connection conn = null;
		String sqlOpenHours = "SELECT bars.id, name, street, city, opening_hour, closing_hour FROM bars INNER JOIN opening_hours_alldays ON bars.id = opening_hours_alldays.id WHERE name LIKE ?";
		LinkedList<Bar> barList = new LinkedList<Bar>();
		try {
			conn = getConnection();
			PreparedStatement prepedStmt = conn.prepareStatement(sqlOpenHours);
			prepedStmt.setString(1, "%"+barName+"%");
			ResultSet rs = prepedStmt.executeQuery();
			while (rs.next()) {									
				String name = rs.getString("name");
				String street = rs.getString("street");
				String city = rs.getString("city");
				int id = rs.getInt("id");
				String openHours = rs.getString("opening_hour");
				String closeHours = rs.getString("closing_hour");
				
				barList.add(new Bar(name, street, city, id, openHours, closeHours));
			}
				
		}	catch (SQLException ex)	{
				ex.printStackTrace();
		
		}	finally {
			try {
				conn.close();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
		return barList;
	}

	//Get bars located at a certain part of the city
	//Justinas Stirbys
	public static LinkedList<Bar> getBarsByCity(String cityPart){
		Connection conn = null;
		String sqlCityPart = "SELECT bars.id, name, street, city FROM bars INNER JOIN address ON bars.id = address.id WHERE city LIKE ?";	
		LinkedList<Bar> barList = new LinkedList<Bar>();
		try {
			conn = getConnection();
			PreparedStatement prepedStmt = conn.prepareStatement(sqlCityPart);
			prepedStmt.setString(1, "%"+cityPart+"%");
			ResultSet rs = prepedStmt.executeQuery();
			while (rs.next()) {									
				int id = rs.getInt("id");
				String street = rs.getString("street");
				String city = rs.getString("city");
				String name = rs.getString("name");
				
				barList.add(new Bar(name, street, city, id));
			}
					
		}	catch (SQLException ex)	{
				ex.printStackTrace();
		
		}	finally {
			try {
				conn.close();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
		return barList;
	}
	
	//Returns a LinkedList of bar objects that are currently in real time
	//Erik Laurin
	public static LinkedList<Bar> isOpen(){
		LinkedList<Bar> barList = new LinkedList<Bar>();
		try {
				Connection myConn = BarfectIO.getConnection();
			
				Date date = new Date();
				SimpleDateFormat dateFormatter = new SimpleDateFormat("HH:mm");
		    
				PreparedStatement stmt = myConn.prepareStatement("SELECT bars.id, name, street, city, postal_code, opening_hour, closing_hour, description FROM opening_hours_alldays INNER JOIN address ON opening_hours_alldays.id = address.id INNER JOIN bars ON opening_hours_alldays.id = bars.id WHERE opening_hour < ?");
				stmt.setString(1, (dateFormatter.format(date)));
				ResultSet myRs = stmt.executeQuery();
			
				while (myRs.next()){
					String name = myRs.getString("name");
					String address = myRs.getString("street");
					String city = myRs.getString("city");
					int id = myRs.getInt("bars.id");
					String postalCode = myRs.getString("postal_code");
					String description = myRs.getString("description");
					String openHours = myRs.getString("opening_hour");
					String closeHours = myRs.getString("closing_hour");
				
					barList.add(new Bar(name, address, city, id, postalCode, description, openHours, closeHours));
				}
			}
			catch (Exception ex)	{
				ex.printStackTrace();
			}
		return barList;
	}
	
	//Returns a LinkedList of all the bars in the DB in a random order
	//Erik Laurin
	public static LinkedList<Bar> ShuffledBarList(){
		LinkedList<Bar> barList = new LinkedList<Bar>();
		try {
				Connection myConn = BarfectIO.getConnection();
					    
				PreparedStatement stmt = myConn.prepareStatement("SELECT bars.id, name, street, city, postal_code, opening_hour, closing_hour, description FROM opening_hours_alldays INNER JOIN address ON opening_hours_alldays.id = address.id INNER JOIN bars ON opening_hours_alldays.id = bars.id");
				ResultSet myRs = stmt.executeQuery();
			
				while (myRs.next()){
					String name = myRs.getString("name");
					String address = myRs.getString("street");
					String city = myRs.getString("city");
					int id = myRs.getInt("bars.id");
					String postalCode = myRs.getString("postal_code");
					String description = myRs.getString("description");
					String openHours = myRs.getString("opening_hour");
					String closeHours = myRs.getString("closing_hour");
				
					barList.add(new Bar(name, address, city, id, postalCode, description, openHours, closeHours));
				}
			}
			catch (Exception ex)	{
				ex.printStackTrace();
			}
		
		Collections.shuffle(barList);
		
		return barList;
	}
	
	public static LinkedList<Bar> getBarsByNeighbourhoodId(String neighbourhood_id){
		Connection conn = null;
		String sqlCityPart = "SELECT * FROM address WHERE neighbourhood_id = ?";	
		LinkedList<Bar> barList = new LinkedList<Bar>();
		try {
			conn = getConnection();
			PreparedStatement prepedStmt = conn.prepareStatement(sqlCityPart);
			prepedStmt.setString(1, neighbourhood_id);
			ResultSet rs = prepedStmt.executeQuery();
			while (rs.next()) {									
				int id = rs.getInt("id");
				String street = rs.getString("street");
				String city = rs.getString("city");
				String name = rs.getString("name");
				
				barList.add(new Bar(name, street, city, id));
				System.out.println(barList);
			}
					
		}	catch (SQLException ex)	{
				ex.printStackTrace();
		
		}	finally {
			try {
				conn.close();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
		
		return barList;
	}
	
}