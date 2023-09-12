package com.cafe.recognition;

import com.cafe.DTO.Customer;
import com.cafe.utils.VNString;
import org.opencv.core.*;
import org.opencv.face.LBPHFaceRecognizer;
import org.opencv.imgproc.Imgproc;

import java.io.File;

public class Recognizer {
    LBPHFaceRecognizer model;

    public Recognizer() {
        model = LBPHFaceRecognizer.create();
    }

    public LBPHFaceRecognizer getModel() {
        return model;
    }
    public void setModel(LBPHFaceRecognizer model) {
        this.model = model;
    }

    public void loadModel(File file) {
        model.read(file.getAbsolutePath());
        System.out.println(model.getClass());
    }

    public double recognize(Mat image) {
        int[] label = new int[1];
        double[] confidence = new double[1];
        Imgproc.resize(image, image, new Size(224, 224));
//        image.convertTo(image, CvType.CV_32FC1);
        model.predict(image, label, confidence);
        return confidence[0];
    }

    public static void draw(Mat image, Rect rect) {
        Point pos = new Point(rect.x + 10, rect.y + rect.height + 40);
        Imgproc.putText(image, "unknown", pos, Imgproc.FONT_HERSHEY_SIMPLEX, 1, new Scalar(36, 255, 255), 2);
    }

    public static void draw(Mat image, Rect rect, String text) {
        Imgproc.rectangle(image, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height), new Scalar(0, 255, 0), 2);
        Point pos = new Point(rect.x + 10, rect.y + rect.height + 40);
        Imgproc.putText(image, text, pos, Imgproc.FONT_HERSHEY_SIMPLEX, 1, new Scalar(36, 255, 12), 2);
    }

    public static void draw(Mat image, Rect rect, Customer customer) {
        Imgproc.rectangle(image, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height), new Scalar(0, 255, 0), 2);
        Point pos;
        pos = new Point(rect.x + 10, rect.y + rect.height - 10);
        Imgproc.putText(image, customer.getCustomerID(), pos, Imgproc.FONT_HERSHEY_SIMPLEX, 1, new Scalar(36, 255, 12), 2);
        pos = new Point(rect.x + 10, rect.y + rect.height + 40);
        Imgproc.putText(image, VNString.removeAccent(customer.getName()), pos, Imgproc.FONT_HERSHEY_SIMPLEX, 1, new Scalar(36, 255, 12), 2);
    }
}
