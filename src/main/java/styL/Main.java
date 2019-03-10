package styL;

import java.util.Scanner;

public class Main {
   // /*
    public static void main(String[] args) {

        String fileName = askForFile();
        System.out.println("Reading file at " + fileName);
        try {
            ImageReader.annotate(fileName);


        } catch(Exception e) {
            System.out.println("Error: " + e);
        }

        // for use with terminal lol
        /*
        if (args.length == 1) {
            try {
                String fileName = args[0];
                ImageReader.main();
            } catch (Exception e) {
                System.out.println("oof\n");
            }

            String inputFile = args[0];
            System.out.println("Received input: " + inputFile);
            ImageReader reader = new ImageReader();
            reader.annotate(inputFile);

        } else {
            System.out.println("Usage: java classes.styL.Main fileName.ext");
        }
        */
    }

    private static String askForFile() {
        System.out.println("Enter the path of your photo: ");
        Scanner sc = new Scanner(System.in);
        return sc.next();
    }

}
