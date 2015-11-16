package com.ekfej.dictatore;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class NyelvValaszto1Activity extends AppCompatActivity {
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nyelv_valaszto1);
		
        Bundle bundy = getIntent().getExtras();
        String nextActivity = bundy.getString("nextActivity"); //kiszedjük az intent extra tartalmát
        Toast.makeText(this, nextActivity, Toast.LENGTH_SHORT).show(); // a kiíratás csak jelzi, hogy sikerült, majd ki kell törölni!


        this.listView = (ListView) findViewById(R.id.listView);
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
        databaseAccess.open();
        //databaseAccess.WordsWordUpdate("barackospite", "autó", default, default);

        //ezek a parancsok mind működnek
        //databaseAccess.LanguageDelete("rabbiwdwd");
        //databaseAccess.LanguageUpdate("Francia", "terrolista");
        databaseAccess.LanguageInsert("Török");
        //databaseAccess.WordsInsert("Orosz", 158, 5, 2);
         // jelen állapotban csak úgy működik (tudtam megoldani), hogy mielőtt az insert fgv-t meghívjuk, példányosítani kell és előtte open, utána close
         // fgv-eket meg kell hívni, ugyanez igaz a selctre és a töbire is...
        List<String> quotes = databaseAccess.LanguageSelect();
        databaseAccess.close();


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, quotes);
        this.listView.setAdapter(adapter);
    }
}