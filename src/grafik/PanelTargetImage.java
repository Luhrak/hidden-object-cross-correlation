package grafik;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class PanelTargetImage extends JPanel {

	public Image targetImage;

	public PanelTargetImage() {
		this.setLayout(new BorderLayout());
		this.setPreferredSize(new Dimension(100, 100));

		try {
			targetImage = (Image) ImageIO.read(new File("Bilder/TargetImage.png"));
		} catch (IOException e) {
			System.err.println("Error on loading image: " + e.getMessage());
		}
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (targetImage != null) {
			// Mitte zum zeichnen herausfinden 
			int panelWidth = getWidth();
			int panelHeight = getHeight();
			int imageWidth = targetImage.getWidth(this);
			int imageHeight = targetImage.getHeight(this);
			int x = (panelWidth - imageWidth) / 2;
			int y = (panelHeight - imageHeight) / 2;

			g.drawImage(targetImage, x, y, this);
		}
	}
}
