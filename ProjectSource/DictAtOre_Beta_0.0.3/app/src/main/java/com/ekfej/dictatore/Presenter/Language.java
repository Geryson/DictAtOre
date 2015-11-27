package com.ekfej.dictatore.Presenter;

/**
 * Created by Lloyd on 2015. 11. 27..
 */
public class Language {

    private int id;
    public int getId() {
        return id;
    }
    public void setId(int id) {
         this.id = id;
    }

    private String name;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }



    public Language(String name) {
        this.name = name;
    }

    public Language(int id, String name) {
        this(name);
        this.id = id;
    }

}
