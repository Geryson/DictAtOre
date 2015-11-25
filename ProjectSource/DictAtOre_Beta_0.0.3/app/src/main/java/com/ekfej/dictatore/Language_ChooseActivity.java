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
    Button NewLanguageButton;
    Button DeleteLanguageButton;
    Button UpdateLanguageButton;
    String Language= null;
    //DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language_choose);

        Bundle bundy = getIntent().getExtras();
        String nextActivity = bundy.getString("nextActivity"); //kiszedjük az intent extra tartalmát
        Toast.makeText(this, nextActivity, Toast.LENGTH_SHORT).show(); // a kiíratás csak jelzi, hogy sikerült, majd ki kell törölni!

        NewLanguageButton = (Button) findViewById(R.id.NewLanguageButton);
        NewLanguageButton.setOnClickListener(this);
        DeleteLanguageButton = (Button) findViewById(R.id.DeleteLanguageButton);
        DeleteLanguageButton.setOnClickListener(this);
        UpdateLanguageButton = (Button) findViewById(R.id.UpdateLanguageButton);
        UpdateLanguageButton.setOnClickListener(this);

        if (nextActivity.length() == 8){
            NewLanguageButton.setVisibility(View.VISIBLE);
            DeleteLanguageButton.setVisibility(View.VISIBLE);
            UpdateLanguageButton.setVisibility(View.VISIBLE);
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
                Language = L.get(position);


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
            if(Language != null) {
                String nextActivity = "Törlés";
                Toast.makeText(this, nextActivity, Toast.LENGTH_SHORT).show();
                DatabaseAccess db= DatabaseAccess.getInstance(this);
                db.LanguageDeleteElemi(Language);
                LoadList(db);
            }
        }

        if (v == UpdateLanguageButton)
        {
            if(Language != null) {
                Intent intent = new Intent(this, Language_InsertActivity.class);

                String nextActivity = "Módosítás";
                Bundle bundy = new Bundle();
                bundy.putString("nextActivity", nextActivity);

                Bundle LanguageName = new Bundle();
                LanguageName.putString("LanguageName", Language);
                intent.putExtras(LanguageName);

                intent.putExtras(bundy);

                startActivityForResult(intent, 0);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
        LoadList(databaseAccess);
    }
}