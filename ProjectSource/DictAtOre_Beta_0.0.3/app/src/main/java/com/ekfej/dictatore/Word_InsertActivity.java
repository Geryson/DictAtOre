package com.ekfej.dictatore;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ekfej.dictatore.Database.DatabaseAccess;
import com.ekfej.dictatore.Presenter.Language;
import com.ekfej.dictatore.Presenter.Word;

import java.util.ArrayList;
import java.util.List;

public class Word_InsertActivity extends AppCompatActivity implements View.OnClickListener {
    TextView FirstLanguageTextView, SecondLanguageTextView;
    EditText WordInsert1, WordInsert2;
    Button Add;
    String FirstLBundle, SecondLBundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_insert);

        //region Pictori kontárkodása
        Bundle getlanguage = getIntent().getExtras();
        FirstLBundle = getlanguage.getString("FirstLanguage");
        SecondLBundle = getlanguage.getString("SecondLanguage");

        FirstLanguageTextView = (TextView) findViewById(R.id.wordInsertFirstLanguage);
        FirstLanguageTextView.setText(FirstLBundle);
        SecondLanguageTextView = (TextView) findViewById(R.id.wordInsertSecondLanguage);
        SecondLanguageTextView.setText(SecondLBundle);

        WordInsert1 = (EditText) findViewById(R.id.wordInsertFirstWord);
        WordInsert2 = (EditText) findViewById(R.id.wordInsertSecondWord);
        WordInsert1.getText();
        WordInsert2.getText();

        Add = (Button) findViewById(R.id.insertWord);
        Add.setOnClickListener(this);

        //endregion

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.8), (int)(height*.6));
        WindowManager.LayoutParams windowManager = getWindow().getAttributes();
        windowManager.dimAmount = 0.5f;
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
    }

    @Override
    public void onClick(View v) {
        if (v == Add) { //ne használd, még nagyon nincs kész
            DatabaseAccess db = DatabaseAccess.getInstance(this);
            List<Word> word2 = new ArrayList<Word>();
                    word2.add(new Word(WordInsert2.getText().toString(), new Language(db.LanguageIdSelect(SecondLBundle), SecondLBundle)));
            db.WordInsert(new Word(WordInsert1.getText().toString(), word2, new Language(db.LanguageIdSelect(FirstLBundle), FirstLBundle)));
            //ha nem létezik a meaning akkor azt is létre kell hoznia

            setResult(RESULT_OK);

            finish();

        }
    }
}
