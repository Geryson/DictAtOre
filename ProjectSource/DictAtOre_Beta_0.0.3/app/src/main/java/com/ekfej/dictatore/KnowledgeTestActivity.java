package com.ekfej.dictatore;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ekfej.dictatore.Database.DatabaseAccess;

public class KnowledgeTestActivity extends AppCompatActivity implements View.OnClickListener {
    EditText User_decipherment; //felhasználó_megoldása
    TextView expression; //Kifejezés
    TextView FirstLanguage;
    Button Help;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_knowledgetest);

        DatabaseAccess db = DatabaseAccess.getInstance(this);

        Help = (Button) findViewById(R.id.tudasteszt_helpButton);
        Help.setOnClickListener(this);

        Bundle LanguageBundle = getIntent().getExtras();
        String FirstLanguageBundle = LanguageBundle.getString("FirstLanguage");
        String SecondLanguageBundle = LanguageBundle.getString("SecondLanguage");
        FirstLanguage = (TextView) findViewById(R.id.tudasteszt_FirstLanguage);
        FirstLanguage.setText(FirstLanguageBundle);

        expression = (TextView) findViewById(R.id.tudasteszt_wordBox);
        try {
            if (db.Expression(FirstLanguageBundle) != null) {
                expression.setText(db.Expression(FirstLanguageBundle));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {

    }
}
