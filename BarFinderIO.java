package dit215;

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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

		/*README
		 * This Java Class is for adding methods for the Barfect Project Team 02. 
		 * It uses the package dit215 so be sure to work your project in the right package.
		 * 
		 * Just ask me if you got any questions.s
		 * 
		 * So feel free to add methods but remember to add comment with what you have done and your name.
		 * This will be available in our private GitHub repository, if you don't have access send me an 
		 * message on SLACK and register your student email on Github. //Joacim Eberlen */

public class BarFinderIO {

	
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
	//This is the basic method for scraping an entire website, using an URL.
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
	public static String toFile(String activeSite, String query, int barNumber) throws IOException{
		
		Document activePage;
		activePage = Jsoup.connect(activeSite).maxBodySize(1000).get();
		Element con = activePage.body();
		String html = con.html();
		
		return html;
	}

    public static Connection getConnection(){
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        }    catch(Exception ex){
            
        }
        Connection conn = null;
        String uri = "jdbc:mysql://leia.skip.chalmers.se:3306/team_02?useSSL=false";
        String username = "team_02";
        String password = "xWIgxDH7WM68kNM1";
    
        try {
            conn = DriverManager.getConnection(uri,    username, password);
            return conn;

        }    catch (SQLException ex)    {
            System.out.println("Error: ");
            System.out.println(ex.getMessage());
            System.out.println(ex.getSQLState());
            System.out.println(ex.getErrorCode());
        }
        
    return conn;
    }
}