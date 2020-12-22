package com.example.opencvapp_;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import org.opencv.android.OpenCVLoader;
import org.opencv.core.Mat;

public class MainActivity extends AppCompatActivity {

    ImageView imageDisplay;
    ConstraintLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        layout = (ConstraintLayout) findViewById(R.id.layout);
        imageDisplay = (ImageView) findViewById(R.id.imageDisplay);

        Log.i("init debug", Boolean.toString(OpenCVLoader.initDebug()));


    }

    public void displayImage(View view){
        imageDisplay.setImageResource(R.drawable.ic_launcher_background);

    }
}