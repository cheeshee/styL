package styL;

// Import the Google Cloud client library
import com.google.api.gax.core.CredentialsProvider;
import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.api.gax.paging.Page;
import com.google.api.gax.rpc.ClientContext;
//import com.google.auth.Credentials;
import com.google.auth.*;
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

public class ImageReader {
/*
    //private Credentials myCredentials;
    //private ImageAnnotatorSettings imageAnnotatorSettings;
    //private ImageAnnotatorClient vision;

    // default constructor initializes credentials
    public ImageReader() {
        try {
            myCredentials = ServiceAccountCredentials.fromStream(
                    new FileInputStream("C:\\Users\\maxin\\Downloads\\rock-heaven-224219-d31b84146fce.json"));
            imageAnnotatorSettings =
                    ImageAnnotatorSettings.newBuilder()
                            .setCredentialsProvider(FixedCredentialsProvider.create(myCredentials))
                            .build();
            vision = ImageAnnotatorClient.create(imageAnnotatorSettings);
        } catch (FileNotFoundException e) {
            System.out.println("Error initializing credentials");
        } catch (IOException e) {
            System.out.println("Error reading input files");
        }

    }

    public void annotate(String fileName) {
        try {
            Credentials myCredentials = ServiceAccountCredentials.fromStream(
                    new FileInputStream("C:\\Users\\maxin\\Downloads\\rock-heaven-224219-d31b84146fce.json"));
            ImageAnnotatorSettings imageAnnotatorSettings =
                    ImageAnnotatorSettings.newBuilder()
                            .setCredentialsProvider(FixedCredentialsProvider.create(myCredentials))
                            .build();
            ImageAnnotatorClient vision = ImageAnnotatorClient.create(imageAnnotatorSettings);

            // The path to the image file to annotate

            // Reads the image file into memory
            Path path = Paths.get(fileName);
            try {
                byte[] data = Files.readAllBytes(path);
                ByteString imgBytes = ByteString.copyFrom(data);

                // Builds the image annotation request
                List<AnnotateImageRequest> requests = new ArrayList<>();
                Image img = Image.newBuilder().setContent(imgBytes).build();
                Feature feat = Feature.newBuilder().setType(Type.LABEL_DETECTION).build();
                AnnotateImageRequest request = AnnotateImageRequest.newBuilder()
                        .addFeatures(feat)
                        .setImage(img)
                        .build();
                requests.add(request);

                // Performs label detection on the image file
                BatchAnnotateImagesResponse response = vision.batchAnnotateImages(requests);
                List<AnnotateImageResponse> responses = response.getResponsesList();

                for (AnnotateImageResponse res : responses) {
                    if (res.hasError()) {
                        System.out.println("womp :(\n");
                        return;
                    }
                    for (EntityAnnotation annotation : res.getLabelAnnotationsList()) {
                        System.out.printf("%s: %s\n", annotation.getDescription(), annotation.getScore());
                    }
                }
            } catch (IOException e) {
                System.out.println("Error reading input file");
                return;
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error initializing credentials");
            return;
        } catch (IOException e) {
            System.out.println("Error reading input files");
            return;
        }
    }
*/

    public static void annotate(String fileName) throws Exception {
        Credentials myCredentials = ServiceAccountCredentials.fromStream(
                new FileInputStream("C:\\Users\\maxin\\Downloads\\rock-heaven-224219-d31b84146fce.json"));
        ImageAnnotatorSettings imageAnnotatorSettings =
                ImageAnnotatorSettings.newBuilder()
                        .setCredentialsProvider(FixedCredentialsProvider.create(myCredentials))
                        .build();
        try (ImageAnnotatorClient vision =
                     ImageAnnotatorClient.create(imageAnnotatorSettings)) {

<<<<<<< HEAD

            // The path to the image file to annotate
            String fileName = "C:/Users/cheep/Downloads/Pengasia+Street+Style+2017+-+Womens+25-01.jpg";

=======
>>>>>>> 02dafbadced88ee03f313a8e80fc7a70e2a3ae9e
            // Reads the image file into memory
            Path path = Paths.get(fileName);
            byte[] data = Files.readAllBytes(path);
            ByteString imgBytes = ByteString.copyFrom(data);

            // Builds the image annotation request
            List<AnnotateImageRequest> requests = new ArrayList<>();
            Image img = Image.newBuilder().setContent(imgBytes).build();
            Feature feat = Feature.newBuilder().setType(Type.LABEL_DETECTION).build();
            AnnotateImageRequest request = AnnotateImageRequest.newBuilder()
                    .addFeatures(feat)
                    .setImage(img)
                    .build();
            requests.add(request);

            // Performs label detection on the image file
            BatchAnnotateImagesResponse response = vision.batchAnnotateImages(requests);
            List<AnnotateImageResponse> responses = response.getResponsesList();

            for (AnnotateImageResponse res : responses) {
                if (res.hasError()) {
                    System.out.printf("Error: %s\n", res.getError().getMessage());
                    return;
                }

                for (EntityAnnotation annotation : res.getLabelAnnotationsList()) {
                    System.out.printf("%s: %s\n", annotation.getDescription(), annotation.getScore());
                }
            }
        }
    }
}
