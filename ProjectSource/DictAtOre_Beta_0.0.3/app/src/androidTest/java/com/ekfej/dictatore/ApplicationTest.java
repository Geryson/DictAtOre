package com.ekfej.dictatore;

import android.app.Application;
import android.test.ApplicationTestCase;

import com.ekfej.dictatore.Database.DatabaseAccess;

import org.junit.Test;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    public ApplicationTest() {
        super(Application.class);
        DatabaseAccess db = DatabaseAccess.getInstance();
        Boolean b = db.LanguageDeleteElemi("Angol");
        assertTrue(b);
        }
// http://surveys.utest.com/surveys/utmr/utest-101-quiz-2015/+e58ef3e63011c2ac689ccb257a19286447f91e14/?collector=111034&page=3
}