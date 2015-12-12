package com.ekfej.dictatore;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ekfej.dictatore.Database.DatabaseAccess;
import com.ekfej.dictatore.Presenter.KnowledgeTestPresenter;

import java.util.List;

public class KnowledgeTestActivity extends AppCompatActivity implements View.OnClickListener {
    EditText User_decipherment; //felhasználó_megoldása
    TextView expression; //Kifejezés
    TextView FirstLanguage;
    Button Help;
    Button AgreeButton; //ideiglenes, később egy while ciklussal folyamotasan lesz ellenőrizve
    List<String> decipherment = null;
    String kifejezes = null;
    TextView progressBox;
    int TestNumber = 1; //számolja hogy hanyadik feladatál jár
    KnowledgeTestPresenter presenter;
    DatabaseAccess db;
    String FirstLanguageBundle, SecondLanguageBundle;
    int sumtest= 15;


    @Override
    public void onBackPressed() {
        setResult(RESULT_OK);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_knowledgetest);

        db = DatabaseAccess.getInstance(this);

        Help = (Button) findViewById(R.id.knowledgeTest_helpButton);
        Help.setOnClickListener(this);

        AgreeButton = (Button) findViewById(R.id.AgreeButton);
        AgreeButton.setOnClickListener(this);

        progressBox = (TextView) findViewById(R.id.tudasteszt_progressBox);
        progressBox.setText(TestNumber + " / " + sumtest + " szó");

        User_decipherment = (EditText) findViewById(R.id.knowledgeTest_decipherment);
        User_decipherment.getText();

        Bundle LanguageBundle = getIntent().getExtras();
        FirstLanguageBundle = LanguageBundle.getString("FirstLanguage");
        SecondLanguageBundle = LanguageBundle.getString("SecondLanguage");
        FirstLanguage = (TextView) findViewById(R.id.tudasteszt_FirstLanguage);
        FirstLanguage.setText(SecondLanguageBundle + " nyelven:");

        presenter = new KnowledgeTestPresenter(this, FirstLanguageBundle, SecondLanguageBundle, 15);
        sumtest = presenter.GetWordsCount();
        expression = (TextView) findViewById(R.id.knowledgeTest_expression);
        expression.setText("nincs ilyen szó");
        Test();

    }
    public void Test () {
        try {
            kifejezes = presenter.GetNextWord();
            if (kifejezes != null) {
                progressBox.setText(TestNumber + " / " + sumtest + " szó");
                expression.setText(kifejezes);
                TestNumber++;
            }
            else {
                //elfogytak a szavak (lehetne értékelni a teljesítményt)
                //esetleg egy kis activity mint a languageinsertnél
                //és oda kiírnánk hány %-os lett a tesztje
                Toast.makeText(this, "Vége", Toast.LENGTH_SHORT).show();
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
                kifejezes = null;
                expression.setText(null);
                User_decipherment.setText(null);
                Test();
            }
            else {
                if (kifejezes != null) {
                    Toast.makeText(this, "Rossz megoldás", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}