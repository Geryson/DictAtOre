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

public class DictActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    private ListView FirstWords, SecondWords;
    private Spinner Spinner1, Spinner2;
    private String FirstLanguageBundle, SecondLanguageBundle;
    private DatabaseAccess databaseAccess;
    private ArrayAdapter<String> Spinner1Adapter, Spinner2Adapter;
    private List<String> Spinner1List, Spinner2List;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dict);

        Bundle LangaugeBundle = getIntent().getExtras();
        FirstLanguageBundle = LangaugeBundle.getString("FirstLanguage");
        SecondLanguageBundle = LangaugeBundle.getString("SecondLanguage");

        databaseAccess = DatabaseAccess.getInstance(this);
        FirstWords = (ListView) findViewById(R.id.FirstWords);
        SecondWords = (ListView) findViewById(R.id.SecondWords);
        Spinner1 = (Spinner) findViewById(R.id.spinner);
        Spinner2 = (Spinner) findViewById(R.id.spinner2);

        Spinner1List = databaseAccess.LanguageSelect();
        Spinner2List = databaseAccess.LanguageSelect();

        RefreshSpinners();

        Spinner1Adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, Spinner1List);
        Spinner2Adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, Spinner2List);
        Spinner1Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner2Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        int Spinner1Position = Spinner1Adapter.getPosition(FirstLanguageBundle);
        int Spinner2Position = Spinner2Adapter.getPosition(SecondLanguageBundle);
        Spinner1.setAdapter(Spinner1Adapter);
        Spinner2.setAdapter(Spinner2Adapter);
        Spinner1.setSelection(Spinner1Position);
        Spinner2.setSelection(Spinner2Position);

        Spinner1.setOnItemSelectedListener(this);
        Spinner2.setOnItemSelectedListener(this);

        RefreshLayout();

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

    private void RefreshSpinners() {
        int firstPosition = 0, secondPosition = 0;
        for (int i = 0; i < Spinner1List.size(); i++){
            if (Spinner1List.get(i).equals(SecondLanguageBundle)) {
                Spinner1List.remove(i);
            }
            if (Spinner1List.get(i).equals(FirstLanguageBundle)){
                firstPosition = i;
            }
        }
        for (int i = 0; i < Spinner2List.size(); i++){
            if (Spinner2List.get(i).equals(FirstLanguageBundle)) {
                Spinner2List.remove(i);
            }
            if (Spinner2List.get(i).equals(SecondLanguageBundle)){
                secondPosition = i;
            }
        }
        Spinner1List.remove(firstPosition);
        Spinner2List.remove(secondPosition);

        java.util.Collections.sort(Spinner1List);
        java.util.Collections.sort(Spinner2List);

        Spinner1List.add(0, FirstLanguageBundle);
        Spinner2List.add(0, SecondLanguageBundle);
    }

    private void RefreshLayout() {
        List<String> FirstLanguageList = databaseAccess.WordsSelect(FirstLanguageBundle);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, FirstLanguageList);
        FirstWords.setAdapter(adapter);

        List<Word> DictionaryList = databaseAccess.DictionarySelect(FirstLanguageBundle, SecondLanguageBundle);
        List<String> SecondLanguageList = new ArrayList<String>();
        for (int i =0; i< DictionaryList.size(); i++)
        {
            if(DictionaryList.get(i).getMeaning().size() == 0) //a problĂ©ma az hogy a secondlist rĂ¶videbb mint a firstlist (most mĂˇr megoldva)
            {
                SecondLanguageList.add("hiányzó szó"); //null Ă©rtĂ©ket nem adhatok bele, mert kiakad az adapter
            }
            else {
                SecondLanguageList.add(DictionaryList.get(i).getMeaning().get(0).getWord());
                //ha megoldjuk megfelelĹ‘en a view-ba
            }
        }
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, SecondLanguageList);
        SecondWords.setAdapter(adapter2);
    }

    @Override
    public void onClick(View v) {

    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String OldFirstLanguage = FirstLanguageBundle;
        String OldSecondLanguage = SecondLanguageBundle;

        int identifier = parent.getId();
        if (identifier == R.id.spinner)
            FirstLanguageBundle = parent.getSelectedItem().toString();
        if (identifier == R.id.spinner2)
            SecondLanguageBundle = parent.getSelectedItem().toString();

        if (!OldFirstLanguage.equals(FirstLanguageBundle)){
            Spinner2Adapter.add(OldFirstLanguage);

        }
        else if (!OldSecondLanguage.equals(SecondLanguageBundle)){
            Spinner1Adapter.add(OldSecondLanguage);
        }

        RefreshSpinners();

        Spinner1.setSelection(Spinner1Adapter.getPosition(FirstLanguageBundle));
        Spinner2.setSelection(Spinner2Adapter.getPosition(SecondLanguageBundle));

        RefreshLayout();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
