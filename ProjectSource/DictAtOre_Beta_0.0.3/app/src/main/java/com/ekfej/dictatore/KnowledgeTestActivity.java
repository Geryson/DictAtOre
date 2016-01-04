package com.ekfej.dictatore;

import android.content.Context;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ekfej.dictatore.Database.DatabaseAccess;
import com.ekfej.dictatore.Presenter.KnowledgeTestPresenter;
import com.ekfej.dictatore.Presenter.Language;

import java.util.List;

public class KnowledgeTestActivity extends AppCompatActivity implements View.OnClickListener, TextWatcher {
    EditText User_decipherment; //felhasználó_megoldása
    TextView expression; //Kifejezés
    TextView FirstLanguageTextview;
    Button Help;
    Button AgreeButton; //ideiglenes, később egy while ciklussal folyamotasan lesz ellenőrizve
    List<String> decipherment = null;
    String kifejezes = null, lastTypedText;
    TextView progressBox;
    int TestNumber = 1; //számolja hogy hanyadik feladatál jár
    KnowledgeTestPresenter presenter;
    DatabaseAccess db;
    int sumtest= 15;
    TextView deciphermentHelpTextView;
    boolean userAskedForHelp;
    String helpText;
    StringBuilder builder;
    private Language firstLanguage, secondLanguage;
    MediaPlayer succes;


    @Override
    public void onBackPressed() {
        setResult(RESULT_OK);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_knowledgetest);

        db = new DatabaseAccess(this);

        Help = (Button) findViewById(R.id.knowledgeTest_helpButton);
        Help.setOnClickListener(this);

        AgreeButton = (Button) findViewById(R.id.AgreeButton);
        AgreeButton.setOnClickListener(this);

        progressBox = (TextView) findViewById(R.id.tudasteszt_progressBox);
        progressBox.setText(TestNumber + " / " + sumtest + " szó");

        deciphermentHelpTextView = (TextView) findViewById(R.id.deciphermentHelpTextView);
        deciphermentHelpTextView.setText("");

        User_decipherment = (EditText) findViewById(R.id.knowledgeTest_decipherment);
        User_decipherment.getText();
        User_decipherment.addTextChangedListener(this);

        Bundle LanguageBundle = getIntent().getExtras();
        firstLanguage = LanguageBundle.getParcelable("firstLanguage");
        secondLanguage = LanguageBundle.getParcelable("secondLanguage");

        FirstLanguageTextview = (TextView) findViewById(R.id.tudasteszt_FirstLanguage);
        FirstLanguageTextview.setText(firstLanguage.getName() + " nyelven:");

        presenter = new KnowledgeTestPresenter(this, firstLanguage.getName(), secondLanguage.getName(), 15);
        sumtest = presenter.GetWordsCount();
        expression = (TextView) findViewById(R.id.knowledgeTest_expression);
        expression.setText("nincs ilyen szó");
        Test();

        succes = MediaPlayer.create(this, R.raw.succes);

    }
    public void Test () {
        try {
            kifejezes = presenter.GetNextWord();
            if (kifejezes != null) {
                progressBox.setText(TestNumber + " / " + sumtest + " szó");
                expression.setText(kifejezes);
                User_decipherment.setEnabled(true);
                Help.setEnabled(true);
                User_decipherment.setText("");
                helpText = null;
                deciphermentHelpTextView.setText(null);
                TestNumber++;
                userAskedForHelp = false;
                User_decipherment.setFilters(new InputFilter[]{});
                AgreeButton.setVisibility(View.INVISIBLE);
            }
            else {
                //elfogytak a szavak (lehetne értékelni a teljesítményt)
                //esetleg egy kis activity mint a languageinsertnél
                //és oda kiírnánk hány %-os lett a tesztje
                Toast.makeText(this, "Vége", Toast.LENGTH_SHORT).show();
                finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            if (kifejezes != null && db.knowledgeTest.Decipherment(secondLanguage.getName(), firstLanguage.getName(), kifejezes).size() > 0) {
                decipherment = db.knowledgeTest.Decipherment(secondLanguage.getName(), firstLanguage.getName(), kifejezes);
            }
            else {
                //nincs párja a kifejezzésnek, majd valamit kell jelezni (vagy új értéket adni az expression-nak)
                //elvileg ilyen eset nem történhet már
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onClick(View v) {
        //DatabaseAccess db = new DatabaseAccess(this);
        if (v == AgreeButton) {
            Test();
        }
        if (v == Help) {
            userAskedForHelp = true;
            User_decipherment.setText("");
            //deciphermentHelpTextView.setText(null);
            helpText = presenter.Help();// hogy ne legyen 2x meghívva
            deciphermentHelpTextView.setText(helpText);
            User_decipherment.setFilters(new InputFilter[]{new InputFilter.LengthFilter(helpText.length())});
            //valójában nem ebbe, hanem a mögötte lévő TextView-ba kell belerakni
        }
    }

    private void CorrectDecipherment_GetNextWord() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);

        Toast.makeText(this, "Jó megoldás", Toast.LENGTH_SHORT).show();
        succes.start();
        User_decipherment.setEnabled(false);
        AgreeButton.setVisibility(View.VISIBLE);
        Help.setEnabled(false);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        lastTypedText = s.toString();
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (!s.toString().equals(lastTypedText)) {
            if (!userAskedForHelp){
                if (presenter.WordCheck(User_decipherment.getText().toString())){
                    CorrectDecipherment_GetNextWord();
                }
            }
            else if (User_decipherment.getText().length() != 0){
                List<Integer> correctChars = presenter.GetMatchingPositions();

                int currentPosition = s.length() - 1;
                if (deciphermentHelpTextView.getText().charAt(currentPosition) != '_'){
                    Toast.makeText(KnowledgeTestActivity.this, "WOW!", Toast.LENGTH_SHORT).show();//ehelyett jönne a színezés
                }

                if (deciphermentHelpTextView.getText().charAt(currentPosition) != s.charAt(currentPosition)){
                    builder = new StringBuilder();
                    builder.append(deciphermentHelpTextView.getText());
                    builder.setCharAt(currentPosition, s.charAt(currentPosition));
                    deciphermentHelpTextView.setText(builder);
                }

                if (correctChars != null) {
                    Spannable word = new SpannableString(helpText);
                    for (int i = 0; i < helpText.length(); i++) {
                        if (correctChars.contains(i)){
                            word.setSpan(new ForegroundColorSpan(Color.GREEN), i, i, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                            helpText.replace(helpText, word);
                        }
                    }
                }

                if (presenter.WordCheck(User_decipherment.getText().toString())){
                    CorrectDecipherment_GetNextWord();
                }
                else if (count == deciphermentHelpTextView.getText().length()) {
                    Toast.makeText(this, "Rossz megoldás", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    public void afterTextChanged(Editable s) {
        if (helpText != null) {


            builder = new StringBuilder();
            builder.append(deciphermentHelpTextView.getText());
            if (s.length() >= 1) {
                for (int i = s.length(); i < helpText.length(); i++) {
                    builder.setCharAt(i, helpText.charAt(i));
                }
            } else {
                builder.setCharAt(0, helpText.charAt(0));
            }
            deciphermentHelpTextView.setText(builder);
        }

    }
}