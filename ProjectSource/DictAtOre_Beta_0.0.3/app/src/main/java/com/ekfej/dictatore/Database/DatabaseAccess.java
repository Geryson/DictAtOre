package com.ekfej.dictatore.Database;

import android.content.Context;

public class DatabaseAccess  {

    public DictDatabaseHandler db;
    public Lister lister;
    public Elementary elementary;
    public LanguageMethod languageMethod;
    public WordMethod wordMethod;
    public KnowledgeTest knowledgeTest;

    public DatabaseAccess(Context context) {
        db = DictDatabaseHandler.getInstance(context);

        wordMethod = new WordMethod(context);
        languageMethod = new LanguageMethod(context);
        lister = new Lister(context);
        elementary = new Elementary(context);
        knowledgeTest = new KnowledgeTest(context);

    }



}

