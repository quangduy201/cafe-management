package com.cafe.recognition;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Camera extends JFrame {
    public static final int CAMERA_WIDTH = 640;
    public static final int CAMERA_HEIGHT = 480;
    private VideoCapture videoCapture;
    private Mat image;
    private JLabel currentFrame;
    private boolean active;
    private boolean loading;

    public Camera(String title) {
        super(title);
        videoCapture = new VideoCapture();
        image = new Mat();
        currentFrame = new JLabel();
        active = false;
        loading = false;
        setSize(CAMERA_WIDTH, CAMERA_HEIGHT);
        setLocationRelativeTo(null);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
//                int message = JOptionPane.showConfirmDialog(null, "Do you want to close?\nThe model won't be trained.", "Exit", JOptionPane.YES_NO_OPTION);
//                if (message == JOptionPane.YES_OPTION)
                    active = false;
            }
        });
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyChar() == 'q') {
//                    int message = JOptionPane.showConfirmDialog(null, "Do you want to close?\nThe model won't be trained.", "Exit", JOptionPane.YES_NO_OPTION);
//                    if (message == JOptionPane.YES_OPTION)
                        active = false;
                }
            }
        });
    }

    public Mat getImage() {
        return image;
    }

    public void setImage(Mat image) {
        this.image = image;
    }

    @Override
    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void open(int index) {
        videoCapture.open(index);
        if (!videoCapture.isOpened()) {
            System.out.println("Failed to open the camera.");
            return;
        }
        getContentPane().add(new JLabel(new ImageIcon("img/icons/loading.gif")));
        setVisible(true);
        active = true;
        loading = true;
    }

    public void read(boolean flip) {
        videoCapture.read(image);
        Core.flip(image, image, flip ? 1 : 0);
    }

    public void showImage() {
        Imgproc.resize(image, image, new Size(CAMERA_WIDTH, CAMERA_HEIGHT));
        MatOfByte m = new MatOfByte();
        Imgcodecs.imencode(".jpg", image, m);
        try {
            if (loading) {
                loading = false;
                getContentPane().removeAll();
                getContentPane().add(currentFrame);
            }
            currentFrame.setIcon(new ImageIcon(m.toArray()));
            pack();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void close() {
        loading = true;
        videoCapture.release();
        dispose();
    }
}
