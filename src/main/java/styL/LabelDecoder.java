package styL;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.*;

public class LabelDecoder {

    private final String artsy = "[\\w*\\s*]*((ART)|(GRAPHIC)|(COLOU*R))[\\w*\\s*]*";
    private final String bohemian = "[\\w*\\s*]*((BOHEMIAN)|(BOHO)|(GYPSY)|(HIPPIE)|(PATTERN))[\\w*\\s*]*";
    private final String casual = "[\\w*\\s*]*((CASUAL)|(JEAN)|(TEE)|(HOOD))[\\w*\\s*]*";
    private final String chic = "[\\w*\\s*]*((CHIC)|(TREND)|(SKIRT)|(BLOUSE)|(PURSE))[\\w*\\s*]*";
    private final String formal = "[\\w*\\s*]*((FORMAL)|(BUSINESS)|(SUIT)|(BLAZER)|(COLLAR)|(TIE)|(HEEL)|(SKIRT)|(DRESS)|(BLOUSE))[\\w*\\s*]*";
    private final String girly = "[\\w*\\s*]*((DRESS)|(SKIRT)|(LACE)|(FRILL))[\\w*\\s*]*";
    private final String grunge = "[\\w*\\s*]*((GRUNG)|(BAGGY)|(TORN)|(PLAID)|(FLANNEL)|(BOOT))[\\w*\\s*]*";
    private final String preppy = "[\\w*\\s*]*((PREP)|(SCHOOL)|(STUDENT)|(GLASSES)|(BAG))[\\w*\\s*]*";
    private final String rocker = "[\\w*\\s*]*((ROCK)|(BIKE)|(LEATHER)|(DENIM)|(BOOT))[\\w*\\s*]*";
    private final String sporty = "[\\w*\\s*]*((SPORT)|(ACTIVE)|(ATHLET)|(RUN)|(SHORTS)|(TANK))[\\w*\\s*]*";
    private final String street = "[\\w*\\s*]*((STREET)|(SURF)|(SKATE)|(CAP)|(HAT)|(TEE))[\\w*\\s*]*";
    private final String vintage = "[\\w*\\s*]*((VINTAGE)|(RETRO)|(DRESS)|(BLOUSE))[\\w*\\s*]*";

    private final String styleDescriptions[] = {
            artsy,
            bohemian,
            casual,
            chic,
            girly,
            grunge,
            formal,
            preppy,
            rocker,
            sporty,
            street,
            vintage
    };
    private final String styleNames[] = {
            "Artsy",
            "Bohemian",
            "Casual",
            "Chic",
            "Girly",
            "Grunge",
            "Formal",
            "Preppy",
            "Rocker",
            "Sporty",
            "Street",
            "Vintage"
    };

    private Map<String, String> lookupStyle;

    public LabelDecoder() {
        initializeStyleMap();
    }

    private void initializeStyleMap() {
        lookupStyle = new HashMap<>();
        for (int i = 0; i < styleNames.length; i++) {
            lookupStyle.put(styleDescriptions[i], styleNames[i]);
        }
    }

    public void decode(Map<String, Float> imageLabels) {
        Map<String, Float> styleHits = new HashMap<>();
        int totalHits = 0;

        for (String label : imageLabels.keySet()) {
            String labelUppercase = label.toUpperCase();
            for (String styleDescription : lookupStyle.keySet()) {
                if (labelUppercase.matches(styleDescription)) {
                    float hits = styleHits.getOrDefault(lookupStyle.get(styleDescription), 0f);
                    styleHits.put(lookupStyle.get(styleDescription), hits + imageLabels.get(label));
                    totalHits++;
                }
            }
        }
        printResults(styleHits, totalHits);
    }

    private void printResults(Map<String, Float> hitMap, int totalHits) {
        // sort map somehow to print larges to smallest percentage?

        NumberFormat formatter = new DecimalFormat("#0.0");
        System.out.println("Results: \n");

        if (totalHits == 0) {
            System.out.println("Could not match to a style");
        } else {
            for (String style : hitMap.keySet()) {
                double percentMatch = (double) hitMap.get(style) / totalHits * 100;
                System.out.println(style + ": " + formatter.format(percentMatch) + "% match");
            }
        }
    }
}
