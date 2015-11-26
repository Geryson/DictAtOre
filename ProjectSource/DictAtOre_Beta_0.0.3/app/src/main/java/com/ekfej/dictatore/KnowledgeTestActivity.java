package com.ekfej.dictatore;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ekfej.dictatore.Database.DatabaseAccess;

import java.util.List;

public class KnowledgeTestActivity extends AppCompatActivity implements View.OnClickListener {
    EditText User_decipherment; //felhasználó_megoldása
    TextView expression; //Kifejezés
    TextView FirstLanguage;
    Button Help;
    Button AgreeButton; //ideiglenes, később egy while ciklussal folyamotasan lesz ellenőrizve
    List<String> decipherment = null;
    String kifejezes = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_knowledgetest);

        DatabaseAccess db = DatabaseAccess.getInstance(this);

        Help = (Button) findViewById(R.id.tudasteszt_helpButton);
        Help.setOnClickListener(this);

        AgreeButton = (Button) findViewById(R.id.AgreeButton);
        AgreeButton.setOnClickListener(this);

        User_decipherment = (EditText) findViewById(R.id.tudasteszt_editText);
        User_decipherment.getText();

        Bundle LanguageBundle = getIntent().getExtras();
        String FirstLanguageBundle = LanguageBundle.getString("FirstLanguage");
        String SecondLanguageBundle = LanguageBundle.getString("SecondLanguage");
        FirstLanguage = (TextView) findViewById(R.id.tudasteszt_FirstLanguage);
        FirstLanguage.setText(FirstLanguageBundle);

        expression = (TextView) findViewById(R.id.tudasteszt_wordBox);
        try {
            if (db.Expression(FirstLanguageBundle) != null) {
                kifejezes = db.Expression(FirstLanguageBundle);
                expression.setText(kifejezes);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            if (kifejezes != null && db.Decipherment(SecondLanguageBundle, FirstLanguageBundle, kifejezes).size() > 0) {
                decipherment = db.Decipherment(SecondLanguageBundle, FirstLanguageBundle, kifejezes);
            }
            else {
                //nincs párja a kifejezzésnek, majd valamit kell jelezni (vagy új értéket adni az expression-nak)
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onClick(View v) {
        DatabaseAccess db = DatabaseAccess.getInstance(this);
        if (v == AgreeButton) {
            if (db.DeciphermentVsElement(User_decipherment, decipherment)) {
                Toast.makeText(this, "Jó megoldás", Toast.LENGTH_SHORT).show();
            }
            else  { Toast.makeText(this, "Rossz megoldás", Toast.LENGTH_SHORT).show(); }
        }
    }
}
