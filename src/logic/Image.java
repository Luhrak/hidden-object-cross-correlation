package logic;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

// Klasse aus den Laboraufgaben 
public class Image {
	// Attribute
	public Mat bildMatrix;
	
	public Image(Mat matrix) {
		bildMatrix = matrix;
	}

	public Image(String dateiName) {
		// Die Bild-Datei in Matrix umwandeln
		Mat pixelValues = Imgcodecs.imread(dateiName, Imgcodecs.IMREAD_UNCHANGED);
		
		// Kontrolliere, ob die Datei existiert
		if (pixelValues.empty()) {
			System.err.println("Error on loading image: " + dateiName);
			System.exit(0);
		} else {
			bildMatrix = pixelValues;
		}
	}

	public void save(String dateiName) {
		Imgcodecs.imwrite(dateiName, bildMatrix);
	}

	public Image getImageCutout(int x, int y, int hohe, int breite) {
		Rect bereich = new Rect(x, y, breite , hohe);
		Mat teilMatrix = new Mat(bildMatrix, bereich );
		Image ergebnis = new Image(teilMatrix);
		return ergebnis;
	}

    public Image convertToGrayscale() {
        Mat kopie = bildMatrix.clone();
        
        Imgproc.cvtColor(bildMatrix, kopie, Imgproc.COLOR_BGR2GRAY);
        kopie.convertTo(kopie, CvType.CV_32F);
        
        Image ergebnis = new Image(kopie);
        return ergebnis;
    }
}