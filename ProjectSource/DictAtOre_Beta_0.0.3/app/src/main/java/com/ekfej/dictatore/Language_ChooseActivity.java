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

public class Language_ChooseActivity extends AppCompatActivity implements View.OnClickListener {
    private ListView listView;
    Button NewLanguageButton;
    Button DeleteLanguageButton;
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

        if (nextActivity.length() == 8){
            NewLanguageButton.setVisibility(View.VISIBLE);
            DeleteLanguageButton.setVisibility(View.VISIBLE);
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
                Language = L.get(position); //nem tudtam kiszedni az itemet


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
                db.LanguageDelete(Language);
                LoadList(db);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
        LoadList(databaseAccess);
    }
}