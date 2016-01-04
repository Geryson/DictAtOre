package com.ekfej.dictatore;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.ekfej.dictatore.Database.DatabaseAccess;
import com.ekfej.dictatore.Presenter.DictPresenter;
import com.ekfej.dictatore.Presenter.Language;
import com.ekfej.dictatore.Presenter.Word;

import java.util.ArrayList;
import java.util.List;

public class DictActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener,
 View.OnTouchListener, AdapterView.OnItemClickListener, AbsListView.OnScrollListener, AdapterView.OnItemLongClickListener{
    private ListView FirstWords, SecondWords;
    private Spinner Spinner1, Spinner2;
    private DatabaseAccess databaseAccess;
    private ArrayAdapter<String> adapter, adapter2;
    private ArrayAdapter<Word> Spinner1Adapter, Spinner2Adapter;

    private Button swapButton;
    private ImageButton addWordsButton;
    private View clickSource, touchSource;
    private int offset = 0;
    private boolean insertWindowIsUp, popupMenuIsUp;
    private DictPresenter presenter;
    private Language firstLanguage, secondLanguage;
    private ArrayAdapter<Language> langAdapter1, langAdapter2;
    private List<ArrayAdapter<Word>> language2Meanings;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dict);


        Bundle LangaugeBundle = getIntent().getExtras();
        firstLanguage = LangaugeBundle.getParcelable("firstLanguage");
        secondLanguage = LangaugeBundle.getParcelable("secondLanguage");

        insertWindowIsUp = false;
        popupMenuIsUp = true;

        FirstWords = (ListView) findViewById(R.id.FirstWords);

        SecondWords = (ListView) findViewById(R.id.SecondWords);

        FirstWords.setOnTouchListener(this);
        SecondWords.setOnTouchListener(this);
        FirstWords.setOnItemClickListener(this);
        SecondWords.setOnItemClickListener(this);
        SecondWords.setOnItemLongClickListener(this);
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

        langAdapter1 = new ArrayAdapter<Language>(this, android.R.layout.simple_spinner_item);
        langAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        langAdapter2 = new ArrayAdapter<Language>(this, android.R.layout.simple_spinner_item);
        langAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        language2Meanings = new ArrayList<ArrayAdapter<Word>>();

        presenter = new DictPresenter(this, firstLanguage.getId(), secondLanguage.getId(), langAdapter1, langAdapter2,
                Spinner1, Spinner2, Spinner1Adapter, Spinner2Adapter, language2Meanings);


        Spinner1.setAdapter(langAdapter1);
        Spinner2.setAdapter(langAdapter2);

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
        Spinner1Adapter.notifyDataSetChanged();
        Spinner2Adapter.notifyDataSetChanged();
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
        ArrayAdapter<Word> meanings = language2Meanings.get(position);

        if (!popupMenuIsUp && parent != FirstWords && meanings != null) {

            PopupMenu popup = new PopupMenu(this, view);

            for (int i = 0; i < meanings.getCount(); i++) {
                popup.getMenu().add(meanings.getItem(i).getWord());
            }

            popup.show();
            popupMenuIsUp = true;
        }
        else{
            popupMenuIsUp = false;
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


    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

        Intent intent = new Intent(this, Word_InsertActivity.class);

        Bundle bundy = new Bundle();

        bundy.putBoolean("editing", true);

        ArrayAdapter<Word> meanings = language2Meanings.get(position);
        ArrayList<Word> secondWord = new ArrayList<Word>();

        secondWord.add((Word) SecondWords.getItemAtPosition(position));

        if (meanings != null) {
            for (int i = 0; i < meanings.getCount(); i++) {
                secondWord.add(meanings.getItem(i));
            }
        }

        bundy.putParcelable("firstWord", (Word) FirstWords.getItemAtPosition(position));
        bundy.putParcelableArrayList("secondWord", secondWord);

        bundy.putParcelable("firstLanguage", firstLanguage);
        bundy.putParcelable("secondLanguage", secondLanguage);

        intent.putExtras(bundy);
        startActivity(intent);


        return true;
    }
}
