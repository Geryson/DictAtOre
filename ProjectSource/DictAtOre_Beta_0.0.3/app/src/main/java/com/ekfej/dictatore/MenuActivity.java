package com.ekfej.dictatore;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.content.Intent;
import android.widget.ImageView;

public class MenuActivity extends AppCompatActivity implements View.OnClickListener{

    Button TudastesztButton, SzotarakButton;
    ImageView progress;

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




;
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

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, Language_ChooseActivity.class);
        Bundle bundy = new Bundle();
        if (v == this.TudastesztButton){
            bundy.putString("nextActivity", "Tudásteszt");
        }
        else{
            bundy.putString("nextActivity", "Szótárak");
        }

        intent.putExtras(bundy);

        startActivity(intent);

    }
}