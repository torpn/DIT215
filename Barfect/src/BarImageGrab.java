
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

//Unused currently
//it did not work - Shaun

public class BarImageGrab extends JPanel{
	
	private BufferedImage image;
	private String barName;
	private int height = 0;
	private int width = 0;
	
	public BarImageGrab() {
	}
	
	public BarImageGrab(int height, int width, String barName) throws IOException, MalformedURLException{
		this.barName = barName;
		this.height = height;
		this.width = width;
		try {
			URL url = new URL("http://ajax.googleapis.com/ajax/services/search/images?v=1.0&q=" + barName);
			URLConnection con = null;
			image = ImageIO.read(url);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
	}
	@Override
	protected void paintComponent(Graphics g) {
	    super.paintComponent(g);
	     g.drawImage(image, 0, 0, null);             
	}
	public void setHeight(int newHeight){
		this.height = newHeight;
	}
	public void setWidth(int newWidth){
		this.width = newWidth;
	}
	public void setBarName(String newBarNumber){
		this.barName = newBarNumber;
	}
	public int getHeight(){
		return this.height;
	}
	public int getWidth(){
		return this.width;
	}
	public String getBarNumber(){
		return this.barName;
	}
	public BufferedImage getImage(){
		return image;
	}
}