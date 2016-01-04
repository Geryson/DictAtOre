package com.ekfej.dictatore;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

public class DescriptionActivity extends AppCompatActivity implements View.OnClickListener{
    Button OkBackButton;
    VideoView LearnVideo;
    TextView description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);

        OkBackButton = (Button) findViewById(R.id.OkBackbutton);
        OkBackButton.setOnClickListener(this);

        description = (TextView) findViewById(R.id.decription);
        String d= "DictAtOre \n" +
                "Verzió: 1.0 \n" +
                "License: Creative Commons Attribution License \n" +
                "Leírás: Ez egy nyelvtanulás elősegítő program \n" +
                "Segítség az alábbi videóban:";
        description.setText(d);

        final VideoView LearnVideo = (VideoView) findViewById(R.id.learnvideo);

        String uriPath = "android.resource://" + getPackageName() + "/" + R.raw.learn;
        Uri uri = Uri.parse(uriPath);
        //LearnVideo.setVideoPath();

        LearnVideo.setVideoURI(uri);

        //LearnVideo.start();

        MediaController mediaController = new
                MediaController(this);
        mediaController.setAnchorView(LearnVideo);
        LearnVideo.setMediaController(mediaController);

        /*
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width * .8), (int) (height * .6));
        WindowManager.LayoutParams windowManager = getWindow().getAttributes();
        windowManager.dimAmount = 0.5f;
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        */

    }

    @Override
    public void onClick(View v) {
        if(v == OkBackButton) {
            finish();
        }

    }
}
