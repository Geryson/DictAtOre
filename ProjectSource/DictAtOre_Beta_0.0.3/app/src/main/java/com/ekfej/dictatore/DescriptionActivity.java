package com.ekfej.dictatore;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;

public class DescriptionActivity extends AppCompatActivity implements View.OnClickListener{
    Button OkBackButton;
    VideoView LearnVideo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);

        OkBackButton = (Button) findViewById(R.id.OkBackbutton);
        OkBackButton.setOnClickListener(this);
/*
        final VideoView LearnVideo = (VideoView) findViewById(R.id.learnvideo);
        LearnVideo.setVideoPath();

        MediaController mediaController = new
                MediaController(this);
        mediaController.setAnchorView(LearnVideo);
        LearnVideo.setMediaController(mediaController);


                LearnVideo.start();
*/



        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width * .8), (int) (height * .6));
        WindowManager.LayoutParams windowManager = getWindow().getAttributes();
        windowManager.dimAmount = 0.5f;
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
    }

    @Override
    public void onClick(View v) {
        if(v == OkBackButton) {
            finish();
        }

    }
}
