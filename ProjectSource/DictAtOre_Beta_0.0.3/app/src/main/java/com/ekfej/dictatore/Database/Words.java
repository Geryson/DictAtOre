package com.ekfej.dictatore.Database;

/**
 * Created by Buda Viktor on 2015.11.03..
 */
public class Words {


        private int _wid;
        private String _word;
        private int _meaning;
        private int _languageid;

        public Words() {

        }

        public Words(int wid, String wname, int meaning, int languageid) {
            this._wid = wid;
            this._word = wname;
            this._meaning= meaning;
            this._languageid = languageid;
        }

        public Words(String word) {
            this._word = word;
        }

        public void setID(int wid) {
            this._wid = wid;
        }

        public int getID() {
            return this._wid;
        }

        public void setword(String word) {
            this._word = word;
        }

        public String getword() {
            return this._word;
        }
        public void setmeaning(int meaning) {
            this._meaning = meaning;
        }

        public int getmeaning() {
            return this._meaning;
        }
        public void setword(int languageid) {
            this._languageid = languageid;
        }

        public int getlangueid() {
            return this._languageid;
        }


}
