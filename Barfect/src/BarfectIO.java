
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.PrintWriter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import jcool.component.dynamiclist.Representable;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


import javax.imageio.ImageIO;




		

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
	public static ArrayList<Representable> getBarByName(String barName){
		ArrayList<Representable> bars = new ArrayList<>();
		try {
			Connection conn = null;
			String sql = "SELECT bars.id, name, street, city, postal_code, opening_hour, closing_hour, description, neighbourhood "
					+ "FROM opening_hours_alldays "
					+ "INNER JOIN address ON opening_hours_alldays.id = address.id "
					+ "INNER JOIN bars ON opening_hours_alldays.id = bars.id "
					+ "WHERE name LIKE ? OR street LIKE ?";
		
			conn = BarfectIO.getConnection();
			PreparedStatement prepedStmt = conn.prepareStatement(sql);
			prepedStmt.setString(1, "%"+barName+"%");
			prepedStmt.setString(2, "%"+barName+"%");
			ResultSet myRs = prepedStmt.executeQuery();
			
			while (myRs.next()) {									
				String name = myRs.getString("name");
				String address = myRs.getString("street");
				String city = myRs.getString("city");
				int id = myRs.getInt("bars.id");
				String postalCode = myRs.getString("postal_code");
				String description = myRs.getString("description");
				String openHours = myRs.getString("opening_hour");
				String closeHours = myRs.getString("closing_hour");
				String neighborhood = myRs.getString("neighbourhood");
				BufferedImage barPic = ImageIO.read(new File("Resources/pustervik.png"));
				
				BarCard newBar = new BarCard(id, name, address, postalCode, city, description, neighborhood, openHours, closeHours, barPic);
				newBar.getRepresentation().addMouseListener(new MouseListener(){

					@Override
					public void mouseClicked(MouseEvent arg0) {
						MainFrame.openBarCard(name, address, postalCode, city, description, neighborhood, openHours, closeHours);
						
					}

					@Override
					public void mouseEntered(MouseEvent arg0) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void mouseExited(MouseEvent arg0) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void mousePressed(MouseEvent arg0) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void mouseReleased(MouseEvent arg0) {
						// TODO Auto-generated method stub
						
					}
				
				});
				bars.add(newBar);
			}
			
		}	catch (Exception ex)	{
				ex.printStackTrace();
		}
		return bars;
	}
		
	//Get bars associated with an address
	//justinas stirbys
	public static Map<String, BarCard> getBarsByAddress(String streetName){
		Map<String, BarCard> mapOfBars = new HashMap<>();
		
		try {
			Connection conn = null;
			String sql = "SELECT bars.id, name, street, city, postal_code, opening_hour, closing_hour, description, neighbourhood "
					+ "FROM opening_hours_alldays "
					+ "INNER JOIN address ON opening_hours_alldays.id = address.id "
					+ "INNER JOIN bars ON opening_hours_alldays.id = bars.id "
					+ "WHERE street LIKE ?";	
		
			conn = BarfectIO.getConnection();
			PreparedStatement prepedStmt = conn.prepareStatement(sql);
			prepedStmt.setString(1, "%"+streetName+"%");
			ResultSet myRs = prepedStmt.executeQuery();
			while (myRs.next()) {									
				String name = myRs.getString("name");
				String address = myRs.getString("street");
				String city = myRs.getString("city");
				int id = myRs.getInt("bars.id");
				String postalCode = myRs.getString("postal_code");
				String description = myRs.getString("description");
				String openHours = myRs.getString("opening_hour");
				String closeHours = myRs.getString("closing_hour");
				String neighborhood = myRs.getString("neighbourhood");
				BufferedImage barPic = ImageIO.read(new File("Resources/pustervik.png"));
				
				BarCard newBar = new BarCard(id, name, address, postalCode, city, description, neighborhood, openHours, closeHours, barPic);
				newBar.getRepresentation().addMouseListener(new MouseListener(){

					@Override
					public void mouseClicked(MouseEvent arg0) {
						MainFrame.openBarCard(name, address, postalCode, city, description, neighborhood, openHours, closeHours);
						
					}

					@Override
					public void mouseEntered(MouseEvent arg0) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void mouseExited(MouseEvent arg0) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void mousePressed(MouseEvent arg0) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void mouseReleased(MouseEvent arg0) {
						// TODO Auto-generated method stub
						
					}
		
				});
				
				mapOfBars.put(name, newBar);
			}
				
		}	catch (Exception ex)	{
				ex.printStackTrace();
		}
		return mapOfBars;
	}

	//Get bars located at a certain part of the city
	//Justinas Stirbys
	public static Map<String, BarCard> getBarsByNeighbourhoods(String neighbourhoodName){
		Map<String, BarCard> mapOfBars = new HashMap<>();
		try {
			
			String sql = "SELECT bars.id, name, street, city, postal_code, opening_hour, closing_hour, description, neighbourhood "
				+ "FROM opening_hours_alldays "
				+ "INNER JOIN address ON opening_hours_alldays.id = address.id "
				+ "INNER JOIN bars ON opening_hours_alldays.id = bars.id "
				+ "WHERE neighbourhood LIKE ?";
			
			Connection conn = BarfectIO.getConnection();
			PreparedStatement prepedStmt = conn.prepareStatement(sql);
			prepedStmt.setString(1, "%"+ neighbourhoodName+"%");
			ResultSet myRs = prepedStmt.executeQuery();
			
			while (myRs.next()) {									
				String name = myRs.getString("name");
				String address = myRs.getString("street");
				String city = myRs.getString("city");
				int id = myRs.getInt("bars.id");
				String postalCode = myRs.getString("postal_code");
				String description = myRs.getString("description");
				String openHours = myRs.getString("opening_hour");
				String closeHours = myRs.getString("closing_hour");
				String neighborhood = myRs.getString("neighbourhood");
				BufferedImage barPic = ImageIO.read(new File("Resources/pustervik.png"));
				
				BarCard newBar = new BarCard(id, name, address, postalCode, city, description, neighborhood, openHours, closeHours, barPic);
				newBar.getRepresentation().addMouseListener(new MouseListener(){

					@Override
					public void mouseClicked(MouseEvent arg0) {
						MainFrame.openBarCard(name, address, postalCode, city, description, neighborhood, openHours, closeHours);
						
					}

					@Override
					public void mouseEntered(MouseEvent arg0) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void mouseExited(MouseEvent arg0) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void mousePressed(MouseEvent arg0) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void mouseReleased(MouseEvent arg0) {
						// TODO Auto-generated method stub
						
					}
				
				});
				mapOfBars.put(name, newBar);
			}
					
		}	catch (Exception ex)	{
				ex.printStackTrace();
		}
		
		return mapOfBars;
	}
	
	//Displays all the bar that are currently open
	//Erik Laurin
	public static Map<String, BarCard> isOpen(){
		Map<String, BarCard> mapOfBars = new HashMap<>();
		try {
			
				// 1. Get a connection to database
				Connection myConn = BarfectIO.getConnection();
			
				// 2. Create a statement
				Date date = new Date();
				SimpleDateFormat dateFormatter = new SimpleDateFormat("HH:mm");
		    
				PreparedStatement stmt = myConn.prepareStatement("SELECT bars.id, name, street, city, postal_code, opening_hour, closing_hour, description, neighbourhood "
						+ "FROM opening_hours_alldays "
						+ "INNER JOIN address ON opening_hours_alldays.id = address.id "
						+ "INNER JOIN bars ON opening_hours_alldays.id = bars.id "
						+ "WHERE ? BETWEEN opening_hour and closing_hour");
				
				stmt.setString(1, dateFormatter.format(date));
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
					String neighborhood = myRs.getString("neighbourhood");
					BufferedImage barPic = ImageIO.read(new File("Resources/pustervik.png"));
					
					BarCard newBar = new BarCard(id, name, address, postalCode, city, description, neighborhood, openHours, closeHours, barPic);
					newBar.getRepresentation().addMouseListener(new MouseListener(){

						@Override
						public void mouseClicked(MouseEvent arg0) {
							MainFrame.openBarCard(name, address, postalCode, city, description, neighborhood, openHours, closeHours);
							
						}

						@Override
						public void mouseEntered(MouseEvent arg0) {
							// TODO Auto-generated method stub
							
						}

						@Override
						public void mouseExited(MouseEvent arg0) {
							// TODO Auto-generated method stub
							
						}

						@Override
						public void mousePressed(MouseEvent arg0) {
							// TODO Auto-generated method stub
							
						}

						@Override
						public void mouseReleased(MouseEvent arg0) {
							// TODO Auto-generated method stub
							
						}
					
					});
					
					mapOfBars.put(name, newBar);
				}
		}
			
			catch (Exception ex)	{
				ex.printStackTrace();
			}
		return mapOfBars;
	}

	//Returns a LinkedList of all the bars in the DB in a random order
		//Erik Laurin
		public static ArrayList<Representable> ShuffledBarList(){
			ArrayList<Representable> bars = new ArrayList<>();
			try {
					Connection myConn = BarfectIO.getConnection();
						    
					PreparedStatement stmt = myConn.prepareStatement("SELECT bars.id, name, street, city, postal_code, opening_hour, closing_hour, description, neighbourhood "
							+ "FROM opening_hours_alldays "
							+ "INNER JOIN address ON opening_hours_alldays.id = address.id "
							+ "INNER JOIN bars ON opening_hours_alldays.id = bars.id");
					
					
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
						String neighborhood = myRs.getString("neighbourhood");
						BufferedImage barPic = ImageIO.read(new File("Resources/pustervik.png"));
						
						BarCard newBar = new BarCard(id, name, address, postalCode, city, description, neighborhood, openHours, closeHours, barPic);
						newBar.getRepresentation().addMouseListener(new MouseListener(){

							@Override
							public void mouseClicked(MouseEvent arg0) {
								MainFrame.openBarCard(name, address, postalCode, city, description, neighborhood, openHours, closeHours);
								
							}

							@Override
							public void mouseEntered(MouseEvent arg0) {
								// TODO Auto-generated method stub
								
							}

							@Override
							public void mouseExited(MouseEvent arg0) {
								// TODO Auto-generated method stub
								
							}

							@Override
							public void mousePressed(MouseEvent arg0) {
								// TODO Auto-generated method stub
								
							}

							@Override
							public void mouseReleased(MouseEvent arg0) {
								// TODO Auto-generated method stub
								
							}
						
						});
						
						bars.add(newBar);
					}
			}
				
			catch (Exception ex)	{
				ex.printStackTrace();
			}
		Collections.shuffle(bars);
			
		return bars;
		}
		
		public static ArrayList<Representable> Ordered(){
			ArrayList<Representable> bars = new ArrayList<>();
			try {
					Connection myConn = BarfectIO.getConnection();
						    
					PreparedStatement stmt = myConn.prepareStatement("SELECT bars.id, name, street, city, postal_code, opening_hour, closing_hour, description, neighbourhood "
							+ "FROM opening_hours_alldays "
							+ "INNER JOIN address ON opening_hours_alldays.id = address.id "
							+ "INNER JOIN bars ON opening_hours_alldays.id = bars.id");
					
					
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
						String neighborhood = myRs.getString("neighbourhood");
						BufferedImage barPic = ImageIO.read(new File("Resources/pustervik.png"));
						
						BarCard newBar = new BarCard(id, name, address, postalCode, city, description, neighborhood, openHours, closeHours, barPic);
						newBar.getRepresentation().addMouseListener(new MouseListener(){

							@Override
							public void mouseClicked(MouseEvent arg0) {
								MainFrame.openBarCard(name, address, postalCode, city, description, neighborhood, openHours, closeHours);
								
							}

							@Override
							public void mouseEntered(MouseEvent arg0) {
								// TODO Auto-generated method stub
								
							}

							@Override
							public void mouseExited(MouseEvent arg0) {
								// TODO Auto-generated method stub
								
							}

							@Override
							public void mousePressed(MouseEvent arg0) {
								// TODO Auto-generated method stub
								
							}

							@Override
							public void mouseReleased(MouseEvent arg0) {
								// TODO Auto-generated method stub
								
							}
						
						});
						
						bars.add(newBar);
					}
			}
				
			catch (Exception ex)	{
				ex.printStackTrace();
			}
		//SortedSet<String> keys = new TreeSet<String>(mapOfBars.keySet());
			
		return bars;
		}
}	