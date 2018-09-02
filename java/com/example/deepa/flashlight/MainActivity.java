package com.example.deepa.flashlight;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.pm.PackageManager;

import android.os.Build;
import android.support.design.widget.Snackbar;
import android.support.v7.view.ActionMode;
import android.support.v4.app.ActivityCompat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public  class MainActivity extends AppCompatActivity {
    Button Flash;
    private android.support.v7.view.ActionMode actionMode1 = null;
    private final Flash flashes = new Flash();
    public boolean flash;
    private static final String TAG = "FlashApp";

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        flash = false;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Flash = (Button) findViewById(R.id.button);
        Flash.setText("Turn on");
        final boolean flashlifgt = permission();
        cameraHardware();

        Flash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (flashlifgt) {
                    if (!flash) {
                        flashes.on();
                        Flash.setText("Turn OFF");
                        flash = true;
                    Snackbar snackbar = Snackbar.make(v,"flash id on",Snackbar.LENGTH_LONG);
                    snackbar.show();
                    snackbar.setAction("UNdO", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            flashes.off();
                            Flash.setText("Turn ON");
                            flash = false;
                            Snackbar.make(v,"flash is oFF",Snackbar.LENGTH_SHORT).show();

                        }
                    });

                    } else {
                        flashes.off();
                        Flash.setText("Turn ON");
                        flash = false;
                        Snackbar.make(v,"flash is oFF",Snackbar.LENGTH_LONG).show();

                    }
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


}