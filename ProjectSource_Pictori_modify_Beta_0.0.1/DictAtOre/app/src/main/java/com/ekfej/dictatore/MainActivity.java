package com.ekfej.dictatore;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.content.Intent;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button TudastesztButton, SzotarakButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TudastesztButton = (Button) findViewById(R.id.TudastesztButton); //saját szöveg
        TudastesztButton.setOnClickListener(this);
        SzotarakButton = (Button) findViewById(R.id.SzotarakButton);
        SzotarakButton.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
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

        if (v == TudastesztButton)
        {
            Toast.makeText(this, "Tudáspróba", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(this, NyelvValaszto1Activity.class);
            startActivity(intent);
    }

    else

    {
        if (v == SzotarakButton)

        {
            Toast.makeText(this, "Szótár", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(this, Szotar_ValasztoActivity.class);
            startActivity(intent);
        }
    }
}
}
