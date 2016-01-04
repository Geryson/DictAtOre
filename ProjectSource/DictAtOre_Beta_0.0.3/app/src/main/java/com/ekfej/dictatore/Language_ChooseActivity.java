package com.ekfej.dictatore;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ekfej.dictatore.Database.DatabaseAccess;
import com.ekfej.dictatore.Presenter.Language;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Language_ChooseActivity extends AppCompatActivity implements View.OnClickListener {
    ListView listView;
    Button NewLanguageButton, DeleteLanguageButton, UpdateLanguageButton, NextButton, RemoveChosenLangsButton;
    String actualactivity;
    Language Language1 = null, Language2 = null;
    DatabaseAccess db;
    TextView FirstSelection, SecondSelection;
    ImageView progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language_choose);

        /*
        progress = (ImageView) findViewById(R.id.imageViewanim2);
        Animation anim2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.scaleleft);
        progress.setVisibility(View.INVISIBLE);
        progress.startAnimation(anim2);
        */

        Bundle bundy = getIntent().getExtras();
        String nextActivity = bundy.getString("nextActivity"); //kiszedjük az intent extra tartalmát
        Toast.makeText(this, nextActivity, Toast.LENGTH_SHORT).show(); // a kiíratás csak jelzi, hogy sikerült, majd ki kell törölni!
        actualactivity = nextActivity;

        FirstSelection = (TextView) findViewById(R.id.firstSelection);
        SecondSelection = (TextView) findViewById(R.id.secondSelection);

        NewLanguageButton = (Button) findViewById(R.id.NewLanguageButton);
        NewLanguageButton.setOnClickListener(this);
        DeleteLanguageButton = (Button) findViewById(R.id.DeleteLanguageButton);
        DeleteLanguageButton.setOnClickListener(this);
        UpdateLanguageButton = (Button) findViewById(R.id.UpdateLanguageButton);
        UpdateLanguageButton.setOnClickListener(this);
        NextButton = (Button) findViewById(R.id.TovábbButton);
        NextButton.setOnClickListener(this);
        RemoveChosenLangsButton = (Button) findViewById(R.id.removeChosenLangs_button);
        RemoveChosenLangsButton.setOnClickListener(this);

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

        db = new DatabaseAccess(this);

        LoadList(db);
    }

    public final ListView.OnItemClickListener ListClick = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            // your action
            if (view != NewLanguageButton && view !=DeleteLanguageButton) {

              List<String> L =  db.lister.LanguageSelect();
                if (Language1 == null)
                {
                    Language1 = new Language(position, L.get(position));
                    FirstSelection.setText(Language1.getName());
                }
                else {
                    if (Language2 == null && !Language1.getName().equals(L.get(position)))
                    {
                        Language2 = new Language(position, L.get(position));
                        SecondSelection.setText(Language2.getName());
                    }
                }

            }
        }
    };
    private void LoadList(DatabaseAccess databaseAccess) {

        List<String> quotes = databaseAccess.lister.LanguageSelect();
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
            if(Language1 != null && Language2 == null) {
                String nextActivity = "Törlés";
                Toast.makeText(this, nextActivity, Toast.LENGTH_SHORT).show();

                db.languageMethod.LanguageDelete(Language1.getName());
                Language1 = null;
                LoadList(db);
            }
        }

        if (v == UpdateLanguageButton)
        {
            if(Language1 != null && Language2 == null) {
                Intent intent = new Intent(this, Language_InsertActivity.class);

                String nextActivity = "Módosítás";
                Bundle bundy = new Bundle();
                bundy.putString("nextActivity", nextActivity);

                Bundle LanguageName = new Bundle();
                LanguageName.putString("LanguageName", Language1.getName());
                intent.putExtras(LanguageName);

                intent.putExtras(bundy);
                Language1 = null;
                startActivityForResult(intent, 0);
            }
        }
        if (v == NextButton) {
            if (Language1 != null && Language2 != null) {
                if (actualactivity.equals("Tudásteszt")) {
                    Intent intent = new Intent(this, KnowledgeTestActivity.class);

                    intent.putExtra("firstLanguage", Language1);
                    intent.putExtra("secondLanguage", Language2);

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

                        intent.putExtra("firstLanguage", Language1);
                        intent.putExtra("secondLanguage", Language2);

                        startActivityForResult(intent, 2);
                    }
                }
            }
        }

        if (v == RemoveChosenLangsButton && Language1 != null) {
            Language1 = null;
            Language2 = null;
            FirstSelection.setText("");
            SecondSelection.setText("");
            Toast.makeText(Language_ChooseActivity.this, "Kijelölés törölve", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        LoadList(db);
        Language1 = null;
        Language2 = null;
        FirstSelection.setText("");
        SecondSelection.setText("");
    }
}