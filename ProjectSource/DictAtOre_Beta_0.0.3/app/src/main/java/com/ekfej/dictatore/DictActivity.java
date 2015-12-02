package com.ekfej.dictatore;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.ekfej.dictatore.Database.DatabaseAccess;
import com.ekfej.dictatore.Presenter.Language;
import com.ekfej.dictatore.Presenter.Word;

import java.util.ArrayList;
import java.util.List;

public class DictActivity extends AppCompatActivity implements View.OnClickListener {
    private ListView FirstWords, SecondWords;
    private Spinner Spinner1, Spinner2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dict);

        Bundle LangaugeBundle = getIntent().getExtras();
        String FirstLanguageBundle = LangaugeBundle.getString("FirstLanguage");
        String SecondLanguageBundle = LangaugeBundle.getString("SecondLanguage");

        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
        FirstWords = (ListView) findViewById(R.id.FirstWords);
        SecondWords = (ListView) findViewById(R.id.SecondWords);
        Spinner1 = (Spinner) findViewById(R.id.spinner);
        Spinner2 = (Spinner) findViewById(R.id.spinner2);

        List<String> Spinner1List = databaseAccess.LanguageSelect();
        List<String> Spinner2List = databaseAccess.LanguageSelect();


        for (int i = 0; i < Spinner1List.size(); i++){
            if (Spinner1List.get(i).equals(SecondLanguageBundle)) {
                Spinner1List.remove(i);
                break;
            }
        }
        for (int i = 0; i < Spinner2List.size(); i++){
            if (Spinner2List.get(i).equals(FirstLanguageBundle)) {
                Spinner2List.remove(i);
                break;
            }
        }

        ArrayAdapter<String> Spinner1Adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, Spinner1List);
        ArrayAdapter<String> Spinner2Adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, Spinner2List);
        Spinner1Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner2Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        int Spinner1Position = Spinner1Adapter.getPosition(FirstLanguageBundle);
        int Spinner2Position = Spinner2Adapter.getPosition(SecondLanguageBundle);
        Spinner1.setAdapter(Spinner1Adapter);
        Spinner2.setAdapter(Spinner2Adapter);
        Spinner1.setSelection(Spinner1Position);
        Spinner2.setSelection(Spinner2Position);

        List<String> FirstLanguageList = databaseAccess.WordsSelect(FirstLanguageBundle);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, FirstLanguageList);
        FirstWords.setAdapter(adapter);

        List<Word> DictionaryList = databaseAccess.DictionarySelect(FirstLanguageBundle, SecondLanguageBundle);
        List<String> SecondLanguageList = new ArrayList<String>();
        for (int i =0; i< DictionaryList.size(); i++)
        {
            if(DictionaryList.get(i).getMeaning().size() == 0) //a probléma az hogy a secondlist rövidebb mint a firstlist (most már megoldva)
            {
                SecondLanguageList.add(""); //null értéket nem adhatok bele, mert kiakad az adapter
            }
            else {
                SecondLanguageList.add(DictionaryList.get(i).getMeaning().get(0).getWord());
                //ha megoldjuk megfelelően a view-ba
            }
        }
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, SecondLanguageList);
        SecondWords.setAdapter(adapter2);

        //region pictori fgv tesztek
        List<Word> insert = new ArrayList<Word>();
        insert.add(new Word(7));
        //databaseAccess.WordInsert(new Word("papa", insert, new Language(2, "Magyar")));
        List<Word> s = new ArrayList<Word>();
        s.add(new Word("tata", insert, new Language(2, "Magyar")));
        //databaseAccess.WordDelete(s);
        String n = ""+0+"";
        //databaseAccess.WordUpdate(s.get(0), n, 4);
        //endregion

    }


    @Override
    public void onClick(View v) {

    }
}
