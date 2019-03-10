package styL;

import com.google.cloud.vision.v1.ColorInfo;
import com.google.cloud.vision.v1.DominantColorsAnnotation;

import java.awt.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ColourReader {
    float leeway = 50;
    float blackMax = 100;
    float whiteMin = 240;



    public void ColourImpression (DominantColorsAnnotation imageColours) {

        for (ColorInfo colour : imageColours.getColorsList()) {
            float red = colour.getColor().getRed();
            float green = colour.getColor().getGreen();
            float blue = colour.getColor().getBlue();


            System.out.printf("Colour R=%.2f, G=%.2f, B=%.2f: %.3f percent of image\n",
                    red, green, blue, colour.getScore());


            if (colour.getScore() >= 0.20) {
                String mainColour = whichColour(red, green, blue);

                System.out.printf("Your clothes are mostly %s \n", mainColour);

            }


        }

    }


    private String whichColour(float red, float green, float blue) {
        String colour;

        if (isBlack(red, green, blue)) {
            colour = "black. \n" +
                    "This indicates seriousness, power, and intelligence. \n";

        } else if (isWhite(red, green, blue)) {
            colour = "white. \n" +
                    "This is a neutral color. " +
                    "It may give the impression that you are neat and organized or pure and simple. \n";

        } else if (isGrey(red, green, blue)) {
            colour = "grey. \n" +
                    "This is a very neutral color. " +
                    "It will prevent drawing attention to yourself. \n";

        } else if (isBrown(red, green, blue)) {
            colour = "brown. \n" +
                    "This gives the impression that you are reliable, rational, and more conservative. \n";

        } else if (isRed(red, green, blue)) {
            colour = "red. \n" +
                    "This will draw attention to yourself. It is an indictor of passion and energy. \n";

        } else if (isBlue(red, green, blue)) {
            colour = "blue. \n" +
                    "This associates you with trust, calmness, and reliability. \n";

        }else if (isGreen(red, green, blue)) {
            colour = "green.\n" +
                    "This gives the impression that you are active, kind-hearted, and content. \n";

        }else if (isPurple(red, green, blue)) {
            colour = "purple. \n" +
                    "This gives the impression that you are dreamy, emotional, and unpredictable. \n";

        }else if (isYellow(red, green, blue)) {
            colour = "yellow. \n" +
                    "This is an indicator of happiness and creativity. \n";

        }else if (isCyan(red, green, blue)) {
            colour = "blue. \n" +
                    "This is a calming colour that gives the impression of trustworthiness. \n";

        }else if (isMagenta(red, green, blue)) {
            colour = "pink. \n" +
                    "This is a striking colour that may attract attention. " +
                    "This gives the impression that you are unpredictable, energetic, and dreamy. \n";

        }else if (isPink(red, green, blue)) {
            colour = "pink.\n " +
                    "This gives the impression that you are romantic, optimistic, and kind. \n";

        } else if (isOrange(red, green, blue)) {
            colour = "orange. \n" +
                    "This is gives the impression that you are cheerful, optimistic, and creative. \n";

        } else {
            colour = "an uncommon colour. \n" +
                    "This may attract attention.";
        }

        return colour;
    }

    private boolean isBlack(float red, float green, float blue) {
        boolean result = false;
        if (red <= blackMax && green <= blackMax && blue <= blackMax) {
            result = true;
        }
        return result;
    }

    private boolean isWhite(float red, float green, float blue) {
        boolean result = false;
        if (red >= whiteMin && green >= whiteMin && blue >= whiteMin) {
            result = true;
        }
        return result;
    }

    private boolean isGrey(float red, float green, float blue) {
        boolean result = false;
        if (green <= red + leeway && green >= red - leeway) {
            if (green <= blue + leeway && green >= blue - leeway) {
                result = true;
            }
        }
        return result;
    }


    private boolean isRed(float red, float green, float blue) {
        boolean result = false;
        if (red <= 255 && red >= 255 - leeway) {
            if (green <= blue + leeway && green >= blue - leeway){
                result = true;
            }

        }
        return result;
    }

    private boolean isBrown(float red, float green, float blue) {
        boolean result = false;
        if (red < 200) {
            if (green >= blue + leeway){
                result = true;
            }

        }
        return result;
    }

    private boolean isPurple(float red, float green, float blue) {
        boolean result = false;
        if (red <= blue + leeway && red >= blue - leeway) {
            if (red < whiteMin && blue < whiteMin){
                result = true;
            }

        }
        return result;
    }

    private boolean isOrange(float red, float green, float blue) {
        boolean result = false;
        if (red <= 255 && red >= 255 - leeway) {
            if (green >= blue + leeway){
                result = true;
            }

        }
        return result;
    }

    private boolean isYellow(float red, float green, float blue) {
        boolean result = false;
        if (green <= red + leeway && green >= red - leeway) {
            result = true;

        }
        return result;
    }

    private boolean isGreen(float red, float green, float blue) {
        boolean result = false;
        if (green <= 255 && green >= 255 - leeway) {
            result = true;

        }
        return result;
    }

    private boolean isCyan(float red, float green, float blue) {
        boolean result = false;
        if (green <= blue + leeway && green >= blue - leeway) {
            result = true;

        }
        return result;
    }

    private boolean isBlue(float red, float green, float blue) {
        boolean result = false;
        if (blue <= 255 && blue >= 255 - leeway) {
            result = true;

        }
        return result;
    }

    private boolean isMagenta(float red, float green, float blue) {
        boolean result = false;
        if (red <= blue + leeway && red >= blue - leeway) {
            result = true;

        }
        return result;
    }

    private boolean isPink(float red, float green, float blue) {
        boolean result = false;
        if (red <= blue + leeway && red >= green - leeway) {
            result = true;

        }
        return result;
    }
}
