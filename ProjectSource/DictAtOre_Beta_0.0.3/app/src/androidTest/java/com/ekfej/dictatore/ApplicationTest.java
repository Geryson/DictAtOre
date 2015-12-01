package com.ekfej.dictatore;

import android.app.Application;
import android.test.ApplicationTestCase;

import com.ekfej.dictatore.Database.DatabaseAccess;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    DatabaseAccess db = DatabaseAccess.getInstance();
    public ApplicationTest(DatabaseAccess db) {
        super(Application.class); }
        public void testfgv() {
        Boolean b = db.LanguageDeleteElemi("Angol");
        assertTrue(b);
        }

}