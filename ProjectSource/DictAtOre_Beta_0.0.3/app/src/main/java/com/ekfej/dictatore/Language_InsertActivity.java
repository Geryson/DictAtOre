package com.ekfej.dictatore;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ekfej.dictatore.Database.DatabaseAccess;
import com.ekfej.dictatore.R;

public class Language_InsertActivity extends AppCompatActivity implements View.OnClickListener {

    Button Ok;
    EditText LanguageName;
    String Name;
    String LanguageNamebeforeactivity = null;
    TextView langInsert_textField;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language_insert);

        Ok = (Button) findViewById(R.id.OkButton);
        Ok.setOnClickListener(this);

        LanguageName = (EditText) findViewById(R.id.LanguageNameText);
        LanguageName.getText();

        langInsert_textField = (TextView) findViewById(R.id.langInsert_textField);

        Bundle gomb = getIntent().getExtras();
        String gombString = gomb.getString("nextActivity");
        Toast.makeText(this, gombString, Toast.LENGTH_SHORT).show();
        if (gombString.equals("Módosítás")) {
            setTitle("Nyelv módosítása");
            langInsert_textField.setText("A nyelv új neve:");
            LanguageNamebeforeactivity = gomb.getString("LanguageName");
            LanguageName.setText(LanguageNamebeforeactivity);
        }

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
                DatabaseAccess db = new DatabaseAccess(this);

            if (LanguageNamebeforeactivity == null) {
                if (db.languageMethod.LanguageInsert(LanguageName.getText().toString())) {
                    setResult(RESULT_OK);
                    finish();
                } else {
                    if (!db.elementary.NullHossz(LanguageName.getText().toString())) {
                        hiba = "Üres mező";
                    }
                    else {
                        hiba = "Már létezik";
                    }
                    Toast.makeText(this, hiba, Toast.LENGTH_SHORT).show();
                }
            }
            else {
                if (db.languageMethod.LanguageUpdate(LanguageName.getText().toString(), LanguageNamebeforeactivity)) {
                    setResult(RESULT_OK);
                    finish();
                }
                else {
                    if (!db.elementary.NullHossz(LanguageName.getText().toString())) {
                        hiba = "Üres mező";
                    }
                    /*else {
                        hiba = "Már létezik";
                    }*/
                    Toast.makeText(this, hiba, Toast.LENGTH_SHORT).show();
                }

            }
        }

    }
}
