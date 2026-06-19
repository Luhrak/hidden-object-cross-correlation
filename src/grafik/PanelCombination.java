package grafik;

import java.awt.BorderLayout;
import javax.swing.JPanel;

public class PanelCombination extends JPanel {

	public PanelInputImage panelInputImage; 
	public PanelFooter pannelFooter; 

	public PanelCombination() {
		
		this.setLayout(new BorderLayout());
		panelInputImage = new PanelInputImage();
		pannelFooter = new PanelFooter();
		this.add(panelInputImage, BorderLayout.CENTER); 
		this.add(pannelFooter, BorderLayout.SOUTH);
	}
}
