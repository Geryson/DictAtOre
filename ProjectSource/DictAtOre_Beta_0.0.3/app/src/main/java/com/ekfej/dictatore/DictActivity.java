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
import com.ekfej.dictatore.Presenter.DictPresenter;
import com.ekfej.dictatore.Presenter.Language;
import com.ekfej.dictatore.Presenter.Word;

import java.util.ArrayList;
import java.util.List;

public class DictActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener,
 View.OnTouchListener, AdapterView.OnItemClickListener, AbsListView.OnScrollListener{
    private ListView FirstWords, SecondWords;
    private Spinner Spinner1, Spinner2;
    private DatabaseAccess databaseAccess;
    private ArrayAdapter<String> adapter, adapter2;
    private ArrayAdapter<Word> Spinner1Adapter, Spinner2Adapter;

    private Button swapButton;
    private ImageButton addWordsButton;
    private View clickSource, touchSource;
    private int offset = 0;
    private boolean insertWindowIsUp;
    private DictPresenter presenter;
    private Language firstLanguage, secondLanguage;
    private ArrayAdapter<Language> langAdapter;
    private List<ArrayAdapter<Word>> language2Meanings;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dict);


        Bundle LangaugeBundle = getIntent().getExtras();
        firstLanguage = LangaugeBundle.getParcelable("firstLanguage");
        secondLanguage = LangaugeBundle.getParcelable("secondLanguage");

        insertWindowIsUp = false;


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

        Spinner1Adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1);
        Spinner2Adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1);

        Spinner1.setOnItemSelectedListener(this);
        Spinner2.setOnItemSelectedListener(this);

        swapButton = (Button) findViewById(R.id.swapButton);
        swapButton.setOnClickListener(this);

        addWordsButton = (ImageButton) findViewById(R.id.addWordsButton);
        addWordsButton.setOnClickListener(this);

        langAdapter = new ArrayAdapter<Language>(this, android.R.layout.simple_spinner_item);
        langAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        presenter = new DictPresenter(this, firstLanguage.getId(), secondLanguage.getId(), langAdapter,
                Spinner1, Spinner2, Spinner1Adapter, Spinner2Adapter, language2Meanings);

        Spinner1.setAdapter(langAdapter);
        Spinner2.setAdapter(langAdapter);

        RefreshLayout();


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
        FirstWords.setAdapter(Spinner1Adapter);
        SecondWords.setAdapter(Spinner2Adapter);
    }

    @Override
    public void onClick(View v) {
        if (v == swapButton){
            presenter.swapLanguages();
        }

        if (v == addWordsButton){
            Intent intent = new Intent(this, Word_InsertActivity.class);
            intent.putExtra("firstLanguage", firstLanguage);
            intent.putExtra("secondLanguage", secondLanguage);
            startActivityForResult(intent, 20);//random szám
        }

    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        presenter.languageChanged(parent.getId(), position);
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
            bundy.putParcelable("firstLanguage", firstLanguage);
            bundy.putParcelable("secondLanguage", secondLanguage);

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
