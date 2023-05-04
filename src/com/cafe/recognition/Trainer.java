package com.cafe.recognition;

import org.opencv.core.Mat;
import org.opencv.core.MatOfInt;
import org.opencv.imgcodecs.Imgcodecs;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Trainer {
    public static final String FACE_DIRECTORY = "img/faces";
    public static final String CLASSIFIER_DIRECTORY = "classifiers";
    Detector detector;
    Recognizer recognizer;

    public Trainer() {
        detector = new Detector();
        recognizer = new Recognizer();
        try {
            Files.createDirectories(Paths.get(CLASSIFIER_DIRECTORY));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public Detector getDetector() {
        return detector;
    }

    public void setDetector(Detector detector) {
        this.detector = detector;
    }

    public Recognizer getRecognizer() {
        return recognizer;
    }

    public void setRecognizer(Recognizer recognizer) {
        this.recognizer = recognizer;
    }

    public static void main(String[] args) {
        Trainer trainer = new Trainer();
        trainer.train("CUS001", 80);
        System.out.println("DONE");
    }

    public void train(String customerID, double percentToSuccess) {
        String imageDirPath = new File(FACE_DIRECTORY, customerID).getPath();
        List<Mat> faceSamples = new ArrayList<>();
        List<Integer> labels = new ArrayList<>();
        List<Integer> errorImages = new ArrayList<>();
        int numberOfImages = Objects.requireNonNull(new File(imageDirPath).list()).length;
        for (int i = 0; i < numberOfImages; i++) {
            String imagePath = new File(imageDirPath, String.format("%03d.jpg", i)).getPath();
            Mat image = Imgcodecs.imread(imagePath, Imgcodecs.IMREAD_GRAYSCALE);
            int label = Integer.parseInt(customerID.substring(3));
            detector.getClassifier().detectMultiScale(image, detector.getFaces());
            if (detector.getFaces().toArray().length != 1) {
                errorImages.add(i);
                continue;
            }
            faceSamples.add(image);
            labels.add(label);
        }
        System.out.printf("Trained %d/%d images.%n", faceSamples.size(), numberOfImages);
        if (faceSamples.size() == 0 || faceSamples.size() < numberOfImages * percentToSuccess / 100) {
            System.out.println("Can't train the model. Please sign up your face again.");
            return;
        } else if (faceSamples.size() != numberOfImages) {
            System.out.println("Error images: " + errorImages);
        }
        MatOfInt labelsMat = new MatOfInt();
        labelsMat.fromList(labels);
        recognizer.model.train(faceSamples, labelsMat);
        recognizer.model.save(new File(CLASSIFIER_DIRECTORY, customerID + ".xml").getPath());
    }

    static {
        System.loadLibrary("libopencv_java470");
    }
}