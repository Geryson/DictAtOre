package com.ekfej.dictatore;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*EditText editText = new EditText(this);
        editText.setText("I love");
        editText.addTextChangedListener(new CompleteSentenceWathcher(editText.getText().toString()));*/
    }
}
/*
class CompleteSentenceWathcher implements TextWatcher {

    private final String initialText;

    private int start;
    private int after;
    private int count;

    public CompleteSentenceWathcher(String initialText) {
        this.initialText = initialText;
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        this.start = start;
        this.count = count;
        this.after = after;
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void afterTextChanged(Editable s) {
        if(start < initialText.length()) {
            if(s.toString().startsWith(initialText)) {
                return;
            }
            if(count >= 1 && after == 0) {
                if(start+count+1 <= initialText.length()) {
                    s.replace(start, start+count, initialText.substring(start, start+count+1));
                } else {
                    s.replace(start, start, initialText.substring(start, start+1));
                }
            } else if(count == 0 && after >= 1) {
                s.delete(start, start+after);
            }
        }
    }

}
*/