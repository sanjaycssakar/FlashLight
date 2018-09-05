package com.deepak.flashlight;

import android.Manifest;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.Loader;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import com.deepak.flashlight.R;


public  class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {
    private android.support.v7.view.ActionMode actionMode1 = null;
    public static CameraManager camManager = null;

    public static Camera mCamera = null;// has to be static, otherwise onDestroy() destroys it
    public static boolean flash = false;
    private static final String TAG = "FlashApp";
    public FloatingActionButton floatingActionButton;
    public ImageButton Flash;
public static boolean flashlifgt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.colorPrimary));
        }
        flash = false;
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_main);





        final ImageButton Flash = (ImageButton) findViewById (R.id.button);
          permission ();
        Log.w (TAG, "onCreate: " + permission ());
        cameraHardware ();

        final CoordinatorLayout coordinatorLayout = (CoordinatorLayout) findViewById (R.id.layout);


        final Toolbar ntoolbar = (Toolbar) findViewById (R.id.toolbar);
        ntoolbar.setTitle ("FlashLight");


        Flash.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                coordinatorLayout.callOnClick ();
            }
        });

        //screeen flash floating button
        FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById (R.id.fabd);
        floatingActionButton.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    coordinatorLayout.cancelPendingInputEvents ();
                }
                Intent intent = new Intent (MainActivity.this, screenBrightness.class);
                startActivity (intent);
            }
        });


        //flash button
        coordinatorLayout.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                flashlifgt=permission ();
                if (flashlifgt) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        try {
                            camManager = (CameraManager) getSystemService (Context.CAMERA_SERVICE);
                            runbackgroun ();


                        } catch (Exception e) {
                            alert ();
                        } finally {
                            onsharedtransition (v);
                        }


                    } else {
                        Log.d (TAG, "onClick: " + flash);
                        if (!flash) {

                            runbackgroun ();
                            Flash.setBackground (getResources ().getDrawable (R.drawable.flashonl));
                            coordinatorLayout.setBackgroundColor (getResources ().getColor (R.color.grey));

                        } else if (flash) {
                            runbackgroun ();
                            Flash.setBackground (getResources ().getDrawable (R.drawable.flashoffl));
                            coordinatorLayout.setBackgroundColor (getResources ().getColor (R.color.colorPrimary));
                            Log.d (TAG, "onClick: flash id offfffffffffff" + flash);

                        }
                    }
                } else {
                   cameraalert ();
                }
            }
        });


    }

    public boolean permission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestpermission();
        }
        else flashlifgt=true;

            return flashlifgt;
    }


    public void cameraHardware() {
        try {
            if (getApplicationContext ().getPackageManager ().hasSystemFeature (PackageManager.FEATURE_CAMERA_FLASH)) {
            } else {
                alertcamera ();
            }
        } catch (Exception e) {
            e.printStackTrace ();
        }

    }

    private android.support.v7.view.ActionMode.Callback mAction = new android.support.v7.view.ActionMode.Callback () {
        @Override
        public boolean onCreateActionMode(android.support.v7.view.ActionMode actionMode, Menu menu) {
            MenuInflater menuInflater = actionMode.getMenuInflater ();
            menuInflater.inflate (R.menu.menu, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(android.support.v7.view.ActionMode actionMode, Menu menu) {
            actionMode.setTitle ("flashhh");
            return true;
        }

        @Override
        public boolean onActionItemClicked(android.support.v7.view.ActionMode actionMode, MenuItem menuItem) {
            return false;
        }

        @Override
        public void onDestroyActionMode(android.support.v7.view.ActionMode actionMode) {
            actionMode1 = null;
        }
    };

    public void onsharedtransition(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            View floatingAction = view.findViewById (R.id.fabd);

            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation (this, floatingAction, "transitionfab1");
            Intent intent = new Intent (MainActivity.this, flashon.class);
            startActivity (intent, options.toBundle ());
        }

    }


    public void alert() {
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder (MainActivity.this, R.style.Alert);
        } else {
            builder = new AlertDialog.Builder (MainActivity.this);
        }
        builder.setTitle ("Camera App is Busy")
                .setMessage ("Camera Service is used by some other app, Please try again")
                .setPositiveButton (android.R.string.ok, new DialogInterface.OnClickListener () {
                    public void onClick(DialogInterface dialog, int which) {
                        finish ();
                    }
                })
                .setIcon (R.drawable.alertwar).create ()
                .show ();
    }

    public void alertcamera() {
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder (MainActivity.this, R.style.Alert);
        } else {
            builder = new AlertDialog.Builder (MainActivity.this);
        }
        builder.setTitle ("Flash Device Found")
                .setMessage ("Sorry, Your Mbile does not have a flash. Dont Worry Check Out The Screen Flash Feature")
                .setPositiveButton (android.R.string.ok, new DialogInterface.OnClickListener () {
                    public void onClick(DialogInterface dialog, int which) {
                        finish ();
                    }
                })
                .setIcon (R.drawable.alertwar).create ()
                .show ();
    }


    public void cameraalert() {
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder (MainActivity.this, R.style.Alert);
        } else {
            builder = new AlertDialog.Builder (MainActivity.this);
        }
        builder.setTitle ("Permission needed!")
                .setMessage ("Camera permission is needed to access Flash")
                .setPositiveButton (android.R.string.ok, new DialogInterface.OnClickListener () {
                    public void onClick(DialogInterface dialog, int which) {
                        permission ();
                    }
                })
                .setIcon (R.drawable.alertwar).create ()
                .show ();

    }

    public static class start {


        public void startflash() {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                try {
                    if (camManager != null) {
                        String cameraId = camManager.getCameraIdList ()[0]; // Usually front camera is at 0 position.
                        camManager.setTorchMode (cameraId, true);
                    }

                } catch (CameraAccessException e) {
                    e.printStackTrace ();

                }


            }
        }


        public void startlolflash() {
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
                mCamera = null;

            } catch (Exception e) {
                e.printStackTrace ();
            }
        }


    }

    public void runbackgroun() {
        getSupportLoaderManager ().initLoader (0, null, this).forceLoad ();
    }

    @NonNull
    @Override
    public Loader<String> onCreateLoader(int i, @Nullable Bundle bundle) {
        return new Flashtask (this);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String o) {
        Log.d (TAG, "onLoadFinished: ---------------");
    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy ();
        Log.d (TAG, "onDestroy: ------------------");


    }

    private static class Flashtask extends AsyncTaskLoader<String> {

        private Flashtask(@NonNull Context context) {

            super (context);
        }

        @RequiresApi(api = Build.VERSION_CODES.M)
        @Nullable
        @Override
        public String loadInBackground() {
            start start = new start ();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                start.startflash ();
            } else {
                if (!flash) {
                    start.startlolflash ();
                    flash = true;
                } else {
                    start.cameraoff ();
                    flash = false;
                }
                Log.d (TAG, "loadInBackground: +++++++++++++++++");
            }
            return "flash on";

        }


    }


public void requestpermission(){
    if (ContextCompat.checkSelfPermission (MainActivity.this, Manifest.permission.CAMERA)
            == PackageManager.PERMISSION_DENIED) {
        Log.d (TAG, "permission: is not given");
        ActivityCompat.requestPermissions (MainActivity.this, new String[]{Manifest.permission.CAMERA}, 100);

    } else {
        flashlifgt = true;
        Log.d (TAG, "permission: permission got");
    }
}






    @Override
    protected void onPause() {
        super.onPause ();

    }

    @Override
    protected void onResume() {
        super.onResume ();

    }

}