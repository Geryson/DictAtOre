package com.ekfej.dictatore.Presenter;

import android.content.Context;

import com.ekfej.dictatore.Database.DatabaseAccess;

/**
 * Created by Lloyd on 2015. 12. 11..
 */
public class MainPresenter {

    protected DatabaseAccess db;

    protected MainPresenter(Context context) {
        db = DatabaseAccess.getInstance(context);
    }

}
