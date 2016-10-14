import java.awt.EventQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.WindowConstants;

/**
 * 
 * @author shaunmcmurray
 * 
 * This just runs the main window found in mainframe
 * Received an error when setting icon for frame
 * TODO fix icon thingy -Shaun
 *
 */

public class Barfect {
	
	public static void main(String[] args){
		
		EventQueue.invokeLater(() -> {
			try {
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				JFrame mainFrame = new MainFrame();
				mainFrame.setVisible(true);
				mainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
				mainFrame.setLocationRelativeTo(null);
				mainFrame.setTitle("Barfect");
				//mainFrame.setIconImage(ImageIO.read(new File("barfectlogo.png")));
				mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
			} catch (Exception e) {
				Logger.getLogger(Barfect.class.getName()).log(Level.SEVERE, null, e);
			}
		});
		
	}

}
