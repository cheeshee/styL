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
    // as an object


    public static void annotate(String fileName) throws Exception {
        Credentials myCredentials = ServiceAccountCredentials.fromStream(
                new FileInputStream("C:\\Users\\cheep\\Downloads\\rock-heaven-224219-d31b84146fce.json"));
        ImageAnnotatorSettings imageAnnotatorSettings =
                ImageAnnotatorSettings.newBuilder()
                        .setCredentialsProvider(FixedCredentialsProvider.create(myCredentials))
                        .build();
        try (ImageAnnotatorClient vision =
                     ImageAnnotatorClient.create(imageAnnotatorSettings)) {


            // Reads the image file into memory
            Path path = Paths.get(fileName);
            byte[] data = Files.readAllBytes(path);
            ByteString imgBytes = ByteString.copyFrom(data);

            // Builds the image annotation request
            List<AnnotateImageRequest> requests = new ArrayList<>();
            Image img = Image.newBuilder().setContent(imgBytes).build();
            // requests for what we want to do to the image
            AnnotateImageRequest.Builder requestBuilder = AnnotateImageRequest.newBuilder();
            requests.add(requestBuilder
                    .addFeatures(Feature.newBuilder().setType(Type.LABEL_DETECTION).build())
                    .addFeatures(Feature.newBuilder().setType(Type.WEB_DETECTION).build())
                    .addFeatures(Feature.newBuilder().setType(Type.IMAGE_PROPERTIES).build())
                    .setImage(img).build());

            // Performs label detection on the image file
            BatchAnnotateImagesResponse response = vision.batchAnnotateImages(requests);
            List<AnnotateImageResponse> responses = response.getResponsesList();

            for (AnnotateImageResponse res : responses) {
                if (res.hasError()) {
                    System.out.printf("Error: %s\n", res.getError().getMessage());
                    return;
                }

                System.out.println("LABELS:\n");
                for (EntityAnnotation annotation : res.getLabelAnnotationsList()) {
                    System.out.printf("%s: %.2f\n", annotation.getDescription(), annotation.getScore());
                }
                System.out.println("");

                System.out.println("WEB:\n");
                WebDetection webDet = res.getWebDetection();
                for (WebDetection.WebEntity entity : webDet.getWebEntitiesList()) {
                    System.out.printf("%s: %.2f\n", entity.getDescription(), entity.getScore());
                }
                System.out.println("");

                System.out.println("COLOURS:\n");
                DominantColorsAnnotation domColours = res.getImagePropertiesAnnotation().getDominantColors();
                for (ColorInfo colour : domColours.getColorsList()) {
                    System.out.printf("Colour R=%.2f, G=%.2f, B=%.2f: %.3f percent of image\n",
                            colour.getColor().getRed(), colour.getColor().getGreen(), colour.getColor().getBlue(), colour.getScore());
                }

            }
        }
    }
}
