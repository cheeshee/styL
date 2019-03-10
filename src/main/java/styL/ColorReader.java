package styL;

import com.google.cloud.vision.v1.AnnotateImageResponse;
import com.google.cloud.vision.v1.ColorInfo;
import com.google.cloud.vision.v1.DominantColorsAnnotation;

public class ColorReader {

    public void ColorImpression (AnnotateImageResponse res) {

        System.out.println("COLOURS:\n");
        DominantColorsAnnotation domColours = res.getImagePropertiesAnnotation().getDominantColors();

        for (ColorInfo colour : domColours.getColorsList()) {
            System.out.printf("Colour R=%.2f, G=%.2f, B=%.2f: %.3f percent of image\n",
                    colour.getColor().getRed(), colour.getColor().getGreen(), colour.getColor().getBlue(), colour.getScore());

        }
    }
}
