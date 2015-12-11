package com.ekfej.dictatore;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.ekfej.dictatore.Database.DatabaseAccess;

import java.util.List;
import java.util.jar.Attributes;

public class Language_ChooseActivity extends AppCompatActivity implements View.OnClickListener {
    private ListView listView;
    Button NewLanguageButton, DeleteLanguageButton, UpdateLanguageButton, NextButton;
    String Language = null, Language2 = null;
    String actualactivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language_choose);

        Bundle bundy = getIntent().getExtras();
        String nextActivity = bundy.getString("nextActivity"); //kiszedjük az intent extra tartalmát
        Toast.makeText(this, nextActivity, Toast.LENGTH_SHORT).show(); // a kiíratás csak jelzi, hogy sikerült, majd ki kell törölni!
        actualactivity = nextActivity;

        NewLanguageButton = (Button) findViewById(R.id.NewLanguageButton);
        NewLanguageButton.setOnClickListener(this);
        DeleteLanguageButton = (Button) findViewById(R.id.DeleteLanguageButton);
        DeleteLanguageButton.setOnClickListener(this);
        UpdateLanguageButton = (Button) findViewById(R.id.UpdateLanguageButton);
        UpdateLanguageButton.setOnClickListener(this);
        NextButton = (Button) findViewById(R.id.TovábbButton);
        NextButton.setOnClickListener(this);

        if (nextActivity.length() == 8){
            NewLanguageButton.setVisibility(View.VISIBLE);
            DeleteLanguageButton.setVisibility(View.VISIBLE);
            UpdateLanguageButton.setVisibility(View.VISIBLE);
            NextButton.setVisibility(View.VISIBLE);
        }
        else {
            if (nextActivity.length() == 10) {
                NextButton.setVisibility(View.VISIBLE);
            }
        }

        this.listView = (ListView) findViewById(R.id.listView);
        listView.setOnItemClickListener(ListClick);

        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);


         // jelen állapotban úgy működik (tudtam megoldani), hogy mielőtt az insert fgv-t meghívjuk, példányosítani kell a databaseaccess-t
         // ugyanez igaz a selectre és a töbire is...
        LoadList(databaseAccess);
    }

    public final ListView.OnItemClickListener ListClick = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            // your action
            if (view != NewLanguageButton && view !=DeleteLanguageButton) {

            DatabaseAccess db = DatabaseAccess.getInstance(this);
              List<String> L =  db.LanguageSelect();
                if (Language == null)
                { Language = L.get(position); }
                else {
                    if (Language2 == null && !Language.equals(L.get(position)))
                    { Language2 = L.get(position); }
                }

            }
        }
    };
    private void LoadList(DatabaseAccess databaseAccess) {

        List<String> quotes = databaseAccess.LanguageSelect();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, quotes);
        this.listView.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        if (v == NewLanguageButton)
        {
            Intent intent = new Intent(this, Language_InsertActivity.class);

            String nextActivity = "Felvitel";
            Bundle bundy = new Bundle();
            bundy.putString("nextActivity", nextActivity);
            intent.putExtras(bundy); // és beletesszük a futtatandó intentbe

            startActivityForResult(intent, 0);
        }


        if (v == DeleteLanguageButton)
        {
            if(Language != null && Language2 == null) {
                String nextActivity = "Törlés";
                Toast.makeText(this, nextActivity, Toast.LENGTH_SHORT).show();
                DatabaseAccess db= DatabaseAccess.getInstance(this);
                db.LanguageDeleteElemi(Language);
                Language = null;
                LoadList(db);
            }
        }

        if (v == UpdateLanguageButton)
        {
            if(Language != null && Language2 == null) {
                Intent intent = new Intent(this, Language_InsertActivity.class);

                String nextActivity = "Módosítás";
                Bundle bundy = new Bundle();
                bundy.putString("nextActivity", nextActivity);

                Bundle LanguageName = new Bundle();
                LanguageName.putString("LanguageName", Language);
                intent.putExtras(LanguageName);

                intent.putExtras(bundy);
                Language = null;
                startActivityForResult(intent, 0);
            }
        }
        if (v == NextButton) {
            if (Language != null && Language2 != null) {
                if (actualactivity.equals("Tudásteszt")) {
                    Intent intent = new Intent(this, KnowledgeTestActivity.class);

                    Bundle FirstLanguage = new Bundle();
                    FirstLanguage.putString("SecondLanguage", Language);
                    intent.putExtras(FirstLanguage);

                    Bundle SecondLanguage = new Bundle();
                    SecondLanguage.putString("SecondLanguage", Language2);
                    intent.putExtras(SecondLanguage);

                    startActivityForResult(intent, 1);
                }
                else {
                    if (actualactivity.equals("Szótárak")) {
                        Intent intent = new Intent(this, DictActivity.class);

                        /*
                        Bundle Language_ChooseActivityBundle = new Bundle();
                        Language_ChooseActivityBundle.putString("Language_ChooseActivity", this.getTitle().toString());
                        intent.putExtras(Language_ChooseActivityBundle);
                        */

                        Bundle FirstLanguage = new Bundle();
                        FirstLanguage.putString("SecondLanguage", Language);
                        intent.putExtras(FirstLanguage);

                        Bundle SecondLanguage = new Bundle();
                        SecondLanguage.putString("SecondLanguage", Language2);
                        intent.putExtras(SecondLanguage);

                        startActivityForResult(intent, 2);
                    }
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
        LoadList(databaseAccess);
        Language = null;
        Language2 = null;
    }
}