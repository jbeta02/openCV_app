package com.example.opencvapp_;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    ImageView imageDisplay;
    ConstraintLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        layout = (ConstraintLayout) findViewById(R.id.layout);
        imageDisplay = (ImageView) findViewById(R.id.imageDisplay);

    }

    public void displayImage(View view){
        imageDisplay.setImageResource(R.drawable.ic_launcher_background);
//        double[] Dmatrix = mat.get(1,1);
//        String matrix = Double.toString(Dmatrix[0]);
//        Log.i("openCVTest", matrix);
    }
}