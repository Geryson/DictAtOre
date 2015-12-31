package com.ekfej.dictatore.Presenter;

import android.content.Context;

import java.util.List;

/**
 * Created by Lloyd on 2015. 12. 31..
 */
public class Language_ChoosePresenter extends MainPresenter{

    private Language_InsertPresenter insertPresenter;

    private Language[] languages;
    public Language[] getLanguages() { return languages; }



    public Language_ChoosePresenter(Context context) {
        super(context);
        languages = stringLanguageList2LanguageLanguageArray(db.lister.LanguageSelect());
    }



    public void addLanguage(String languageName) {
        if (insertPresenter == null) insertPresenter = new Language_InsertPresenter(db);

        insertPresenter.addLanguage(languageName);
    }

    public void updateLanguage(int languageIndex, String newName) {
        if (insertPresenter == null) insertPresenter = new Language_InsertPresenter(db);

        insertPresenter.updateLanguage(languages[languageIndex].getName(), newName);
    }

    public void deleteLanguage(int languageIndex) {
        db.languageMethod.LanguageDelete(languages[languageIndex].getName());
    }
}
