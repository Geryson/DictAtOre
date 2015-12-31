package com.ekfej.dictatore.Presenter;

import android.content.Context;
import android.widget.EditText;

import com.ekfej.dictatore.Database.DatabaseAccess;

/**
 * Created by Lloyd on 2015. 12. 31..
 */
public class Language_InsertPresenter {

    private DatabaseAccess db;



    public Language_InsertPresenter(DatabaseAccess db) {
        this.db = db;
    }



    public void addLanguage(String languageName) {
        db.languageMethod.LanguageInsert(languageName);
    }

    public void updateLanguage(String oldName, String newName) {
        db.languageMethod.LanguageUpdate(oldName, newName);
    }
}
