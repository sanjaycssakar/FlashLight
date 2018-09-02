package com.example.deepa.flashlight;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;

public class flashon extends AppCompatActivity {
    private final Flash flashes = new Flash();
private final MainActivity flash= new MainActivity();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
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
                flashes.off();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
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
