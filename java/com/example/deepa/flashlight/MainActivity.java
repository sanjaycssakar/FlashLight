package com.example.deepa.flashlight;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.content.pm.PackageManager;

import android.os.Build;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewCompat;
import android.support.v7.view.ActionMode;
import android.support.v4.app.ActivityCompat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.Toolbar;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;

import static android.support.v4.view.ViewCompat.setTransitionName;

public  class MainActivity extends AppCompatActivity {
    private android.support.v7.view.ActionMode actionMode1 = null;
    private final Flash flashes = new Flash();
    public static boolean flash;
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

                if (flashlifgt) {
                   flashes.on();
                    onsharedtransition(v);
                }
            }
        });

        Toolbar ntoolbar = (Toolbar) findViewById(R.id.toolbar);
        ntoolbar.setTitle("FlashLight");


        Flash.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (actionMode1 != null) {
                    return false;
                }
                actionMode1 = startSupportActionMode(mAction);
                return true;
            }
        });


    }

    @TargetApi(Build.VERSION_CODES.M)
    public boolean permission() {
        boolean permission = false;
        if (android.os.Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CAMERA}, 11);

                return permission = true;
            }
        } else {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CAMERA}, 11);
            return permission = true;
        }
        return permission;
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


}