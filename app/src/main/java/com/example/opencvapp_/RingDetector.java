package com.example.opencvapp_;

import android.nfc.Tag;
import android.util.Log;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.List;

public class RingDetector {

    private double lowerBound = 100;
    private double upperBound = 120;

    private double lowerValue = 30;
    private double upperValue = 255;

    private ArrayList<Rect> rects = new ArrayList<>(2);

    private int ringCount;
    private int height = 0;


    public RingDetector(){
    }

    public void releaseMats(){
    }

    public void increaseHue(){
        upperValue += 10;
    }

    public void decreaseHue(){
        upperValue -= 10;
    }

    public double getLowerHue(){
        return upperValue;
    }

    public void processImg(Mat rawImg){
        Mat modifiedImg = new Mat();

        Scalar lowerBound = new Scalar(this.lowerBound, 25, this.lowerValue);
        Scalar upperBound = new Scalar(this.upperBound, 255, this.upperValue);

        List<MatOfPoint> contours = new ArrayList<>();

        Imgproc.cvtColor(rawImg, modifiedImg, Imgproc.COLOR_BGR2HSV);

        Core.inRange(modifiedImg, lowerBound, upperBound, modifiedImg);

        Imgproc.erode(modifiedImg, modifiedImg, Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(8,8)));

        Imgproc.findContours(modifiedImg, contours, new Mat(), Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);
        Imgproc.drawContours(rawImg, contours, -1, new Scalar(255, 0, 0), 3);

        // fit rects
        for (MatOfPoint contour: contours){
            Rect rect = Imgproc.boundingRect(contour);
            //Imgproc.rectangle(rawImg, rect, new Scalar(255, 0, 0));
            rects.add(rect);
        }

        // look for biggest contour area
        Rect largestRect = new Rect();
        for (Rect rect: rects){
            if (rect.height > height){
                height = rect.height;
                largestRect = rect;
            }
        }
        Imgproc.rectangle(rawImg, largestRect, new Scalar(255, 0, 0));
        Log.i("imgProc: height", Integer.toString(height));


        // diff between 1, 4, and 0 rings

        /*TODO: look at how the images are taken from the robot
            then use them to deferentiate between 1, 4, and 0
         */
    }

    public int getHeight(){
        return height;
    }

}
