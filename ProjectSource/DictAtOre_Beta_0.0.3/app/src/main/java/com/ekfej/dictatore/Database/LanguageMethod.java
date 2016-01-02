package com.ekfej.dictatore.Database;

import android.content.Context;
import android.widget.EditText;

import java.util.List;

/**
 * Nyelvet kezelő metódusok
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

    /**
     * Nyelv beszúrása
     * @param LanguageName
     * @return
     */
    public boolean LanguageInsert(String LanguageName) {
        String Name = LanguageName;
        List<String> nyelvek = lister.LanguageSelect();
        if (elementary.ListEqualsElement(Name, nyelvek) && elementary.NullHossz(Name)) {
            elementary.LanguageInsertElemi(Name);
            return  true;
        }
        else { return false; }
    }

    /**
     * Nxelv módosítása (csak a neve)
     * @param OldName régi neve
     * @param NewName új neve
     * @return
     */
    public boolean LanguageUpdate(String OldName, String NewName) {
        if (elementary.NullHossz(NewName)) {
            elementary.LanguageUpdateElemi(OldName, NewName);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Nyelv törlése a neve alapján
     * @param LanguageName
     * @return
     */
    public boolean LanguageDelete(String LanguageName) {
        return elementary.LanguageDeleteElemi(LanguageName);
    }
}
