package com.cafe.recognition;

import com.cafe.utils.Resource;
import org.apache.commons.io.FileUtils;
import org.bytedeco.javacpp.Loader;
import org.bytedeco.opencv.opencv_java;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.Videoio;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Recorder {
    public static final int NUMBER_OF_PICTURES = 40;
    public static final int CAPTURE_INTERVAL = 500;
    static final String FACE_DIRECTORY = "img/faces";
    private Camera camera;
    private Detector detector;
    private Mat grayROI;
    private int count;

    public Recorder(String cameraName) {
        camera = new Camera(cameraName);
        detector = new Detector();
        try {
            Files.createDirectories(Paths.get(Resource.getResourceOutsideJAR(FACE_DIRECTORY)));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public Camera getCamera() {
        return camera;
    }

    public void setCamera(Camera camera) {
        this.camera = camera;
    }

    public Detector getDetector() {
        return detector;
    }

    public void setDetector(Detector detector) {
        this.detector = detector;
    }

    public Mat getGrayROI() {
        return grayROI;
    }

    public void setGrayROI(Mat grayROI) {
        this.grayROI = grayROI;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public static void main(String[] args) {
        Recorder recorder = new Recorder("Camera");
        recorder.record("CUS001");
        Trainer trainer = new Trainer();
        trainer.train("CUS001", 80);
        System.out.println("SUCCESS");
    }

    public void takePictures(String customerID) {
        String imageDirPath = new File(Resource.getResourceOutsideJAR(FACE_DIRECTORY), customerID).getPath();
        try {
            FileUtils.deleteDirectory(new File(imageDirPath));
            Files.createDirectories(Paths.get(imageDirPath));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        count = 0;
        while (camera.isActive() && count < NUMBER_OF_PICTURES) {
            try {
                Thread.sleep(CAPTURE_INTERVAL);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
            if (grayROI != null) {
                String filename = String.format("%s/%03d.jpg", imageDirPath, count);
                Imgproc.resize(grayROI, grayROI, new Size(224, 224));
                Imgcodecs.imwrite(filename, grayROI);
                count++;
            }
        }
        camera.setActive(false);
    }

    public void record(String customerID) {
        camera.open(Videoio.CAP_ANY);
        new Thread(() -> takePictures(customerID)).start();
        String text;
        while (camera.isActive()) {
            camera.read(true);
            if (count < NUMBER_OF_PICTURES / 4)
                text = "Nhin len";
            else if (count < NUMBER_OF_PICTURES / 2)
                text = "Nhin xuong";
            else if (count < NUMBER_OF_PICTURES * 3 / 4)
                text = "Nhin trai";
            else
                text = "Nhin phai";
            detector.detect(camera.getImage(), 1.3, 5);
            detector.draw(camera.getImage(), text);
            grayROI = detector.getGrayROI();
            camera.showImage();
        }
        camera.close();
    }

    static {
        Loader.load(opencv_java.class);
    }
}
