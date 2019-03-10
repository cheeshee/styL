package styL;

import com.google.api.gax.core.CredentialsProvider;
import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.api.gax.paging.Page;
import com.google.api.gax.rpc.ClientContext;
import com.google.auth.Credentials;
import com.google.auth.oauth2.AccessToken;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.vision.v1.*;
import com.google.cloud.vision.v1.Feature.Type;
import com.google.common.collect.Lists;
import com.google.protobuf.ByteString;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
   // /*
    public static void main(String[] args) {

        String fileName = askForFileName();
        System.out.println("File name received: " + fileName);
        try {
            ImageReader.annotate(fileName);
        } catch(Exception e) {
            // blah
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

    private static String askForFileName() {
        System.out.println("Enter the path of your photo: ");
        Scanner sc = new Scanner(System.in);
        return sc.next();
    }

}
