import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.image.BufferedImage;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import org.jdesktop.swingx.JXLabel;

import jcool.component.JCReflectedImage;
import jcool.component.dynamiclist.DefaultItem;

public class BarCard extends DefaultItem{
	
	private int barID;
	private String barName;
	private String address;
	private String zipcode;
	private String city;
	private String description;
	private String neighborhood;
	private String openingHours;
	private String closingHours;
	private BufferedImage barPic;
	public BufferedImage stars;
	private JLabel barNameLabel = new JLabel();
	private JXLabel addressLabel = new JXLabel();
	private JCReflectedImage reflectedBarPic;
	
	public BarCard(int barID, String barName, String address, String zipcode, String city, String description, String neighborhood, 
			String openingHours, String closingHours, BufferedImage barPic){
		
		this.barID = barID;
		this.barName = barName;
		this.address = address;
		this.zipcode = zipcode;
		this.city = city;
		this.description = description;
		this.neighborhood = neighborhood;
		this.openingHours = openingHours;
		this.closingHours = closingHours;
		this.barPic = barPic;
		
		setLayout(null);
		setSize(new Dimension(260, 120));
		setBackground(Color.DARK_GRAY);
		
		barNameLabel.setSize(125, 15);
		barNameLabel.setLocation(110, 17);
		barNameLabel.setFont(new Font("Avenir Next", Font.BOLD, 15));
		barNameLabel.setText(barName);
		barNameLabel.setForeground(Color.WHITE);
		
		addressLabel.setSize(125, 65);
		addressLabel.setLocation(110, 37);
		addressLabel.setVerticalAlignment(SwingConstants.TOP);
		addressLabel.setForeground(Color.WHITE);
		addressLabel.setLineWrap(true);
		addressLabel.setFont(new Font("Avenir", Font.PLAIN, 15));
		addressLabel.setText(address);
		
		reflectedBarPic = new JCReflectedImage(barPic);
		reflectedBarPic.setSize(100, 100);
		reflectedBarPic.setLocation(20, 10);
		add(reflectedBarPic);
		reflectedBarPic.setVisible(true);
		
		add(barNameLabel);
		add(addressLabel);
		
		setVisible(true);
		
	}
	
	public void setBarName(String barName){
		
		this.barName = barName;
		barNameLabel.setText(barName);
		
	}
	
	public void setAddress(String address, String zipcode, String city){
		
		this.address = address;
		this.zipcode = zipcode;
		this.city = city;
		addressLabel.setText(address + " " + zipcode + " " + city);
		
	}
	
	public void setBarPic(BufferedImage barPic){
		
		this.barPic = barPic;
		
	}
	
	public String getBarDescription(){
		
		return description;
		
	}

}
