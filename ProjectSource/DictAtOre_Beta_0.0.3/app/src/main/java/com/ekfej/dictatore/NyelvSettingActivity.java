package com.ekfej.dictatore;

import android.app.PendingIntent;
import android.content.IntentSender;
import android.provider.Telephony;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.content.Intent;
import android.widget.Toast;

public class NyelvSettingActivity extends AppCompatActivity implements View.OnClickListener {

    Button Ok;
    EditText LanguageName;
    String Name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nyelv_setting);

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

                Name = LanguageName.getText().toString();

            if ( 0 < (Name.length())) {
                DatabaseAccess db = DatabaseAccess.getInstance(this);
                db.LanguageInsert(Name);
                finish();
            }
            else {
                Toast.makeText(this, "Üres mező", Toast.LENGTH_SHORT).show();
            }
        }

    }
}
