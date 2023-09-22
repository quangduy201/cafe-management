package com.cafe.recognition;

import com.cafe.BLL.CustomerBLL;
import com.cafe.DTO.Customer;
import com.cafe.utils.Resource;
import org.opencv.core.Rect;
import org.opencv.videoio.Videoio;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

public class Scanner {
    private Camera camera;
    private Detector detector;
    private Map<String, Recognizer> recognizers;

    public Scanner(String cameraName) {
        camera = new Camera(cameraName);
        detector = new Detector();
        recognizers = new HashMap<>();
        try {
            Files.createDirectories(Paths.get(Resource.getResourcePathOutsideJAR(Recorder.FACE_DIRECTORY)));
        } catch (IOException e) {
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

    public Map<String, Recognizer> getRecognizers() {
        return recognizers;
    }

    public void setRecognizers(Map<String, Recognizer> recognizers) {
        this.recognizers = recognizers;
    }

    public void loadModels() {
        File[] files = new File(Objects.requireNonNull(Resource.getResourcePathOutsideJAR(Trainer.CLASSIFIER_DIRECTORY))).listFiles();
        assert files != null;
        for (File file : files) {
            String customerID = file.getName();
            Recognizer recognizer = new Recognizer();
            recognizer.loadModel(file);
            recognizers.put(customerID, recognizer);
        }
    }

    public Customer getTheClosestCustomer(Rect rect, double confidence) {
        String label = "";
        double minimum = 100;
        for (Map.Entry<String, Recognizer> entry : recognizers.entrySet()) {
            Recognizer recognizer = entry.getValue();
            double conf = recognizer.recognize(detector.getGray().submat(rect));
            if (conf < confidence && conf < minimum) {
                label = entry.getKey();
                minimum = conf;
            }
        }
        if (label.equals("")) {
            Recognizer.draw(camera.getImage(), rect);
            return null;
        }
        String customerID = label.split("\\.")[0];
        Customer customer = new CustomerBLL().findCustomersBy(Map.of("CUSTOMER_ID", customerID)).get(0);
        Recognizer.draw(camera.getImage(), rect, customer);
        return customer;
    }

    public boolean scan(double confidence, Function<Customer, Void> function) {
        camera.open(Videoio.CAP_ANY);
        loadModels();

        boolean found = false;
        Customer customer;
        while (camera.isActive()) {
            camera.read(true);
            detector.detect(camera.getImage(), 1.3, 5);
            detector.draw(camera.getImage());

            for (Rect rect : detector.getFaces().toArray()) {
                customer = getTheClosestCustomer(rect, confidence);
                if (customer != null) {
                    function.apply(customer);
                    found = true;
                }
            }
            camera.showImage();
        }
        camera.close();
        return found;
    }
}
