package com.example.deepa.flashlight;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.hardware.Camera;

import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;

public class flashon extends AppCompatActivity {
    private static final String TAG = "flashon";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this ,R.color.grey));
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);}
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flashon);

        final CoordinatorLayout coordinatorLayout = (CoordinatorLayout) findViewById(R.id.layout2);

        final ImageButton Flash = (ImageButton) findViewById(R.id.button2);
        Flash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                coordinatorLayout.callOnClick();
            }
        });

        coordinatorLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    CameraManager camManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
                    try {
                        if(camManager != null){
                        String cameraId = camManager.getCameraIdList()[0]; // Usually front camera is at 0 position.
                        camManager.setTorchMode(cameraId, false);
                        camManager=null;}

                    } catch (CameraAccessException e) {
                        e.printStackTrace();
                    }
                    finishAfterTransition();
                }

            }
        });

        final Toolbar ntoolbar = (Toolbar) findViewById(R.id.toolbar1);
        ntoolbar.setTitle("FlashLight");
        ntoolbar.setTitleTextColor(getResources().getColor(R.color.colorPrimary));

        FloatingActionButton floatingActionButton = findViewById(R.id.fabb);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    coordinatorLayout.cancelPendingInputEvents();
                    Intent intent = new Intent(flashon.this,screenBrightness.class);
                    startActivity(intent);
                }

            }
        });


    }
    public void  onsharedtransitionfab(View view){
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            View floatingAction= view.findViewById(R.id.fabb);

            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this,floatingAction,"transitionfab1");
            Intent intent = new Intent(flashon.this,flashon.class);
            startActivity(intent,options.toBundle());
        }}


        @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}
