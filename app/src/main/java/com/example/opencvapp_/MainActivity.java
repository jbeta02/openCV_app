package com.example.opencvapp_;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.pm.PackageManager;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.CameraBridgeViewBase.*;
import org.opencv.android.JavaCamera2View;
import org.opencv.android.JavaCameraView;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;
import org.w3c.dom.Text;

import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements CvCameraViewListener2 {
    public static final String TAG = "MainAct_openCv";

    private RingDetector ringDetector;

    private ConstraintLayout layout;
    private TextView textView;

    private CameraBridgeViewBase mOpenCvCameraView;

    private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            switch (status) {
                case LoaderCallbackInterface.SUCCESS:
                    Log.i(TAG, "OpenCV loaded successfully");
                    mOpenCvCameraView.enableView();
                    break;
                default:
                    super.onManagerConnected(status);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        setContentView(R.layout.activity_main);

        layout = (ConstraintLayout) findViewById(R.id.layout);
        mOpenCvCameraView = (CameraBridgeViewBase) findViewById(R.id.cameraViewId);
        textView = (TextView) findViewById(R.id.lowerHueView);

        mOpenCvCameraView.setVisibility(SurfaceView.VISIBLE);
        mOpenCvCameraView.setCvCameraViewListener(this);

        mOpenCvCameraView.setCameraPermissionGranted();

        ringDetector = new RingDetector();

        textView.setText(Double.toString(ringDetector.getLowerHue()));
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mOpenCvCameraView != null){
            mOpenCvCameraView.disableView();
            mOpenCvCameraView.setVisibility(SurfaceView.INVISIBLE);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!OpenCVLoader.initDebug()) {
            Log.d(TAG, "Internal OpenCV library not found. Using OpenCV Manager for initialization");
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_4_0, this, mLoaderCallback);
        } else {
            Log.d(TAG, "OpenCV library found inside package. Using it!");
            mLoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS);
        }
    }

    public void onDestroy() {
        super.onDestroy();
        if (mOpenCvCameraView != null){
            mOpenCvCameraView.disableView();
            mOpenCvCameraView.setVisibility(SurfaceView.INVISIBLE);
        }
    }

    @Override
    public void onCameraViewStarted(int width, int height) {
        // init mats
    }

    @Override
    public void onCameraViewStopped() {
        // release mats
    }

    @Override
    public Mat onCameraFrame(CvCameraViewFrame inputFrame) {
        // do work here

        Mat img = inputFrame.rgba();

        Log.i(TAG, "onCameraFrame");

        ringDetector.processImg(img);

        return img;
    }

    public void increaseHue(View view) {
        ringDetector.increaseHue();
        textView.setText(Double.toString(ringDetector.getLowerHue()));
    }

    public void decreaseHue(View view) {
        ringDetector.decreaseHue();
        textView.setText(Double.toString(ringDetector.getLowerHue()));
    }
}