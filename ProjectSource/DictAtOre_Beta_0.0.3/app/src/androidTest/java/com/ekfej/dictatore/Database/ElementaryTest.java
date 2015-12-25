package com.ekfej.dictatore.Database;

import com.ekfej.dictatore.MenuActivity;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Buda Viktor on 2015.12.24..
 */
public class ElementaryTest extends TestCase {
    Elementary e = new Elementary();
    public void testNullHossz() throws Exception {
        String expected = "baba";
        String expected2 = "";
        assertEquals(e.NullHossz(expected), true);
        assertEquals(e.NullHossz(expected2), false);

    }

    public void testListEqualsElement() throws Exception {
        List<String> lista = new ArrayList<String>();
        String w = "megszentségteleníthetetlen";
        for (int i=0; i<10; i++) {
            lista.add(String.valueOf(w.indexOf(i)));
        }
        boolean expected = e.ListEqualsElement("e", lista);
        boolean actual = true;
        assertEquals(expected, actual);
    }
}