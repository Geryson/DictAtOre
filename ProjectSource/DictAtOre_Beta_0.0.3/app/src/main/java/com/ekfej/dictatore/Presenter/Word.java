package com.ekfej.dictatore.Presenter;

import java.util.List;

/**
 * Created by Lloyd on 2015. 11. 27..
 */
public class Word {

    private int id;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    private String word;
    public String getWord() {
        return word;
    }
    public void setWord(String word) {
        this.word = word;
    }

    private List<Word> meaning;
    public List<Word> getMeaning() {
        return meaning;
    }

    private Language language;
    public Language getLanguage() {
        return language;
    }
    public void setLanguage(Language language) {
        this.language = language;
    }



    public Word(String word, Language language) {
        this.word = word;
        this.language = language;
    }

    public Word(int id, String word, Language language) {
        this(word, language);
        this.id = id;
    }

    public Word(String word, List<Word> meaning, Language language) {
        this(word, language);
        this.meaning = meaning;
    }

    public Word(int id, String word, List<Word> meaning, Language language) {
        this(id, word, language);
        this.meaning = meaning;
    }

    public Word(int id) {
        this.id =id;
    }



    public void AddMeaning(Word word) {
        meaning.add(word);
    }

    @Override
    public String toString() {
        return word;
    }
}
