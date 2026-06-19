package grafik;

import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PanelFooter extends JPanel {

	JLabel displayText;
	
	public PanelFooter() {
		
		// Layout 
		this.setLayout(new GridLayout(1, 2));
		
		// Text hinzufügen 
		displayText = new JLabel("  Finde:");
		displayText.setFont(new Font("SansSerif", Font.BOLD, 24)); 
		this.add(displayText);
      
		// Template hinzufügen 
		this.add(new PanelTargetImage());
	}
	
	public void updateText(String newText) {
		displayText.setText(newText);
	}
}
