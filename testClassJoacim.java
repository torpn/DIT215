package dit215;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class testClassJoacim extends JPanel{

	public static void main(String[] args) throws IOException {
		
		URL url = new URL("http://maps.googleapis.com/maps/api/geocode/json?majorna");
		String postal = url.toString();
		
		System.out.println(postal);
		
	}
	
	
	

}
