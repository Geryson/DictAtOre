package com.ekfej.dictatore;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.content.Intent;

<<<<<<< HEAD
public class MainActivity extends AppCompatActivity implements View.OnClickListener{
=======
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button TudastesztButton, SzotarakButton;
>>>>>>> 186317e93dded085c22f2b33c3d343473ed7976c

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

<<<<<<< HEAD
        Button TudastesztButton = (Button) findViewById(R.id.TudastesztButton); //saját szöveg
        TudastesztButton.setOnClickListener(this);
    }
=======
        TudastesztButton = (Button) findViewById(R.id.TudastesztButton); //saját szöveg
        TudastesztButton.setOnClickListener(this);
        SzotarakButton = (Button) findViewById(R.id.SzotarakButton);
        SzotarakButton.setOnClickListener(this);
    }

>>>>>>> 186317e93dded085c22f2b33c3d343473ed7976c
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
<<<<<<< HEAD
        Toast.makeText(this, "Tudáspróba", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(this, NyelvValaszto1Activity.class);
        startActivity(intent);
    }
}
=======

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
>>>>>>> 186317e93dded085c22f2b33c3d343473ed7976c
