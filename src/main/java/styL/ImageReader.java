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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

                Map<String, Float> imageLabels = new HashMap<>();
                Map<String, Double> imageColours = new HashMap<>();

                // read Annotation Labels and add to Image Labels
                for (EntityAnnotation annotation : res.getLabelAnnotationsList()) {
                    imageLabels.put(annotation.getDescription(), annotation.getScore());
                }

                // read Web Entities and add to Image Labels
                WebDetection webDet = res.getWebDetection();
                for (WebDetection.WebEntity entity : webDet.getWebEntitiesList()) {
                    imageLabels.put(entity.getDescription(), entity.getScore());
                }

                // read Dominant Colours and add to Image Colours
                DominantColorsAnnotation domColours = res.getImagePropertiesAnnotation().getDominantColors();
                for (ColorInfo colour : domColours.getColorsList()) {
                    System.out.printf("Colour R=%.2f, G=%.2f, B=%.2f: %.3f percent of image\n",
                            colour.getColor().getRed(), colour.getColor().getGreen(), colour.getColor().getBlue(), colour.getScore());
                }

                // interpret the Labels and Colours of an image
                LabelDecoder labelDecoder = new LabelDecoder();
                labelDecoder.decode(imageLabels);
            }
        }
    }
}
