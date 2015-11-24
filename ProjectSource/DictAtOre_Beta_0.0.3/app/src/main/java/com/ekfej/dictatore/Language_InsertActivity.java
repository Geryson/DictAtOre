package com.ekfej.dictatore;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ekfej.dictatore.Database.DatabaseAccess;

public class Language_InsertActivity extends AppCompatActivity implements View.OnClickListener {

    Button Ok;
    EditText LanguageName;
    String Name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language_insert);

        Ok = (Button) findViewById(R.id.OkButton);
        Ok.setOnClickListener(this);

        LanguageName = (EditText) findViewById(R.id.LanguageNameText);
        LanguageName.getText();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_nyelv_setting, menu);
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
        if(v == Ok)
        {
                String hiba = "Nincs hiba";
                DatabaseAccess db = DatabaseAccess.getInstance(this);
            if (db.LanguageInsert(LanguageName)){
                setResult(RESULT_OK);
                finish();
            }
            else {
                if (! db.NullHossz(LanguageName.getText().toString())) { hiba = "Üres mező"; }  else { hiba = "Már létezik";}
                Toast.makeText(this, hiba, Toast.LENGTH_SHORT).show();
            }
        }

    }
}
