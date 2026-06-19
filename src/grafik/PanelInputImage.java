package grafik;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class PanelInputImage extends JPanel {

	public Image intputImage;
	
	public PanelInputImage() {
		try {
			// Wir nehmen ausgabe.png damit es immer das mit dem gezecihneten Rechteck nimmt 
			intputImage = (Image)ImageIO.read(new File("Bilder/OutputImage.png"));
		} catch (IOException e) {
			System.err.println("Error on loading image: " + e.getMessage());
		}
	}
	
	@Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); 
        
        try {
			intputImage = (Image)ImageIO.read(new File("Bilder/OutputImage.png"));
		} catch (IOException e) {
			System.err.println("Error on loading image: " + e.getMessage());
		}

        if (intputImage != null) {
        	// Mitte zum zeichnen herausfinden 
        	int panelWidth = getWidth();
			int panelHeight = getHeight();
			int imageWidth = intputImage.getWidth(this);
			int imageHeight = intputImage.getHeight(this);
			int x = (panelWidth - imageWidth) / 2;
			int y = (panelHeight - imageHeight) / 2;

			g.drawImage(intputImage, x, y, this);
			repaint();
        }
    }
	
	// Ändert preferedSize zu den Bild dimensionen damit das panel immer genauso groß ist wie das Bild
	@Override
	public Dimension getPreferredSize() {
	    if (intputImage != null) {
	        return new Dimension(intputImage.getWidth(this), intputImage.getHeight(this));
	    }
	    return new Dimension(400, 300); // fallback
	}
}
