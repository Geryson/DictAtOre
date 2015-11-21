package com.ekfej.dictatore.Database;

/**
 * Created by Buda Viktor on 2015.11.03..
 */
public class Languages {

        private int _id;
        private String _name;

        public Languages() {

        }

        public Languages(int id, String name) {
            this._id = id;
            this._name = name;
        }

    public Languages(String name) {
        this._name = name;
    }

    public void setID(int id) {
        this._id = id;
    }

    public int getID() {
        return this._id;
    }

    public void setName(String name) {
        this._name = name;
    }

    public String getName() {
        return this._name;
    }
}
