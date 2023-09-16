package com.cafe.recognition;

import com.cafe.utils.Resource;
import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

public class Detector {
    public static final String HAAR_CASCADE_PATH = "haarcascade_frontalface_default.xml";
    private CascadeClassifier classifier;
    private MatOfRect faces;
    private Mat gray;
    private Mat grayROI;

    public Detector() {
        try {
            Files.createDirectories(Paths.get(Resource.getPathOutsideJAR("resources")));
            Path haar = Path.of(Resource.getResourceOutsideJAR(HAAR_CASCADE_PATH));
            if (Files.notExists(haar)) {
                InputStream inputStream = Resource.loadInputStream(HAAR_CASCADE_PATH);
                if (inputStream != null)
                    Files.copy(inputStream, haar);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        classifier = new CascadeClassifier();
        classifier.load(Resource.getResourceOutsideJAR(HAAR_CASCADE_PATH));
        faces = new MatOfRect();
        gray = new Mat();
    }

    public CascadeClassifier getClassifier() {
        return classifier;
    }

    public MatOfRect getFaces() {
        return faces;
    }

    public Mat getGray() {
        return gray;
    }

    public Mat getGrayROI() {
        return grayROI;
    }

    public void detect(Mat image, double scaleFactor, int minNeighbors) {
        Imgproc.cvtColor(image, gray, Imgproc.COLOR_BGR2GRAY);
        classifier.detectMultiScale(gray, faces, scaleFactor, minNeighbors);
    }

    public void draw(Mat image) {
        grayROI = null;
        for (Rect rect : faces.toArray()) {
            Imgproc.rectangle(image, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height), new Scalar(255, 0, 0), 2);
            grayROI = gray.submat(rect);
        }
    }

    public void draw(Mat image, String text) {
        grayROI = null;
        for (Rect rect : faces.toArray()) {
            Imgproc.rectangle(image, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height), new Scalar(255, 0, 0), 2);
            grayROI = gray.submat(rect);
            Point pos = new Point(rect.x + 10, rect.y + rect.height - 10);
            Imgproc.putText(image, text, pos, Imgproc.FONT_HERSHEY_SIMPLEX, 1, new Scalar(36, 255, 12), 2);
        }
    }
}
