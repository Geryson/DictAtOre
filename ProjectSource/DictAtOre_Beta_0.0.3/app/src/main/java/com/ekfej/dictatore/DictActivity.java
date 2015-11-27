package com.ekfej.dictatore;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.ekfej.dictatore.Database.DatabaseAccess;

import java.util.List;

public class DictActivity extends AppCompatActivity implements View.OnClickListener {
    private ListView FirstWords, SecondWords;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dict);

        FirstWords = (ListView) findViewById(R.id.FirstWords);
        SecondWords = (ListView) findViewById(R.id.SecondWords);

        Bundle LangaugeBundle = getIntent().getExtras();
        String FirstLanguageBundle = LangaugeBundle.getString("FirstLanguage");
        String SecondLanguageBundle = LangaugeBundle.getString("SecondLanguage");

        DatabaseAccess db = DatabaseAccess.getInstance(this);
        List<String> FirstLanguageList = db.WordsSelect(FirstLanguageBundle);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, FirstLanguageList);
        FirstWords.setAdapter(adapter);


    }


    @Override
    public void onClick(View v) {

    }
}
