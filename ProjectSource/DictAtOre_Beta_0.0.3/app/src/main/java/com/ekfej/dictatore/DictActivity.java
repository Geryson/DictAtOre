package com.ekfej.dictatore;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;

import com.ekfej.dictatore.Database.DatabaseAccess;
import com.ekfej.dictatore.Presenter.Word;

import java.util.ArrayList;
import java.util.List;

public class DictActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener,
 View.OnTouchListener, AdapterView.OnItemClickListener, AbsListView.OnScrollListener{
    private ListView FirstWords, SecondWords;
    private Spinner Spinner1, Spinner2;
    private String FirstLanguageBundle, SecondLanguageBundle;
    private DatabaseAccess databaseAccess;
    private ArrayAdapter<String> Spinner1Adapter, Spinner2Adapter, adapter, adapter2;
    private List<String> Spinner1List, Spinner2List;
    private Button swapButton;
    private ImageButton addWordsButton;
    private View clickSource, touchSource;
    private int offset = 0;
    private boolean insertWindowIsUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dict);

        Bundle LangaugeBundle = getIntent().getExtras();
        FirstLanguageBundle = LangaugeBundle.getString("FirstLanguage");
        SecondLanguageBundle = LangaugeBundle.getString("SecondLanguage");

        insertWindowIsUp = false;

        databaseAccess = new DatabaseAccess(this);
        FirstWords = (ListView) findViewById(R.id.FirstWords);
        SecondWords = (ListView) findViewById(R.id.SecondWords);

        FirstWords.setOnTouchListener(this);
        SecondWords.setOnTouchListener(this);
        FirstWords.setOnItemClickListener(this);
        SecondWords.setOnItemClickListener(FirstWords.getOnItemClickListener());
        FirstWords.setOnScrollListener(this);
        SecondWords.setOnScrollListener(this);

        Spinner1 = (Spinner) findViewById(R.id.spinner);
        Spinner2 = (Spinner) findViewById(R.id.spinner2);

        Spinner1List = databaseAccess.lister.LanguageSelect();
        Spinner2List = databaseAccess.lister.LanguageSelect();

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

        swapButton = (Button) findViewById(R.id.swapButton);
        swapButton.setOnClickListener(this);

        addWordsButton = (ImageButton) findViewById(R.id.addWordsButton);
        addWordsButton.setOnClickListener(this);

        RefreshLayout();


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

    @Override
    public void onBackPressed() {
        setResult(RESULT_OK);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        RefreshLayout();
    }

    private void RefreshLayout() {
        List<String> FirstLanguageList = databaseAccess.lister.WordsSelect(FirstLanguageBundle);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, FirstLanguageList);
        FirstWords.setAdapter(adapter);

        List<Word> DictionaryList = databaseAccess.wordMethod.DictionarySelect(FirstLanguageBundle, SecondLanguageBundle);
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
        adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, SecondLanguageList);
        SecondWords.setAdapter(adapter2);
    }

    @Override
    public void onClick(View v) {
        if (v == swapButton){
            String temp = FirstLanguageBundle;
            FirstLanguageBundle = SecondLanguageBundle;
            SecondLanguageBundle = temp;

            Spinner1List.remove(0);
            Spinner1List.add(0, FirstLanguageBundle);
            Spinner1Adapter.notifyDataSetChanged();

            Spinner2List.remove(0);
            Spinner2List.add(0, SecondLanguageBundle);
            Spinner2Adapter.notifyDataSetChanged();

            RefreshLayout();
        }

        if (v == addWordsButton){
            Intent intent = new Intent(this, Word_InsertActivity.class);
            Bundle bundy = new Bundle();
            bundy.putString("FirstLanguage", FirstLanguageBundle);
            bundy.putString("SecondLanguage", SecondLanguageBundle);
            intent.putExtras(bundy);
            startActivityForResult(intent, 20);//random szám
        }

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

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        ListView otherList = v == FirstWords ? SecondWords : FirstWords;

        if(touchSource == null)
            touchSource = v;

        if(v == touchSource) {
            otherList.dispatchTouchEvent(event);
            if(event.getAction() == MotionEvent.ACTION_UP) {
                clickSource = v;
                touchSource = null;
            }
        }

        return false;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //az elágazás azért kell, mert egy nyomással 2 listát is megnyomunk (az összekapcsolás miatt), enélkül 2 activity nyílna
        if (!insertWindowIsUp){
            ListView otherParent = parent == FirstWords ? SecondWords : FirstWords;

            Intent intent = new Intent(this, Word_InsertActivity.class);

            Bundle bundy = new Bundle();

            String meaning = "";
            if (!otherParent.getItemAtPosition(position).toString().equals("hiányzó szó"))
                meaning = otherParent.getItemAtPosition(position).toString();

            bundy.putBoolean("editing", true);
            if (parent == FirstWords){
                bundy.putString("firstWord", parent.getItemAtPosition(position).toString());
                bundy.putString("secondWord", meaning);
            }
            else if (parent == SecondWords){
                bundy.putString("firstWord", meaning);
                bundy.putString("secondWord", parent.getItemAtPosition(position).toString());
            }
            bundy.putString("FirstLanguage", FirstLanguageBundle);
            bundy.putString("SecondLanguage", SecondLanguageBundle);

            intent.putExtras(bundy);
            startActivity(intent);
            insertWindowIsUp = true;
        }
        else{
            insertWindowIsUp = false;
        }

    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        ListView otherList = view == FirstWords ? SecondWords : FirstWords;

        if (view == clickSource)
            otherList.setSelectionFromTop(firstVisibleItem, view.getChildAt(0).getTop() + offset);
    }
}
