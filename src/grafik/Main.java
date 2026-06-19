package grafik;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JFrame;
import javax.swing.Timer;
import logic.CrossLogic;

public class Main extends JFrame {

	MouseAdapter clickLister;
	
	// Starte Spiel von hier 
	public static void main(String[] args) {
		Main frame = new Main();
	}
	
	public Main() {
		CrossLogic logic = new CrossLogic();
		this.setTitle("Wimmelspiel");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		PanelCombination pannelCombi = new PanelCombination(); 
		clickLister = new MouseAdapter() {
		    @Override 
		    public void mousePressed(MouseEvent e) {
		        System.out.println("Clicked at:" + e.getX()+ "," + e.getY());
		        
		        double correctPercent = logic.evaluateInput(e.getX(), e.getY());
		        
				boolean isCorrect; 
				if (correctPercent >= 99) {
					isCorrect = true; 
				} else {
					isCorrect = false;
				}

		        if (isCorrect) {
		        	pannelCombi.panelInputImage.removeMouseListener(clickLister);
		        	pannelCombi.pannelFooter.updateText("  Richtig!");
		        	
		        	// Einen moment warten damit der User verarbeiten kann, dass er es geschafft hat
		        	Timer timer = new Timer(1000, e1-> {
		        		dispose(); 
		        		new Main();  
		        	});
		        	timer.setRepeats(false); 
		            timer.start();
		        	
		        } else {
		        	int ähnlichkeit = Math.max(0, (int)correctPercent);
		        	pannelCombi.pannelFooter.updateText("  " + ähnlichkeit + "% Ähnlichkeit.");
		        }
		    }
		};
		
		pannelCombi.panelInputImage.addMouseListener(clickLister);
		add(pannelCombi);
		
		// Damit die visuellen Koordinaten immer den Bildkoordinaten entspricht: 
		this.pack();
		this.setResizable(false); 
		
		this.setVisible(true);
	}
}
