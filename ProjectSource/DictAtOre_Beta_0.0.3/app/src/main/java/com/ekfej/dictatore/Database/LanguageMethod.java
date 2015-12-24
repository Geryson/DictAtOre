package com.ekfej.dictatore.Database;

import android.content.Context;
import android.widget.EditText;

import java.util.List;

/**
 * Created by Buda Viktor on 2015.12.24..
 */
public class LanguageMethod {

    //public DictDatabaseHandler db;
    Context context;

    public LanguageMethod(Context contextall) {
        context =contextall;
    }


    Elementary elementary = new Elementary(context);
    Lister lister = new Lister(context);


    //region Presenter rétegnek való fgv-ek

    public boolean LanguageInsert(EditText LanguageName) {
        String Name = LanguageName.getText().toString();
        List<String> nyelvek = lister.LanguageSelect();
        if (elementary.ListEqualsElement(Name, nyelvek) && elementary.NullHossz(Name)) {
            elementary.LanguageInsertElemi(Name);
            return  true;
        }
        else { return false; }
    }

    public boolean LanguageUpdate(String OldName, String NewName) {
        if (elementary.NullHossz(NewName)) {
            elementary.LanguageUpdateElemi(OldName, NewName);
            return true;
        } else {
            return false;
        }
    }

    public boolean LanguageDelete(String LanguageName) {
        return elementary.LanguageDeleteElemi(LanguageName);
    }
}
