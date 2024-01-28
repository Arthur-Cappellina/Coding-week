package eu.telecomnancy.codingweek;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Helper {

    public static String generateUniqueFileName() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String timestamp = dateFormat.format(new Date());
        return "image_" + timestamp + ".png";
    }

    public static FileChooser getImagesFileChooser() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(getExtensionFiltersImages());
        return fileChooser;
    }

    private static FileChooser.ExtensionFilter[] getExtensionFiltersImages() {
        return new FileChooser.ExtensionFilter[] {
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"),
                new FileChooser.ExtensionFilter("All Files", "*.*")
        };
    }

    public static String saveImage(File selectedFile){
        // Si le dossier image n'existe pas, on le crée
        Path img = Paths.get("img");
        if(!Files.exists(img)){
            try {
                Files.createDirectory(img);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        String currentDirectory = System.getProperty("user.dir") + "/img";
        try {
            String fileName = Helper.generateUniqueFileName();
            Path targetPath = Paths.get(currentDirectory, fileName);

            Files.copy(selectedFile.toPath(), targetPath, StandardCopyOption.REPLACE_EXISTING);
            return fileName;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Image getImage(String fileName) {
        String currentDirectory = System.getProperty("user.dir") + "/img";
        Path targetPath = Paths.get(currentDirectory, fileName);
        return new Image(targetPath.toUri().toString());
    }


    public static HBox cloneHbox(HBox originalModel) {
        HBox clonedModel = new HBox();
        List<Node> originalChildren = new ArrayList<>(originalModel.getChildren());
        for (Node node : originalChildren) {
            Node clonedNode = cloneNode(node);
            clonedModel.getChildren().add(clonedNode);
        }

        return clonedModel;
    }

    public static VBox cloneVbox(VBox originalModel) {
        VBox clonedModel = new VBox();
        List<Node> originalChildren = new ArrayList<>(originalModel.getChildren());

        for (Node node : originalChildren) {
            Node clonedNode = cloneNode(node);
            clonedModel.getChildren().add(clonedNode);
        }

        return clonedModel;
    }

    public static Node cloneNode(Node originalNode) {
        if (originalNode instanceof HBox) {
            HBox clonedNode = new HBox();
            // Copy other necessary properties
            clonedNode.setPrefSize(((HBox) originalNode).getPrefWidth(), ((HBox) originalNode).getPrefHeight());
            // Copy children if needed
            clonedNode.getChildren().addAll(((HBox) originalNode).getChildren());
            return clonedNode;
        } else if (originalNode instanceof VBox) {
            VBox clonedNode = new VBox();
            clonedNode.setPrefSize(((VBox) originalNode).getPrefWidth(), ((VBox) originalNode).getPrefHeight());
            clonedNode.getChildren().addAll(((VBox) originalNode).getChildren());
            return clonedNode;
        } else if (originalNode instanceof ImageView) {
            // Handle ImageView case or throw an exception if necessary
            ImageView clonedImageView = new ImageView(((ImageView) originalNode).getImage());
            return clonedImageView;
        } else if (originalNode instanceof Label) {
            Label clonedLabel = new Label(((Label) originalNode).getText());
            return clonedLabel;
        } else if (originalNode instanceof Button) {
            Button clonedButton = new Button(((Button) originalNode).getText());
            return clonedButton;
        } else if(originalNode instanceof Text){
            Text clonedText = new Text(((Text) originalNode).getText());
            return clonedText;
        } else {
            throw new IllegalArgumentException("Unsupported node type: " + originalNode.getClass());
        }
    }

    public static LocalDateTime convertDate(String date) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date start = sdf.parse(date);
        return LocalDateTime.ofInstant(start.toInstant(), sdf.getTimeZone().toZoneId());
    }

    public static String convertDate(LocalDateTime date) {
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        String v =  sdf.format(date);
//        return v;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return date.format(formatter);
    }

    public static String convertDateTime(String dateString) throws ParseException {
        // Parse la chaîne de caractères en LocalDateTime
        LocalDateTime date = LocalDateTime.parse(dateString, DateTimeFormatter.ISO_DATE_TIME);

        // Formatte la date en utilisant SimpleDateFormat
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        System.out.println(date);
        return sdf.format(Date.from(date.atZone(java.time.ZoneId.systemDefault()).toInstant()));
    }

    public static String convertDateTime(LocalDateTime date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        String formattedDate = date.format(formatter);
        return formattedDate;
    }

    public static LocalDateTime convertDateTimeToDate(String date) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        Date start = sdf.parse(date);
        return LocalDateTime.ofInstant(start.toInstant(), sdf.getTimeZone().toZoneId());
    }

    public static LocalDateTime convertDateTimeNow() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        Date start = new Date();
        return LocalDateTime.ofInstant(start.toInstant(), sdf.getTimeZone().toZoneId());
    }

    public static boolean checkDates(LocalDateTime start, LocalDateTime end) {

        if (end.isBefore(start)) {
            return false;
        }

        return true;
    }
}
