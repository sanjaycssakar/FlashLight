package com.deepak.flashlight;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.hardware.Camera;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;

import com.deepak.flashlight.R;

public class flashon extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {
    private static final String TAG = "flashon";
    public static CameraManager camManager=null ;
    public static Camera mCamera = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow ().setStatusBarColor (ContextCompat.getColor (this, R.color.grey));
            getWindow ().requestFeature (Window.FEATURE_CONTENT_TRANSITIONS);
        }
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_flashon);

        final CoordinatorLayout coordinatorLayout = (CoordinatorLayout) findViewById (R.id.layout2);

        final ImageButton Flash = (ImageButton) findViewById (R.id.button2);
        Flash.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                coordinatorLayout.callOnClick ();
            }
        });

        coordinatorLayout.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {


                //  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    camManager = (CameraManager) getSystemService (Context.CAMERA_SERVICE);
                    runbackgroun();
                    finishAfterTransition ();
                }
                else{
                    runbackgroun();
                    finish ();
                }
            }

            // }
        });

        final Toolbar ntoolbar = (Toolbar) findViewById (R.id.toolbar1);
        ntoolbar.setTitle ("FlashLight");
        ntoolbar.setTitleTextColor (getResources ().getColor (R.color.colorPrimary));

        FloatingActionButton floatingActionButton = findViewById (R.id.fabb);
        floatingActionButton.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    coordinatorLayout.cancelPendingInputEvents ();
                    Intent intent = new Intent (flashon.this, screenBrightness.class);
                    startActivity (intent);
                }

            }
        });


    }




    @Override
    public void onBackPressed() {
        moveTaskToBack (true);
    }

    public void runbackgroun() {
        getSupportLoaderManager ().initLoader (0, null, this).forceLoad ();
    }

    public static class stop {
        @TargetApi(Build.VERSION_CODES.M)
        public void stopflash() {
            try {
                if (camManager != null) {
                    String cameraId = camManager.getCameraIdList ()[0]; // Usually front camera is at 0 position.
                    camManager.setTorchMode (cameraId, false);
                    camManager = null;
                }

            } catch (CameraAccessException e) {
                e.printStackTrace ();
            }
        }

        public void stopflashlol(){
            try {
                Camera.Parameters parameters = mCamera.getParameters ();
                parameters.setFlashMode (Camera.Parameters.FLASH_MODE_OFF);
                mCamera.setParameters (parameters);
                mCamera.stopPreview ();
                mCamera.release ();
                mCamera = null;

            } catch (Exception e) {
                e.printStackTrace ();
            }
        }


    }


    @NonNull
    @Override
    public Loader<String> onCreateLoader(int i, @Nullable Bundle bundle) {
        return new Flashtask (this);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String o) {

    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {

    }



    private static class Flashtask extends AsyncTaskLoader<String> {

        public Flashtask(@NonNull Context context) {
            super (context);
        }

        @Nullable
        @Override
        public String loadInBackground() {
            stop stop = new stop ();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                stop.stopflash ();
            }
            else{
                stop.stopflashlol ();
            }
            return "flashoff";
        }
    }
}
