package com.ekfej.dictatore;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ekfej.dictatore.Modell.DatabaseAccess;
import com.ekfej.dictatore.Presenter.Language;
import com.ekfej.dictatore.Presenter.Word;
import com.ekfej.dictatore.R;

import java.util.ArrayList;
import java.util.List;

public class Word_InsertActivity extends AppCompatActivity implements View.OnClickListener {
    TextView FirstLanguageTextView, SecondLanguageTextView;
    EditText WordInsert1, WordInsert2;
    Button Add;
    String FirstLBundle, SecondLBundle;
    LinearLayout removeLayout, addMeaningLayout;
    ImageView Editing, Deleting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_insert);

        //region Pictori kontárkodása
        Bundle getlanguage = getIntent().getExtras();
        FirstLBundle = getlanguage.getString("FirstLanguage");
        SecondLBundle = getlanguage.getString("SecondLanguage");
        WordInsert1 = (EditText) findViewById(R.id.wordInsertFirstWord);
        WordInsert2 = (EditText) findViewById(R.id.wordInsertSecondWord);

        removeLayout = (LinearLayout) findViewById(R.id.removeLayout);
        addMeaningLayout = (LinearLayout) findViewById(R.id.addMeaningLayout);

        Add = (Button) findViewById(R.id.insertWord);
        Add.setOnClickListener(this);

        if (getlanguage.getBoolean("editing")){
            removeLayout.setVisibility(View.VISIBLE);
            addMeaningLayout.setVisibility(View.VISIBLE);
            WordInsert1.setText(getlanguage.getString("firstWord"));
            WordInsert2.setText(getlanguage.getString("secondWord"));
            Add.setText("Módosítás");
            setTitle("Szó módosítása");

            Editing = (ImageView) findViewById(R.id.EditingimageView);
            Editing.setOnClickListener(this);
            Deleting = (ImageView) findViewById(R.id.DeleteimageView);
            Deleting.setOnClickListener(this);
        }

        FirstLanguageTextView = (TextView) findViewById(R.id.wordInsertFirstLanguage);
        FirstLanguageTextView.setText(FirstLBundle);
        SecondLanguageTextView = (TextView) findViewById(R.id.wordInsertSecondLanguage);
        SecondLanguageTextView.setText(SecondLBundle);

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
        DatabaseAccess db = DatabaseAccess.getInstance(this);

        if (v == Add) { //ne használd, még nagyon nincs kész

            List<Word> word2 = new ArrayList<Word>();
                    word2.add(new Word(WordInsert2.getText().toString(), new Language(db.LanguageIdSelect(SecondLBundle), SecondLBundle)));
            db.WordInsert(new Word(WordInsert1.getText().toString(), word2, new Language(db.LanguageIdSelect(FirstLBundle), FirstLBundle)));
            //ha nem létezik a meaning akkor azt is létre kell hoznia

            setResult(RESULT_OK);

            finish();

        }

        if (v == Editing) {

            //db.WordUpdateObject(,WordInsert1.getText().toString());
            //db.WordUpdateObject(,WordInsert2.getText().toString());
            // tudni kellene hozzá a szó többi mezőjét is, hogy pontosan melyiket akarjuk módosítani

            setResult(RESULT_OK);

            finish();
        }

        if (v == Deleting) {
            //ugyanaz igaz itt is mint a módosításnál
            setResult(RESULT_OK);

            finish();
        }
    }
}
