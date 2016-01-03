package com.ekfej.dictatore.Presenter;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lloyd on 2015. 11. 27..
 */
public class Word implements Parcelable {

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


    protected Word(Parcel in) {
        id = in.readInt();
        word = in.readString();
        if (in.readByte() == 0x01) {
            meaning = new ArrayList<Word>();
            in.readList(meaning, Word.class.getClassLoader());
        } else {
            meaning = null;
        }
        language = (Language) in.readValue(Language.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(word);
        if (meaning == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(meaning);
        }
        dest.writeValue(language);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Word> CREATOR = new Parcelable.Creator<Word>() {
        @Override
        public Word createFromParcel(Parcel in) {
            return new Word(in);
        }

        @Override
        public Word[] newArray(int size) {
            return new Word[size];
        }
    };
}
