import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.jdesktop.swingx.JXLabel;
import jcool.component.JCReflectedImage;
import jcool.component.JCSearchField;
import jcool.component.dynamiclist.JCDynamicList;
import jcool.component.dynamiclist.Representable;
import net.miginfocom.swing.MigLayout;

/**
 * 
 * @author shaunmcmurray
 *
 */

public class MainFrame extends JFrame {
	
	private JPanel barPanel;
	private JPanel searchPanel;
	private JPanel textSearchPanel;
	private JPanel logoPanel;
	private JCDynamicList barCardList;
	
	
	public MainFrame() throws IOException, SQLException{
		
		//Here we initialize our main list
		//JCDynamicList is a handy dandy list that will help with animations
		//I am currently rewriting the JCDynamicList class as well as the rest of the JAR 
		//ti make a more stabile overall program. We will use the JAR for the time being though
		//the jar is very outdated :( @shaunmcmurray
		
		ArrayList<Representable> bar = new ArrayList<>(BarfectIO.ShuffledBarList());
		
		JCDynamicList bars = new JCDynamicList(20, bar, new Dimension(260, 120));
		
		//Basic SWING STUFF here! If you dont understand this part just google swing this is basic stuff
		
		JPanel barPanel = new JPanel();
		barPanel.setLayout(new BorderLayout());
		barPanel.setBorder(null);
		barPanel.setBackground(Color.GRAY);
		barPanel.add(bars, BorderLayout.CENTER);
		barCardList = bars;
		barPanel.add(barCardList);
		getContentPane().add(barPanel, BorderLayout.CENTER);
	
		JPanel searchPanel = new JPanel();
		searchPanel.setPreferredSize(new Dimension(400, 495));
		searchPanel.setLayout(new GridBagLayout());
		//GBL constrains
		GridBagConstraints tags = new GridBagConstraints();
		//Initial button size and spaces between them
		tags.insets = new Insets(2, 2, 2, 2);
		tags.fill = GridBagConstraints.BOTH;
		tags.weightx = 1;
		tags.weighty = 1;
		getContentPane().add(searchPanel, BorderLayout.LINE_START);
		
		JLabel logoLabel = new JLabel();
		//BufferedImage logo = getClass().getResource("Resources/barfectlogo.png");//ImageIO.read(new File("Resources/barfectlogo.png"));
		//logo = scale(logo, BufferedImage.TYPE_INT_ARGB, 800, 600, 0.2, 0.2);
		logoLabel.setIcon(new ImageIcon(getClass().getResource("barfectlogo.png")));
		JPanel logoPanel = new JPanel();
		//logoPanel.setBackground(Color.BLACK);
		logoPanel.setLayout(new BorderLayout());
		logoPanel.setPreferredSize(new Dimension(400, 150));
		logoPanel.add(logoLabel, BorderLayout.CENTER);
		tags.gridwidth = 0;
		tags.gridx = 0;
		tags.gridy = 0;
		searchPanel.add(logoPanel, tags);
		
		
		//This search function updates after every keystroke so its almost like live search updates while typing
		
		JCSearchField searchBar = new JCSearchField();
		searchBar.setBounds(20, 20, 400, 20);
		searchBar.setRoundness(0);
		searchBar.addKeyListener(new KeyAdapter() {
			
			public void keyPressed(KeyEvent e){
				getContentPane().remove(barPanel);
				
				JCDynamicList bars = new JCDynamicList(20, BarfectIO.getBarByName(searchBar.getText()), new Dimension(260, 120));
				JPanel barPanel = new JPanel();
				barPanel.setLayout(new BorderLayout());
				barPanel.setBorder(null);
				barPanel.setBackground(Color.GRAY);
				barPanel.add(bars, BorderLayout.CENTER);
				barCardList = bars;
				barPanel.add(barCardList);
				getContentPane().add(barPanel, BorderLayout.CENTER);
				getContentPane().revalidate();
				getContentPane().repaint();
			}
		});
		
		JPanel searchBarPanel = new JPanel();
		searchBarPanel.setBounds(0, 0, 400, 65);
		searchBarPanel.setLayout(new BorderLayout());
		searchBarPanel.add(searchBar);
		//tags.gridwidth = 4;
		tags.gridx = 0;
		tags.gridy = 1;
		tags.fill = GridBagConstraints.HORIZONTAL;
		searchPanel.add(searchBarPanel, tags);
		

		//Buttons! These used a method to replace the current bar list with a new one
		//TODO these methods are highly inefficient and extremely unstable @Use cardlayout in future? -Shaun
		
		JButton ordered = new JButton();
		ordered.setText("A - Z");
		tags.gridwidth = 1;
		tags.gridx = 1;
	    tags.gridy = 3;
	    ordered.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				bar.clear();
				

  				getContentPane().remove(barPanel);				
				JCDynamicList bars = new JCDynamicList(20, BarfectIO.Ordered(), new Dimension(260, 120));
				JPanel barPanel = new JPanel();
				barPanel.setLayout(new BorderLayout());
				barPanel.setBorder(null);
				barPanel.setBackground(Color.GRAY);
				barPanel.add(bars, BorderLayout.CENTER);
				barCardList = bars;
				getContentPane().add(barPanel, BorderLayout.CENTER);
				getContentPane().revalidate();
				getContentPane().repaint();
				
				
			}
		});
	    searchPanel.add(ordered, tags);
	    
	    JButton shuffle = new JButton();
		shuffle.setText("Shuffle");
		tags.gridwidth = 1;
		tags.gridx = 1;
	    tags.gridy = 4;
	    shuffle.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				bar.clear();
				

  				getContentPane().remove(barPanel);				
				JCDynamicList bars = new JCDynamicList(20, BarfectIO.ShuffledBarList(), new Dimension(260, 120));
				JPanel barPanel = new JPanel();
				barPanel.setLayout(new BorderLayout());
				barPanel.setBorder(null);
				barPanel.setBackground(Color.GRAY);
				barPanel.add(bars, BorderLayout.CENTER);
				barCardList = bars;
				getContentPane().add(barPanel, BorderLayout.CENTER);
				getContentPane().revalidate();
				getContentPane().repaint();
				
				
			}
		});
	    searchPanel.add(shuffle, tags);
			
		JButton open = new JButton();
		open.setText("Open");
		tags.gridwidth = 1;
		tags.gridx = 1;
	    tags.gridy = 2;
	    open.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				getContentPane().remove(barPanel);
				
				ArrayList<Representable> bar = new ArrayList<>(BarfectIO.isOpen().values());
				
				JCDynamicList bars = new JCDynamicList(20, bar, new Dimension(260, 120));
				JPanel barPanel = new JPanel();
				barPanel.setLayout(new BorderLayout());
				barPanel.setBorder(null);
				barPanel.setBackground(Color.GRAY);
				barPanel.add(bars, BorderLayout.CENTER);
				barCardList = bars;
				barPanel.add(barCardList);
				getContentPane().add(barPanel, BorderLayout.CENTER);
				getContentPane().revalidate();
				getContentPane().repaint();
				
				
			}
		});
		searchPanel.add(open, tags);
		
		JButton heden = new JButton();
		heden.setText("Heden");
		tags.gridwidth = 1;
		tags.gridx = 0;
	    tags.gridy = 2;
		searchPanel.add(heden, tags);
		heden.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				getContentPane().remove(barPanel);
				
				ArrayList<Representable> bar = new ArrayList<>(BarfectIO.getBarsByNeighbourhoods("heden").values());
				
				JCDynamicList bars = new JCDynamicList(20, bar, new Dimension(260, 120));
				JPanel barPanel = new JPanel();
				barPanel.setLayout(new BorderLayout());
				barPanel.setBorder(null);
				barPanel.setBackground(Color.GRAY);
				barPanel.add(bars, BorderLayout.CENTER);
				barCardList = bars;
				barPanel.add(barCardList);
				getContentPane().add(barPanel, BorderLayout.CENTER);
				getContentPane().revalidate();
				getContentPane().repaint();
				
				
			}
		});
		
		JButton inomVallgren = new JButton();
		inomVallgren.setText("Inom Vallgren");
		tags.gridwidth = 1;
		tags.gridx = 0;
	    tags.gridy = 3;
		searchPanel.add(inomVallgren, tags);
		inomVallgren.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				getContentPane().remove(barPanel);
				
				ArrayList<Representable> bar = new ArrayList<>(BarfectIO.getBarsByNeighbourhoods("inom vallgren").values());
				
				JCDynamicList bars = new JCDynamicList(20, bar, new Dimension(260, 120));
				JPanel barPanel = new JPanel();
				barPanel.setLayout(new BorderLayout());
				barPanel.setBorder(null);
				barPanel.setBackground(Color.GRAY);
				barPanel.add(bars, BorderLayout.CENTER);
				barCardList = bars;
				barPanel.add(barCardList);
				getContentPane().add(barPanel, BorderLayout.CENTER);
				getContentPane().revalidate();
				getContentPane().repaint();
				
				
			}
		});

		
		
		JButton lindhomen = new JButton();
		lindhomen.setText("Lindhomen");
		tags.gridwidth = 1;
		tags.gridx = 0;
	    tags.gridy = 4;
		searchPanel.add(lindhomen, tags);
		lindhomen.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				getContentPane().remove(barPanel);
				
				ArrayList<Representable> bar = new ArrayList<>(BarfectIO.getBarsByNeighbourhoods("lindholmen").values());
				
				JCDynamicList bars = new JCDynamicList(20, bar, new Dimension(260, 120));
				JPanel barPanel = new JPanel();
				barPanel.setLayout(new BorderLayout());
				barPanel.setBorder(null);
				barPanel.setBackground(Color.GRAY);
				barPanel.add(bars, BorderLayout.CENTER);
				barCardList = bars;
				barPanel.add(barCardList);
				getContentPane().add(barPanel, BorderLayout.CENTER);
				getContentPane().revalidate();
				getContentPane().repaint();
				
				
			}
		});

		
		
		JButton lorensberg = new JButton();
		lorensberg.setText("Lorensberg");
		tags.gridwidth = 1;
		tags.gridx = 0;
	    tags.gridy = 5;
		searchPanel.add(lorensberg, tags);
		lorensberg.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				getContentPane().remove(barPanel);
				
				ArrayList<Representable> bar = new ArrayList<>(BarfectIO.getBarsByNeighbourhoods("lorensberg").values());
				
				JCDynamicList bars = new JCDynamicList(20, bar, new Dimension(260, 120));
				JPanel barPanel = new JPanel();
				barPanel.setLayout(new BorderLayout());
				barPanel.setBorder(null);
				barPanel.setBackground(Color.GRAY);
				barPanel.add(bars, BorderLayout.CENTER);
				barCardList = bars;
				barPanel.add(barCardList);
				getContentPane().add(barPanel, BorderLayout.CENTER);
				getContentPane().revalidate();
				getContentPane().repaint();
				
				
			}
		});

		
		JButton olivedal = new JButton();
		olivedal.setText("Olivedal");
		tags.gridwidth = 1;
		tags.gridx = 0;
	    tags.gridy = 6;
		searchPanel.add(olivedal, tags);
		olivedal.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				getContentPane().remove(barPanel);
				
				ArrayList<Representable> bar = new ArrayList<>(BarfectIO.getBarsByNeighbourhoods("olivedal").values());
				
				JCDynamicList bars = new JCDynamicList(20, bar, new Dimension(260, 120));
				JPanel barPanel = new JPanel();
				barPanel.setLayout(new BorderLayout());
				barPanel.setBorder(null);
				barPanel.setBackground(Color.GRAY);
				barPanel.add(bars, BorderLayout.CENTER);
				barCardList = bars;
				barPanel.add(barCardList);
				getContentPane().add(barPanel, BorderLayout.CENTER);
				getContentPane().revalidate();
				getContentPane().repaint();
				
				
			}
		});

		
		JButton masthugget = new JButton();
		masthugget.setText("Masthugget");
		tags.gridwidth = 1;
		tags.gridx = 0;
	    tags.gridy = 7;
		searchPanel.add(masthugget, tags);
		masthugget.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				getContentPane().remove(barPanel);
				
				ArrayList<Representable> bar = new ArrayList<>(BarfectIO.getBarsByNeighbourhoods("masthugget").values());
				
				JCDynamicList bars = new JCDynamicList(20, bar, new Dimension(260, 120));
				JPanel barPanel = new JPanel();
				barPanel.setLayout(new BorderLayout());
				barPanel.setBorder(null);
				barPanel.setBackground(Color.GRAY);
				barPanel.add(bars, BorderLayout.CENTER);
				barCardList = bars;
				barPanel.add(barCardList);
				getContentPane().add(barPanel, BorderLayout.CENTER);
				getContentPane().revalidate();
				getContentPane().repaint();
				
				
			}
		});

	}
	
	
	//This method opens a new window when a bar car is clicked
	//TODO We need to pull the object from the used hashmap or array list for accurate info!!
	public static void openBarCard(String name, String address, String zipcode, String city, String description, String neighborhood, 
			String openingHours, String closingHours){
		
		
		JFrame barCardFrame = new JFrame(name);
		barCardFrame.setVisible(true);
		barCardFrame.setSize(800, 600);
		barCardFrame.setBounds(100, 100, 450, 300);
		barCardFrame.getContentPane().setLayout(new MigLayout("", "[grow][][][][][][grow]", "[grow][]"));
		
		JPanel imagePanel = new JPanel();
		barCardFrame.getContentPane().add(imagePanel, "cell 0 0 3 1,grow");
		imagePanel.setLayout(new BorderLayout());
//		GridBagLayout imagePanelLayout = new GridBagLayout();
//		imagePanelLayout.columnWidths = new int[]{0, 0};
//		imagePanelLayout.rowHeights = new int[]{0, 0};
//		imagePanelLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
//		imagePanelLayout.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		
		
		JXLabel pictureLabel = new JXLabel();
		
//		GridBagConstraints pictureLabelLayout = new GridBagConstraints();
//		pictureLabelLayout.gridx = 0;
//		pictureLabelLayout.gridy = 0;
//		pictureLabelLayout.fill = GridBagConstraints.BOTH;
		imagePanel.add(pictureLabel, BorderLayout.CENTER);
	
		
		JPanel descriptionPanel = new JPanel();
		barCardFrame.getContentPane().add(descriptionPanel, "cell 3 0 4 1, grow");
		descriptionPanel.setLayout(new BorderLayout());
		JLabel barInfo  = new JLabel();
        barInfo.setText(name);
        barInfo.setSize(250, 40);
        barInfo.setFont(new Font("Avenir Next", Font.BOLD, 30));
       // barInfo.setLocation(245, 45);
        barInfo.setVisible(true);
        descriptionPanel.add(barInfo, BorderLayout.NORTH);
		JLabel descriptionArea = new JLabel();
		descriptionArea.setText(address + ", " + zipcode + " " + city);
		descriptionArea.setFont(new Font("Avenir", Font.PLAIN, 20));
		descriptionArea.setSize(400, 50);
		descriptionPanel.add(descriptionArea, BorderLayout.PAGE_END);
		
		JLabel everythingElse = new JLabel();
		String descriptor = description + " " + neighborhood + " " + openingHours + " " + closingHours;
		String decText = String.format("<html><div WIDTH=%d>%s</div><html>", 300, descriptor);
		everythingElse.setText(decText);
		everythingElse.setFont(new Font("Avenir", Font.PLAIN, 20));
		everythingElse.setSize(250, 40);
		//everythingElse.setLocation(400, 45);
		everythingElse.setVisible(true);
		descriptionPanel.add(everythingElse, BorderLayout.CENTER);
		
		try{
			BufferedImage tempName = ImageIO.read(new File("Resources/pustervikfullsize.png"));
	        JCReflectedImage img = new JCReflectedImage(tempName);
	        pictureLabel.add(img);
		} catch (Exception e) {
				// TODO: handle exception
			}
	}
	
	/**
	 * 
	 * Unused image scalers for possible future use -Shaun
	 * 
	 * @param sbi
	 * @param imageType
	 * @param dWidth
	 * @param dHeight
	 * @param fWidth
	 * @param fHeight
	 * @return
	 */
	
	
	public static BufferedImage scale(BufferedImage sbi, int imageType, int dWidth, int dHeight, double fWidth, double fHeight) {
		BufferedImage dbi = null;
	    if(sbi != null) {
	    	dbi = new BufferedImage(dWidth, dHeight, imageType);
	        Graphics2D g = dbi.createGraphics();
	        AffineTransform at = AffineTransform.getScaleInstance(fWidth, fHeight);
	        g.drawRenderedImage(sbi, at);	
	        }
	    return dbi;
	}
		
	public BufferedImage scaleImage(int WIDTH, int HEIGHT, String filename) {
	    BufferedImage image = null;
	    try {
	    	ImageIcon img = new ImageIcon(filename);
	    	image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
	    	Graphics2D graphics = (Graphics2D) image.createGraphics();
	    	graphics.addRenderingHints(new RenderingHints(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY));
	    	graphics.drawImage(img.getImage(), 0, 0, WIDTH, HEIGHT, null);
	    }catch (Exception e){
	    	e.printStackTrace();
	    	return null;
	    }
	    return image;
	}

}
