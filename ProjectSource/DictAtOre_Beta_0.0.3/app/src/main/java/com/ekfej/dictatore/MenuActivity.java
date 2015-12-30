package com.ekfej.dictatore;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.opengl.Visibility;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.content.Intent;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Timer;

import com.ekfej.dictatore.Database.DatabaseAccess;
import com.ekfej.dictatore.Database.Elementary;

public class MenuActivity extends AppCompatActivity implements View.OnClickListener{

    Button TudastesztButton, SzotarakButton;
    ImageView progress;
    ImageButton Don;
    WebView donweb;
    Button HelpButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        progress = (ImageView) findViewById(R.id.imageView);
        Animation an = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.scale);
        progress.startAnimation(an);
        progress.setVisibility(View.INVISIBLE);

        TudastesztButton = (Button) findViewById(R.id.TudastesztButton); //nem kell újrapéldányosítani
        TudastesztButton.setOnClickListener(this);

        SzotarakButton = (Button) findViewById(R.id.SzotarakButton);
        SzotarakButton.setOnClickListener(this);

        Don = (ImageButton) findViewById(R.id.donButton);
        Don.setOnClickListener(this);

        HelpButton = (Button) findViewById(R.id.Sbutton);
        HelpButton.setOnClickListener(this);

/*
        donweb = (WebView) findViewById(R.id.donweb);
        WebSettings webSettings = donweb.getSettings();

        donweb.loadUrl("file:///android_asset/www/donate.html");


        webSettings.setBuiltInZoomControls(true);
        // Enable JavaScript
        webSettings.setJavaScriptEnabled(true);

        //Let links appear in this WebView
        donweb.setWebViewClient(new WebViewClient());
*/

    }


    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private boolean isConnectingToInternet(Context applicationContext){
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        if (ni == null) {
            // There are no active networks.
            return false;
        } else
            return true;

    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, Language_ChooseActivity.class);
        Bundle bundy = new Bundle();

        if (Don == v) {
            if(isConnectingToInternet(getApplicationContext()))   {
                Intent donIntent = new Intent(this, DonateActivity.class);
                startActivity(donIntent);
            }else{
                Toast.makeText(getApplicationContext(), "nincs internet", Toast.LENGTH_LONG).show();
            }
        }
        else {
            if (v == HelpButton) {
                Intent helpintent = new Intent(this, DescriptionActivity.class);
                startActivity(helpintent);
            } else {
                if (v == this.TudastesztButton) {
                    bundy.putString("nextActivity", "Tudásteszt");
                } else {
                    bundy.putString("nextActivity", "Szótárak");
                }

                intent.putExtras(bundy);

                startActivity(intent);
            }
        }
    }
}