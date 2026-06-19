package logic;

import java.util.Random;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

public class CrossLogic {

	int height;
	int width;
	Image inputImage;
	Image outputImage;
	Image targetImage;
	Image inputImageGray;
	Image targetImageGray;

	public CrossLogic() {
		// Lade zufälliges Eingabebild
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		Random random = new Random();
		loadInputImage(random.nextInt(8));

		// Setze Dimensionen
		height = inputImage.bildMatrix.rows();
		width = inputImage.bildMatrix.cols();

		// Generiere ein Suchbild
		int x = random.nextInt(inputImage.bildMatrix.cols() - 100);
		int y = random.nextInt(inputImage.bildMatrix.rows() - 100);
		targetImage = inputImage.getImageCutout(x, y, 100, 100);

		// Speichere Bilder 
		outputImage.save("Bilder/OutputImage.png");
		targetImage.save("Bilder/TargetImage.png");

		// Generiere Graue Bilder für Kreuz Korrelation
		inputImageGray = inputImage.convertToGrayscale();
		targetImageGray = targetImage.convertToGrayscale();
	}

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}

	public void loadInputImage(int imageNumber) {
		switch (imageNumber) {
		case 0:
			inputImage = new Image("Bilder/Eingabebilder/1_Blaetter.jpg");
			break;
		case 1:
			inputImage = new Image("Bilder/Eingabebilder/2_Altes Haus.jpg");
			break;
		case 2:
			inputImage = new Image("Bilder/Eingabebilder/3_Wimmelbild.jpg");
			break;
		case 3:
			inputImage = new Image("Bilder/Eingabebilder/4_Buero.jpg");
			break;
		case 4:
			inputImage = new Image("Bilder/Eingabebilder/5_Stadt.jpg");
			break;
		case 5:
			inputImage = new Image("Bilder/Eingabebilder/6_Buffet.jpg");
			break;
		case 6:
			inputImage = new Image("Bilder/Eingabebilder/7_Kinderzimmer.jpg");
			break;
		case 7:
			inputImage = new Image("Bilder/Eingabebilder/8_Bibliothek.jpg");
			break;
		default:
			System.err.println("Invalid imageNumber");
			break;
		}
		outputImage = inputImage; 
	}

	public double evaluateInput(int x, int y) {

		// Klammere x und y damit userTempalte nicht über das Bild hinaus ragt
		int xClamped = Math.max(50, Math.min((width - 50), x));
		int yClamped = Math.max(50, Math.min((height - 50), y));

		// Erstelle userTemplate zentiert auf Mauseingabe 
		Image userTemplate = inputImage.getImageCutout(xClamped - 10, yClamped - 10, 20, 20);
		userTemplate.save("Bilder/userTemplate.png");

		// Bekomme Korrelationswert Wert 
		Image userTemplateGrau = userTemplate.convertToGrayscale();
		double correlationPercent = crossCorrelate(userTemplateGrau);
		System.out.println("Korrelationswert:" + correlationPercent);
		
		// Zeichne ein rechteck zentiert auf Mauseingabe 
		outputImageWithRectangle(correlationPercent, xClamped, yClamped);

		return correlationPercent;
	}

	private double crossCorrelate(Image userTemplateGrau) {
		// Ergebnis-Matrix für Template Matching vorbereiten
		int resultCols = targetImageGray.bildMatrix.cols() - userTemplateGrau.bildMatrix.cols() + 1;
		int resultRows = targetImageGray.bildMatrix.rows() - userTemplateGrau.bildMatrix.rows()  + 1;
		Mat result = new Mat(resultRows, resultCols, CvType.CV_32FC1);

		// Kreuzkorrelation ausführen!
		Imgproc.matchTemplate(targetImageGray.bildMatrix, userTemplateGrau.bildMatrix, result, Imgproc.TM_CCOEFF_NORMED);

		// Größten Wert ermitteln und ausgeben 
		Core.MinMaxLocResult mmr = Core.minMaxLoc(result);
		return mmr.maxVal *100;
	}

	private void outputImageWithRectangle(double correctPercent, int x, int y) {
		// Farbe ermitteln
		Scalar recColor;
		if (correctPercent >= 99) {
			recColor = new Scalar(0, 255, 0);
		} else {
			recColor = new Scalar(0, 0, 255);
		}

		// Bild mit gezeichnetem Rechteck erstellen
		Point p1 = new Point(x - 50, y - 50);
		Point p2 = new Point(x + 50, y + 50);

		Image outputImage = new Image(inputImage.bildMatrix.clone());
		Imgproc.rectangle(outputImage.bildMatrix, p1, p2, recColor, 2);
		outputImage.save("Bilder/OutputImage.png");
	}

}