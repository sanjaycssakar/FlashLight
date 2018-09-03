package com.example.deepa.flashlight;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;

import android.hardware.Camera;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v4.app.ActivityCompat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;


public  class MainActivity extends AppCompatActivity {
    private android.support.v7.view.ActionMode actionMode1 = null;

    public static Camera mCamera = null;// has to be static, otherwise onDestroy() destroys it
    public static boolean flash=false;
    private static final String TAG = "FlashApp";
    public  FloatingActionButton floatingActionButton;
public  ImageButton Flash;
    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        flash = false;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       final ImageButton Flash = (ImageButton) findViewById(R.id.button);
        final boolean flashlifgt = permission();
        Log.d (TAG, "onCreate: "+permission ());
        cameraHardware();


        final CoordinatorLayout coordinatorLayout = (CoordinatorLayout) findViewById(R.id.layout);

Flash.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
       coordinatorLayout.callOnClick();
        }
});

        //screeen flash floating button
        FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(R.id.fabd);
floatingActionButton.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        coordinatorLayout.cancelPendingInputEvents();
        Intent intent = new Intent(MainActivity.this,screenBrightness.class);
        startActivity(intent);
    }
});



        //flash button
        coordinatorLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
if(flashlifgt){
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                    CameraManager camManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);

                    try {
                        if (camManager != null) {
                            String cameraId = camManager.getCameraIdList ()[0]; // Usually front camera is at 0 position.
                            camManager.setTorchMode (cameraId, true);
                        }
                    } catch (CameraAccessException e) {
                        e.printStackTrace ();
                        alert ();

                    } finally {
                        onsharedtransition (v);

                    }

                }

                else {
                    Log.d (TAG, "onClick: "+flash);
                    if(flash==false){
                        cameraon();                    flash=true;
                        Log.d (TAG, "onClick: flash is onnnnnnnnnnnnnn"+ flash);
                    }
                  else if(flash==true) {
cameraoff ();
flash=false;
                        Log.d (TAG, "onClick: flash id offfffffffffff"+ flash);

                }
                }
            }else{
                     cameraalert();
                          Log.d (TAG, "onClick: executed");
            }
            }
        });







    }

    public boolean permission() {
        int a =1;
        boolean bool=false;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(MainActivity.this, new String[] {Manifest.permission.CAMERA}, 100);
        bool=false;
        if(a!=0){
            permission ();
            a--;
        }
        return bool;
        }else
        bool = true;
        return bool;}
        bool = true;
        return bool;
    }


    public void cameraHardware() {
        try {
            if (getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)) {
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private android.support.v7.view.ActionMode.Callback mAction = new android.support.v7.view.ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(android.support.v7.view.ActionMode actionMode, Menu menu) {
            MenuInflater menuInflater = actionMode.getMenuInflater();
            menuInflater.inflate(R.menu.menu,menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(android.support.v7.view.ActionMode actionMode, Menu menu) {
actionMode.setTitle("flashhh");
            return true;
        }

        @Override
        public boolean onActionItemClicked(android.support.v7.view.ActionMode actionMode, MenuItem menuItem) {
            return false;
        }

        @Override
        public void onDestroyActionMode(android.support.v7.view.ActionMode actionMode) {
actionMode1=null;
        }
    };

public void  onsharedtransition(View view){
    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
        View floatingAction= view.findViewById(R.id.fabd);

        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this,floatingAction,"transitionfab1");
        Intent intent = new Intent(MainActivity.this,flashon.class);
        startActivity(intent,options.toBundle());
    }

}


        public void cameraon() {
            try {
                mCamera = Camera.open ();
                Camera.Parameters parameters = mCamera.getParameters ();
                parameters.setFlashMode (Camera.Parameters.FLASH_MODE_TORCH);
                mCamera.setParameters (parameters);
                mCamera.startPreview ();
            } catch (Exception e) {
                e.printStackTrace ();
            }
        }

        public void cameraoff() {
            try {
                Camera.Parameters parameters = mCamera.getParameters ();
                parameters.setFlashMode (Camera.Parameters.FLASH_MODE_OFF);
                mCamera.setParameters (parameters);
                mCamera.stopPreview ();
                mCamera.release ();
                mCamera=null;

            } catch (Exception e) {
                e.printStackTrace ();
            }
        }




    public void alert(){
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(MainActivity.this, R.style.Alert);
        } else {
            builder = new AlertDialog.Builder(MainActivity.this);
        }
        builder.setTitle("Camera App is Busy")
                .setMessage("Camera Service is used by some other app, Please try again")
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish ();
                    }
                })
                .setIcon(R.drawable.alertwar).create ()
                .show();
    }
    public void cameraalert(){
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(MainActivity.this, R.style.Alert);
        } else {
            builder = new AlertDialog.Builder(MainActivity.this);
        }
        builder.setTitle("Permission needed!")
                .setMessage("Camera permission is needed to access Flash")
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions((Activity)MainActivity.this, new String[]{Manifest.permission.CAMERA}, 100);
                    }
                })
                .setIcon(R.drawable.alertwar).create ()
                .show();

    }





}